package com.seanxiao.sell.service.impl;

import com.seanxiao.sell.dao.ProductInfo;
import com.seanxiao.sell.enums.ProductStatusEnum;
import com.seanxiao.sell.service.InfoService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class InfoServiceImplTest {

    @Autowired
    private InfoService infoService;

    @Test
    void findOne() {
        ProductInfo pi = infoService.findOne("123");
        Assert.assertEquals("123", pi.getProductId());
    }

    @Test
    void findAvailableAll() {
        List<ProductInfo> pilst = infoService.findAvailableAll();
        Assert.assertNotEquals(0, pilst.size());
    }

    @Test
    void findAll() {
        // page request is protected in the source code
        PageRequest request = PageRequest.of(0, 2);
        Page<ProductInfo> piPage = infoService.findAll(request);
        System.out.println(piPage.getTotalElements());

    }

    @Test
    void save() {
        ProductInfo pi = new ProductInfo("124", "shrimp");
        pi.setProductPrice(new BigDecimal(5));
        pi.setCategoryType(1);
        pi.setProductDescription("best shrimp");
        pi.setProductStatus(ProductStatusEnum.Down.getStatus());
        pi.setProductStock(10);
        infoService.save(pi);
        Assert.assertNotEquals(null, pi);
    }
}