package com.seanxiao.sell.aop;

import com.seanxiao.sell.constant.CookieConstant;
import com.seanxiao.sell.constant.RedisConstant;
import com.seanxiao.sell.enums.ResultEnum;
import com.seanxiao.sell.exception.SellException;
import com.seanxiao.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.seanxiao.sell.controller.Seller*.*(..))" +
            "&& !execution(public * com.seanxiao.sell.controller.SellerUserController.*(..))")
    public void verify() {

    }

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie == null) {
           log.warn("[login check] cannot find token");
           throw new SecurityException("invalid token");
        }

        // find in redis
        String tokenValue = stringRedisTemplate.opsForValue().
                get(String.format(RedisConstant.TOKEN_PREFIX,  cookie.getValue()));
        log.warn("tokenValue : {}" + tokenValue);
        if(StringUtils.isEmpty(tokenValue)) {
            log.warn("[login check] cannot find token in Redis");
            throw  new SecurityException("invalid token");
        }
    }

}
