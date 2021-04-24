package com.seanxiao.sell.enums;

public enum ResultEnum {

    PARAM_ERROR(1, "parameter errors"),

    PRODUCT_NOT_EXIST(10, "product not exist"),

    PRODUCT_BAD_NUM(11, "wrong product number"),

    ORDER_NOT_EXIST(12,  "order not exist"),

    ORDER_DETAIL_LOST(13, "order detail not exist"),

    ORDER_DETAIL_EMPTY(14, "order detail is empty"),

    ORDER_STATUS_ERROR(15, "order status error"),

    ORDER_UPDATE_FAIL(16, "order update fail"),

    ORDER_PAY_STATUS_ERROR(17," order pay status error"),

    LOG_FAIL(30, "log fail"),

    FAIL_GET_TOKEN(40, "cannot find token");


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
