package com.seanxiao.sell.viewobject;

import java.util.List;
import java.util.Map;

public class ResultViewObject<T> {

    /*status code*/
    private Integer code;

    /*returned message*/
    private String message;

    /*returned detailed data*/
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
