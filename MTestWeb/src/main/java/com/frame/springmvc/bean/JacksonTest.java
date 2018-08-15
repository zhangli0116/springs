package com.frame.springmvc.bean;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

/**
 * Create by Administrator on 2018/8/15
 *
 * @author admin
 */
public class JacksonTest {

    public static void main(String[] args)  throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User(1,"Z","L",new Date(),"B");
        String str = objectMapper.writeValueAsString(user);
        System.out.println(str);

        String jsonStr = "{\"username\":\"Z\",\"password\":\"L\",\"birthday\":\"2018-08-15\",\"address\":\"B\",\"cd\":1}";
        User user1 = objectMapper.readValue(jsonStr,User.class);
        System.out.println(user1);
    }
}
