package com.seanxiao.sell.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProjectUrlConfig {

    /**
     * Wechat platfrom url
     */
    public String wechatMpAuthorize;

    /**
     * Wechat platfrom url
     */
    public String wechatOpenAuthorize = "https://seanxiao.natapp1.cc";

    /**
     * System
     */
    public String sell = "http://seanxiao.natapp1.cc/";
}