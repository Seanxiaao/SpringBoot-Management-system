package com.seanxiao.sell.service.impl;

import com.seanxiao.sell.converter.OrderMaster2OrderDTOConverter;
import com.seanxiao.sell.dao.OrderDetail;
import com.seanxiao.sell.dao.OrderMaster;
import com.seanxiao.sell.dao.ProductInfo;
import com.seanxiao.sell.dto.CartDTO;
import com.seanxiao.sell.dto.OrderDTO;
import com.seanxiao.sell.enums.OrderStatusEnum;
import com.seanxiao.sell.enums.PayStatusEnum;
import com.seanxiao.sell.enums.ResultEnum;
import com.seanxiao.sell.exception.SellException;
import com.seanxiao.sell.repository.OrderDetailRepository;
import com.seanxiao.sell.repository.OrderMasterRepository;
import com.seanxiao.sell.service.InfoService;
import com.seanxiao.sell.service.OrderService;
import com.seanxiao.sell.service.PayService;
import com.seanxiao.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private InfoService infoService;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    //TODO: fix bugs about payService
    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        // according to the API document, only receive productId, productQuantity;
        // search the product
        // calculate the price
        String orderId = KeyUtils.genKey();
        BigDecimal totalPrice = new BigDecimal(BigInteger.ZERO);
        // write in the db (order master, order detail )
        // modify the store
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = infoService.findOne(orderDetail.getProductId());
            if (productInfo  == null) {
                // self design an exception
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            } else {
                totalPrice = productInfo.getProductPrice().
                        multiply(new BigDecimal(orderDetail.getProductAmount()))
                        .add(totalPrice);
                // populate the detail of the order
                orderDetail.setOrderId(orderId);
                orderDetail.setDetailId(KeyUtils.genKey());
                // set the other properties, copy from repository;
                BeanUtils.copyProperties(productInfo, orderDetail);
                orderDetailRepository.save(orderDetail);
            }
        }


        //redis for concurrency.
        //set the ordermaster
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster); // if copy then, unassigned data would be given none
        orderMaster.setOrderAmount(totalPrice);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getStatus());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getStatus());
        orderMasterRepository.save(orderMaster);

        //modify the store;
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),
                e.getProductAmount())).collect(Collectors.toList());
        infoService.decreaseStock(cartDTOList);

        //send websocket msg
        webSocket.sendMessage("has new order" + orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_LOST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();
        // check for order status first
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getStatus())) {
            log.error("[cancel order] not proper order status, orderId = {}, orderStatus={}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // modify the order status
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getStatus());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);

        if (updateResult == null) {
            log.error("[cancel order] update failed.. orderMaster = {}", updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // add the stock
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[cancel order] empty order list.. orderMaster = {}", updateResult);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductAmount())).collect(Collectors.toList());
        infoService.addStock(cartDTOList);
        // refund if funded
        if (orderDTO.getPayStatus().equals(PayStatusEnum.PAID.getStatus())) {
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        // check status
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getStatus())) {
            log.error("[finish order] not proper order status, orderId = {}, orderStatus={}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // modify the status
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.COMPLETE.getStatus());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("[finish order] update failed.. orderMaster = {}", updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {

        // check for order status
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getStatus())) {
            log.error("[pay order] not proper order status, orderId = {}, orderStatus={}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // check for pay status
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getStatus())) {
            log.error("[pay order] not proper pay status, orderId = {}, orderStatus={}",
                    orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // change pay status
        orderDTO.setPayStatus(PayStatusEnum.PAID.getStatus());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("[pay order] update failed.. orderMaster = {}", updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // pay for the order

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> page = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(page.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, page.getTotalElements());
    }

}
