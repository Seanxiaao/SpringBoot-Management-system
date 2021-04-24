package com.seanxiao.sell.service;

import com.seanxiao.sell.dto.OrderDTO;

public interface BuyerService {

    //search for a order
    OrderDTO findOrder(String openId, String orderId);


    //cancel order
    OrderDTO cancelOrder(String openId, String orderId);
}
