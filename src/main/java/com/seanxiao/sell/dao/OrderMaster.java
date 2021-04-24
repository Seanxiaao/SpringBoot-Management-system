package com.seanxiao.sell.dao;

import com.seanxiao.sell.enums.OrderStatusEnum;
import com.seanxiao.sell.enums.PayStatusEnum;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Table(name="order_master")
public class OrderMaster {

    // notice the naming method is important since it will help JPA to locate the attribute;
    @Id
    private String orderId;

    private String buyerName;

    private String buyerTele;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    /*default set to new order*/
    private Integer orderStatus = OrderStatusEnum.NEW.getStatus();

    /*default set to not paid*/
    private Integer payStatus = PayStatusEnum.WAIT.getStatus();

    private Date createTime;

    private Date updateTime;

    public OrderMaster() {};

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

}
