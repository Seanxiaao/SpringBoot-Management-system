package com.seanxiao.sell.service;

import com.seanxiao.sell.dao.ProductInfo;
import com.seanxiao.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InfoService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findAvailableAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // add store
    void addStock(List<CartDTO> cartDTOList);

    // reduce store
    void decreaseStock(List<CartDTO> cartDTOList);

    ProductInfo onSale(String productId);

    ProductInfo offSale(String productId);
}

