package com.seanxiao.sell.service.impl;



import com.seanxiao.sell.exception.SellException;
import com.seanxiao.sell.service.RedisLock;
import com.seanxiao.sell.service.SecKillService;
import com.seanxiao.sell.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SecKillService {

    private static final int TIMEOUT = 10 * 1000; //超时时间 10s

    @Autowired
    private RedisLock redisLock;
    /**
     *
     */
    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;
    static
    {
        /**
         *
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

    private String queryMap(String productId)
    {
        return "Product:"
                + products.get(productId)
                +" has：" + stock.get(productId)+" left"
                +" and "
                +  orders.size() +"people place the order";
    }

    @Override
    public String querySecKillProductInfo(String productId)
    {
        return this.queryMap(productId);
    }

    @Override
    public synchronized void orderProductMockDiffUser(String productId)
    {
        //Add lock
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(productId, String.valueOf(time)) ) {
            throw new SellException(50, "Did get the product, try another time");
        }


        int stockNum = stock.get(productId);
        if(stockNum == 0) {
            throw new SellException(100, "Service end");
        }else {

            orders.put(KeyUtils.genKey(), productId);
            stockNum = stockNum-1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId,stockNum);
        }

        //release lock
        redisLock.unlock(productId, String.valueOf(time));

    }
}
