package com.seanxiao.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.seanxiao.sell.dao.OrderDetail;
import com.seanxiao.sell.enums.OrderStatusEnum;
import com.seanxiao.sell.enums.PayStatusEnum;
import com.seanxiao.sell.utils.EnumUtils;
import com.seanxiao.sell.utils.serializer.Date2longSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerTele;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    /*default set to new order*/
    private Integer orderStatus;

    /*default set to not paid*/
    private Integer payStatus;

    @JsonSerialize(using = Date2longSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2longSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    public OrderDTO() {};

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerTele() {
        return buyerTele;
    }

    public void setBuyerTele(String buyerTele) {
        this.buyerTele = buyerTele;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerOpenid() {
        return buyerOpenid;
    }

    public void setBuyerOpenid(String buyerOpenid) {
        this.buyerOpenid = buyerOpenid;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
    }

    public PayStatusEnum getPayStatusEnum() {
        return EnumUtils.getByCode(payStatus, PayStatusEnum.class);
    }

}
