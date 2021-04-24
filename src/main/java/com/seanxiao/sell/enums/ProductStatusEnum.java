package com.seanxiao.sell.enums;

public enum ProductStatusEnum implements CodeEnum {

    UP(0, "available product"),
    Down(1, "off shelf product");

    private Integer status;

    private String statusDetails;

    ProductStatusEnum(Integer status, String statusDetails) {
        this.status = status;
        this.statusDetails = statusDetails;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusDetails() {
        return statusDetails;
    }
}
