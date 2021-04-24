package com.seanxiao.sell.service;

public interface SecKillService {

    /**
     * search information
     * @param productId
     * @return
     */
    String querySecKillProductInfo(String productId);

    /**
     * mock request
     * @param productId
     * @return
     */
    void orderProductMockDiffUser(String productId);

}
