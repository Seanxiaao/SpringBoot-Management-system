package com.seanxiao.sell.repository;

import com.seanxiao.sell.dao.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByOpenid(String openId);
}
