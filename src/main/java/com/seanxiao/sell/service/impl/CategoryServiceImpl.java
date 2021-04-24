package com.seanxiao.sell.service.impl;

import com.seanxiao.sell.dao.ProductCategory;
import com.seanxiao.sell.repository.ProductCategoryRepository;
import com.seanxiao.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    //introduce dao
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByProductTypeIn(List<Integer> typeInList) {
        return repository.findByCategoryTypeIn(typeInList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

}
