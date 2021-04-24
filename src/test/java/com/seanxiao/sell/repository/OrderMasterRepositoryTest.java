package com.seanxiao.sell.repository;

import com.seanxiao.sell.dao.OrderMaster;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    private final String OPENID = "9527";

    @Test
    void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerName("Author");
        orderMaster.setBuyerTele("1234586910");
        orderMaster.setBuyerAddress("us, la");
        orderMaster.setOrderAmount(new BigDecimal(1.99));

        orderMasterRepository.save(orderMaster);
        Assert.assertNotEquals(null, orderMaster);
    }

    @Test
    void findByBuyerOpenid() throws Exception {
        PageRequest request = PageRequest.of(0, 3);
        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid(OPENID, request);
        Assert.assertNotEquals(0, page.getTotalElements());
    }
}