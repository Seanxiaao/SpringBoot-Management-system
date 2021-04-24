package com.seanxiao.sell.repository;

import com.seanxiao.sell.dao.OrderDetail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderDetailRepositoryTest {


    @Autowired
    OrderDetailRepository repository;

    private final String ORDERID = "826214";

    @Test
    void save() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(ORDERID);
        orderDetail.setDetailId("1234");
        orderDetail.setProductIcon("xx");
        orderDetail.setProductId("123");
        orderDetail.setProductName("star");
        orderDetail.setProductPrice(new BigDecimal(2.5));
        orderDetail.setProductAmount(2);
        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotEquals(null, result);
    }


    @Test
    void findByOrderId() {
        List<OrderDetail> result = repository.findByOrderId(ORDERID);
        Assert.assertNotEquals(0, result.size());
    }
}