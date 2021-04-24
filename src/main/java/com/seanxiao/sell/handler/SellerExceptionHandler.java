package com.seanxiao.sell.handler;

import com.seanxiao.sell.config.ProjectUrlConfig;
import com.seanxiao.sell.exception.SellException;
import com.seanxiao.sell.utils.ResultViewObjectUtils;
import com.seanxiao.sell.viewobject.ResultViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    @ExceptionHandler(value = SecurityException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:" .concat(projectUrlConfig.getSell()).concat("sell/login"));
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultViewObject handlerSellException(SellException e) {
        return ResultViewObjectUtils.error(e.getCode(), e.getMessage());
    }
}
