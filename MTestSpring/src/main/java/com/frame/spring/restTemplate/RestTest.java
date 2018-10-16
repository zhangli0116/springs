package com.frame.spring.restTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * Create by Administrator on 2018/10/16
 * 使用 RestTemplate 例子
 * @author admin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-rest.xml"})
public class RestTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test1(){
        long start = System.currentTimeMillis();
        // 循环2000次访问，但同一时刻并发量受到连接池限制
        for(int i =0;i<2000;i++){
            //返回json格式数据
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/getCacheUser",String.class);
        }
        //String str = responseEntity.getBody();
        //System.out.println(str);
        long end = System.currentTimeMillis();
        System.out.println("用时 " + (end -start) +" ms");
    }

    /**
     * 发送contentType为json的数据
     */
    @Test
    public void test2(){
        String jsonStr = "{\n" +
                "  \"MessageHead\": {\n" +
                "    \"UserInfo\": {\n" +
                "      \"userName\": \"超级管理员\",\n" +
                "      \"password\": \"e3c2b85523b1fd71f5cb41b29312c1cd\"\n" +
                "    },\n" +
                "    \"ClientID\": 5,\n" +
                "    \"RequestGUID\":\"fd1cace1-9eb5-4d1b-93cc-081e696ea0a4\"\n" +
                "  },\n" +
                "  \"MessageBody\": {\n" +
                "    \"SearchCondition\": {\n" +
                "      \"AirLine\": \"SC\",\n" +
                "      \"EffectDate\": \"2018-11-03T00:00:00\",\n" +
                "      \"DepartPort\": \"CAN\",\n" +
                "      \"ArrivePort\": \"TAO\",\n" +
                "      \"GoSubClass\": \"\",\n" +
                "      \"TradePolicyType\": \"ALL\",\n" +
                "      \"ShareProductTypeList\": [\n" +
                "         3,\n" +
                "        6,\n" +
                "        4,\n" +
                "        2,\n" +
                "        13,\n" +
                "        0\n" +
                "      ],\n" +
                "      \"IsRealTimeData\": \"False\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> request = new HttpEntity<>(jsonStr,headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://apiproxy.ctrip.com/apiproxy/soa2/11576/json/LowPricePolicyList",request,String.class);
        System.out.println(responseEntity.getBody());

    }
}
