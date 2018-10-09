package com.test.mspringboot.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * Create by Administrator on 2018/9/11
 * jdbc配置信息
 * @PropertySource使用 指定properties
 * @author admin
 */
@PropertySource("classpath:db.properties")
@ConfigurationProperties(prefix = "jdbc")
public class DbProperties {
    private String driver;
    private String url;
    private String username;
    private String password;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
