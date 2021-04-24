package com.seanxiao.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.seanxiao.sell.dto.OrderDTO;
import com.seanxiao.sell.enums.ResultEnum;
import com.seanxiao.sell.exception.SellException;
import com.seanxiao.sell.service.OrderService;
import com.seanxiao.sell.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping("/templates/templates")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) throws UnsupportedEncodingException {
        //1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2. 发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl.startsWith("http://") ? returnUrl : URLEncoder.encode(returnUrl, "utf-8"));

        return new ModelAndView("pay/create", map);
    }

    /**
     * asyc notification
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        payService.notify(notifyData);

        return new ModelAndView("pay/success");
    }
}
