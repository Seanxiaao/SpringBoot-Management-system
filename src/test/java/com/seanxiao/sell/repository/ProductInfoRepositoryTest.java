package com.seanxiao.sell.repository;

import com.seanxiao.sell.dao.ProductCategory;
import com.seanxiao.sell.dao.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void updateTest() {
        ProductInfo pi = new ProductInfo("123", "porriage");
        pi.setProductPrice(new BigDecimal(10));
        pi.setCategoryType(1);
        pi.setProductDescription("best porrigae");
        pi.setProductStatus(0);
        pi.setProductStock(10);
        repository.save(pi);
        Assert.assertNotEquals(null, pi);
    }

}