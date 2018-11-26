package com.java.string;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Create by Administrator on 2018/9/21
 *
 * @author admin
 */
public class StringTest {
    private enum Color{
        BLUE(1),RED(2),YELLOW(3);
        private int index;
        private Color(int index){
            this.index = index;
        }
        private int getValue(){
            return index;
        }
    }

    @Test
    public void test1() throws Exception{
        Class c = StringTest.class;
        System.out.println(c.equals(StringTest.class));
        System.out.println(DateFormatUtils.format(DateUtils.addDays(DateUtils.parseDate("2018-09-21","yyyy-MM-dd"),30),"yyyy-MM-dd"));
    }

    @Test
    public void test2()throws Exception{
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        System.out.println(new Date(1542470400000L));
        System.out.println(DateUtils.parseDate("2018-11-04","yyyy-MM-dd").getTime());
    }

    /**
     * 按map的value排序 并获得最小一对
     */
    @Test
    public void test3(){
        Map<String,Double> map = new HashMap<>(16);
        map.put("a",1.0);
        map.put("b",1.1);
        map.put("c",0.2);
        map.put("d",2.2);
        //java 8排序
        Map<String,Double> newMap = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))  //Comparator.reverseOrder() Comparator.naturalOrder()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        Set<String> keySet = newMap.keySet();
        for(String key : keySet){
            double value = newMap.get(key);
            System.out.println(value);//2.2
            break;
        }
     }

     @Test
     public void test4(){
         byte[] bytes = JSON.toJSONBytes("5", new SerializerFeature[]{SerializerFeature.WriteClassName});
         System.out.println(new String(bytes).equals("\"5\""));// "5"
         byte[] bytes2 = "5".getBytes();
         System.out.println(new String(bytes2).equals("5")); // 5
         long l = 5;
         System.out.println(l == 5);
     }

     @Test
     public void test5() throws  Exception{
         String str = "";
         List list  = Arrays.asList(str.split(","));
         System.out.println(list);
         System.out.println(list.contains("abc"));
         String location= "http://www.baidu.com?a=b";
         //测试URLEncoder
         System.out.println(URLEncoder.encode(location,"utf-8"));


     }

}
