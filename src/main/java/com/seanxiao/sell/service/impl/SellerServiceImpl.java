package com.seanxiao.sell.service.impl;

import com.seanxiao.sell.dao.SellerInfo;
import com.seanxiao.sell.repository.SellerInfoRepository;
import com.seanxiao.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
