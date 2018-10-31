package com.test.mspringboot.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Create by Administrator on 2018/9/18
 * jedis集群配置信息
 * @author admin
 */
@Component
@ConfigurationProperties(prefix = "person")
public class PersonProperties {
    private String name;
    private int age;
    private boolean sex;
    private List<String> hobby;
    private Map<String,String> own;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public Map<String, String> getOwn() {
        return own;
    }

    public void setOwn(Map<String, String> own) {
        this.own = own;
    }
}
