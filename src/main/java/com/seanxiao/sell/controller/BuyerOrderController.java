package com.seanxiao.sell.controller;

import com.seanxiao.sell.converter.OrderForm2OrderDTOConverter;
import com.seanxiao.sell.dao.OrderDetail;
import com.seanxiao.sell.dto.OrderDTO;
import com.seanxiao.sell.enums.ResultEnum;
import com.seanxiao.sell.exception.SellException;
import com.seanxiao.sell.form.OrderForm;
import com.seanxiao.sell.service.BuyerService;
import com.seanxiao.sell.service.OrderService;
import com.seanxiao.sell.service.impl.BuyerServiceImpl;
import com.seanxiao.sell.utils.ResultViewObjectUtils;
import com.seanxiao.sell.viewobject.ResultViewObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


// rest controller is the combination of controller and responsebody
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController implements Serializable {

    private static final long serialVersionUID = 4051413451259848905L;

    // call service
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // create order
    @PostMapping("/create")
    public ResultViewObject create(@Valid OrderForm orderForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("[create order] not proper parameter.. orderForm ={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[create order] cart cannot be empty.. ");
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        OrderDTO updateResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderID", updateResult.getOrderId());
        return ResultViewObjectUtils.success(map);
    }

    // order list

    @GetMapping("/list")
    @Cacheable(cacheNames = "order", key = "1")
    public ResultViewObject list(@RequestParam("openid") String openId,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size) {

        if (StringUtils.isEmpty(openId)) {
            log.error("[search order list] empty order list..");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderDTO> orderDetailList =  orderService.findList(openId, pageRequest);
        return ResultViewObjectUtils.success(orderDetailList.getContent());

    }

    // order detail
    @GetMapping("/detail")
    public ResultViewObject list(@RequestParam("openid") String openid,
                                 @RequestParam("orderId") String orderId) {
        //check a privilege
        OrderDTO result = buyerService.findOrder(openid, orderId);
        return ResultViewObjectUtils.success(result);


    }

    // cancel order
    @PostMapping("/cancel")
    public ResultViewObject cancel(@RequestParam("openid") String openid,
                                 @RequestParam("orderId") String orderId) {
        //TODO here has a privilege problem
        OrderDTO result = buyerService.cancelOrder(openid, orderId);
        return ResultViewObjectUtils.success();
    }


}
