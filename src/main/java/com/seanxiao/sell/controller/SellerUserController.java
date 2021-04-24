package com.seanxiao.sell.controller;

import com.seanxiao.sell.config.ProjectUrlConfig;
import com.seanxiao.sell.constant.CookieConstant;
import com.seanxiao.sell.constant.RedisConstant;
import com.seanxiao.sell.dao.SellerInfo;
import com.seanxiao.sell.enums.ResultEnum;
import com.seanxiao.sell.service.SellerService;
import com.seanxiao.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "openid", required = false) String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        //find user info in mysql
        if (openid == null) {
            return new ModelAndView("common/login");
        }

        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOG_FAIL.getMsg());
            map.put("url", "login");
            return new ModelAndView("common/error");
        }

        //set to redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
        //
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "sell/seller/order/list");

    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response, HttpServletRequest request, Map<String, Object> map) {
        Cookie cookie =  CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null) {
            //remove redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        }
        CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        map.put("msg", "LOG OUT SUCCESS");
        map.put("url", "sell/seller/order/list");
        return new ModelAndView("coomon/success", map);

    }
}
