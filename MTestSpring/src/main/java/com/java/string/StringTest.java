package com.java.string;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.UUID;

/**
 * Create by Administrator on 2018/9/21
 *
 * @author admin
 */
public class StringTest {

    @Test
    public void test1() throws Exception{
        Class c = StringTest.class;
        System.out.println(c.equals(StringTest.class));
        System.out.println(DateFormatUtils.format(DateUtils.addDays(DateUtils.parseDate("2018-09-21","yyyy-MM-dd"),30),"yyyy-MM-dd"));
    }

    @Test
    public void test2(){
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
    }

}
