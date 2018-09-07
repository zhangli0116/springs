package com.java.date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Create by Administrator on 2018/8/31
 * 时间测试类
 * @author admin
 */
public class DateTest {

    @Test
    public void test1(){
        String next = DateFormatUtils.format(DateUtils.addMonths(new Date(),1),"yyyy-MM-dd");
        String next2 = DateFormatUtils.format(DateUtils.addDays(new Date(),30),"yyyy-MM-dd");
        System.out.println(next2);

    }
}
