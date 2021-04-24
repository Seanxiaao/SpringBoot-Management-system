package com.seanxiao.sell.service.impl;

import com.seanxiao.sell.dao.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {


    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    void findOne() {
        ProductCategory pc = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), pc.getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> lst = categoryService.findAll();
        Assert.assertNotEquals(0, lst.size());
    }

    @Test
    void findByProductTypeIn() {
        List<ProductCategory> lst = categoryService.findByProductTypeIn(Arrays.asList(2,3));
        Assert.assertNotEquals(0, lst.size());
    }

    @Test
    @Transactional
    void save() {
        ProductCategory pc = new ProductCategory("only for bodys", 10);
        ProductCategory result = categoryService.save(pc);
        Assert.assertNotEquals(null, result);
    }
}