package com.test.mspringboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by Administrator on 2018/9/25
 *
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD
})
public @interface DataSource {
    String value() default "master";
}
