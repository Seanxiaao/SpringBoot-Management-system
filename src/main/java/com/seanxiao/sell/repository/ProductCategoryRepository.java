package com.seanxiao.sell.repository;
import com.seanxiao.sell.dao.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// this is the DAO layer which helps we do some query for the Mysql
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    //find the list, must be named as below (required by repository);
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
