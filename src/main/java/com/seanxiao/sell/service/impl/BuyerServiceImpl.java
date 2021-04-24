package com.seanxiao.sell.service.impl;

import com.seanxiao.sell.dto.OrderDTO;
import com.seanxiao.sell.enums.ResultEnum;
import com.seanxiao.sell.exception.SellException;
import com.seanxiao.sell.service.BuyerService;
import com.seanxiao.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrder(String openId, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (!openId.equalsIgnoreCase(orderDTO.getBuyerOpenid())) {
            log.error("[search order]inconsistent order openId, openid = {}", openId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openId, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("[cancel order] fail to find order, orderId= {}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (!openId.equalsIgnoreCase(orderDTO.getBuyerOpenid())) {
            log.error("[search order]inconsistent order openId, openid = {}", openId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return orderService.cancel(orderDTO);
    }
}
