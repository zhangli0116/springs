package com.java.java8;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Create by Administrator on 2018/10/24
 * lambda表达式 应用
 * @author admin
 */
public class StreamExample {
    /**
     *
     */
    @Test
    public void test1(){
        List<String> list = Lists.newArrayList(); //Lists 是一个工具类 类似还有Maps
        List<String> list2 = Lists.newArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list2 = list.stream().filter(string->string.equals("a") || string.equals("b")).collect(Collectors.toList());
        System.out.println(list2);
    }
    @Test
    public void test2(){
        List<String> list = Arrays.asList("a","b","c");
        System.out.println(list);
    }

}
