package com.seanxiao.sell.enums;

public enum PayStatusEnum implements CodeEnum {

    WAIT(0, "not paid order"),
    PAID(1, "paid order"),
    CANCELED(2, "canceled paid order");

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    private Integer status;

    private String message;

    PayStatusEnum(Integer status, String msg) {
        this.status = status;
        this.message = msg;
    }
}
