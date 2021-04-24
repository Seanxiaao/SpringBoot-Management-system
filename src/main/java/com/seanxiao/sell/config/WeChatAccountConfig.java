package com.seanxiao.sell.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Slf4j
@Component
//@ConfigurationProperties(prefix = "wechat")
public class WeChatAccountConfig {

    private String myAppId="wx6370f0a1719b0355";

    private String mpAppSecret="a71a19f53cb49cb290fbd47054e4716e";

    private String openAppId="wx6ad144e54af67d87";

    private String openAppSecret="91a2ff6d38a2bbccfb7e9f9079108e2e";

    private String mchId;

    private String mchKey;

    private String keyPath;

    private String notifyUrl;

    private Map<String, String> templateId;
}
