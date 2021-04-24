package com.seanxiao.sell.enums;

public enum OrderStatusEnum implements CodeEnum {

    NEW(0, "new order"),
    COMPLETE(1, "finished order"),
    CANCEL(2, "canceled order");

    private Integer status;

    private String message;

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    OrderStatusEnum(Integer status, String msg) {
        this.status = status;
        this.message = msg;
    }

//    public static OrderStatusEnum getOrderStatusEnum(Integer code) {
//        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
//            if (orderStatusEnum.getStatus().equals(code)) {
//                return orderStatusEnum;
//            }
//        }
//        return null;
//    }
}
