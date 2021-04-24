package com.seanxiao.sell.service.impl;

import com.seanxiao.sell.dao.OrderDetail;
import com.seanxiao.sell.dto.OrderDTO;
import com.seanxiao.sell.enums.OrderStatusEnum;
import com.seanxiao.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String OPEN_ID = "100120213";

    private final String ORDER_ID = "161482788956213212";

    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("928 S broadway");
        orderDTO.setBuyerName("sean xiao");
        orderDTO.setBuyerOpenid(OPEN_ID);
        orderDTO.setBuyerTele("13871072299");

        //set order details
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123");
        orderDetail.setProductAmount(1);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("125");
        orderDetail1.setProductAmount(2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);

        orderDTO.setOrderDetailList(orderDetails);

        OrderDTO result = orderService.create(orderDTO);
        log.info("[create new oreder].. result = {}", result);
        Assert.assertNotEquals(null, result);

    }

    @Test
    void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("[search for single order].. result = {}", orderDTO);
        Assert.assertNotEquals(null, orderDTO.getOrderId());

    }

    @Test
    void findList() {
        PageRequest request = PageRequest.of(0, 2);
        Page<OrderDTO> orderDTOPage  = orderService.findList(OPEN_ID, request);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getStatus(), orderDTO.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.COMPLETE.getStatus(), orderDTO.getOrderStatus());
    }

    @Test
    void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.PAID.getStatus(), orderDTO.getPayStatus());
    }

    @Test
    void list() {
        PageRequest request = PageRequest.of(0, 2);
        Page<OrderDTO> orderDTOPage  = orderService.findList(request);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }
}