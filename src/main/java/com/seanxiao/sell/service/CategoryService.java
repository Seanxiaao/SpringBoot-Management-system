package com.seanxiao.sell.service;
import com.seanxiao.sell.dao.ProductCategory;

import java.util.List;

public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByProductTypeIn(List<Integer> typeInList);

    ProductCategory save(ProductCategory productCategory);


}
