package com.seanxiao.sell.service;

import com.seanxiao.sell.dao.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
