package com.seanxiao.sell.repository;

import com.seanxiao.sell.dao.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        ProductCategory pc = repository.findById(1).orElse(null);
        System.out.println(pc.toString());
    }

    // transactional helps rollback for the generating data into mysql
    @Test
    @Transactional
    public void saveTest() {
        ProductCategory pc = new ProductCategory("favor by girls", 2);
        repository.save(pc);
        Assert.assertNotEquals(null, pc);
    }

    @Test
    public void updateTest() {
        ProductCategory pc = repository.findById(3).orElse(null);
        if (pc != null) {
            pc.setCategoryName("favor by boys");
            repository.save(pc);
        };

    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> lst = Arrays.asList(2,3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(lst);
        Assert.assertNotEquals(0, result.size());
    }

}