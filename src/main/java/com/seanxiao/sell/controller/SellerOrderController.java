package com.seanxiao.sell.controller;

import com.lly835.bestpay.rest.type.Get;
import com.seanxiao.sell.dao.OrderDetail;
import com.seanxiao.sell.dao.OrderMaster;
import com.seanxiao.sell.dto.OrderDTO;
import com.seanxiao.sell.enums.ResultEnum;
import com.seanxiao.sell.service.OrderService;
import com.seanxiao.sell.utils.ResultViewObjectUtils;
import com.seanxiao.sell.viewobject.ResultViewObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);

    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                                Map<String, Object> map) {
        try {
            OrderDTO orderDTO =  orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (Exception e) {
            log.error(("[business side cancel] cannot find order"));
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", "success");
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);

    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,  Map<String, Object> map) {
        map.put("url", "/sell/seller/order/list");
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }
        map.put("msg", "order successfully finished");
        return new ModelAndView("common/success", map);
    }

}
