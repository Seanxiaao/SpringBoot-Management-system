package com.seanxiao.sell.service;

import com.seanxiao.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    /*create the order*/
    OrderDTO create(OrderDTO orderDTO);

    /*search for the certain order*/
    OrderDTO findOne(String orderId);

    /*search for the order list*/
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);

    /*cancel the order*/
    OrderDTO cancel(OrderDTO orderDTO);

    /*complete the order*/
    OrderDTO finish(OrderDTO orderDTO);

    /*pay the order*/
    OrderDTO paid(OrderDTO orderDTO);

    /*search order list for page*/
    Page<OrderDTO> findList(Pageable pageable);
}
