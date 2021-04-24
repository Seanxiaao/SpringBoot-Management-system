package com.seanxiao.sell.repository;

import com.seanxiao.sell.dao.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
