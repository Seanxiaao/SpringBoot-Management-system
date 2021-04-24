package com.seanxiao.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class RedisLock {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean lock(String key, String value) {

        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        // only one thread can get the lock;
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, currentValue);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return  true;
            }
        }
        return false;
    }

    public void unlock(String key, String value) {
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("[redis distribution lock] unlock, {}",  e.getCause());
        }
    }
}
