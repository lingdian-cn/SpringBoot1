package com.dling.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @description app配置
 * @author dling
 * @date 2019-06-11 11:07:58
 * @since jdk8
 */
@Component
@ConfigurationProperties(prefix = "app")
// PropertySource默认取application.properties
// @PropertySource(value = "config.properties")
public class AppProperties {

    private String appId;
    private String secretKey;
    private String secretIv;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretIv() {
        return secretIv;
    }

    public void setSecretIv(String secretIv) {
        this.secretIv = secretIv;
    }
}
