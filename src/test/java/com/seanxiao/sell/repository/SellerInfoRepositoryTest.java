package com.seanxiao.sell.repository;

import com.seanxiao.sell.dao.SellerInfo;
import com.seanxiao.sell.utils.KeyUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abd");
        sellerInfo.setSellerId(KeyUtils.genKey());
        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }


    @Test
    void testFindByOpenid() {
        SellerInfo sellerInfo = repository.findByOpenid("abd");
        Assert.assertNotEquals(null, sellerInfo);
    }
}