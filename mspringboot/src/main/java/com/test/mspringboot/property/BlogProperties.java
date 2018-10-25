package com.test.mspringboot.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Create by Administrator on 2018/9/11
 *  @Value 能够注入application.properties或 yml中的值
 * @author admin
 */
@Component
public class BlogProperties {
    @Value("${blog.name}")
    private String name;
    @Value("${blog.title}")
    private String title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
