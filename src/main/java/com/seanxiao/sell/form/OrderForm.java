package com.seanxiao.sell.form;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {

    // buyer's name
    @NotEmpty(message = "required name")
    private String name;

    // buyer's phone
    @NotEmpty(message = "required phone num")
    private String phone;

    // buyer's address
    @NotEmpty(message = "required address")
    private String address;

    // buyer's openid
    @NotEmpty(message = "required openid")
    private String openid;

    @NotEmpty(message = "item chart cannot be empty")
    private String items;
}
