package com.test.mspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.test.mspringboot.model.User;
import com.test.mspringboot.service.UserService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/9/11
 *
 * @author admin
 */
@RestController
public class HelloController{

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redisson;

    @RequestMapping("/")
    private String home(HttpServletRequest request){
        request.getSession().setAttribute("value","this is a test");
        return "hello world";
    }

    @RequestMapping("/getUser")
    private User getUser() throws Exception{
        redisTemplate.opsForValue().set("jedis",30,60,TimeUnit.SECONDS);
        RBucket<String> rBucket = redisson.getBucket("bucket");
        boolean flag = rBucket.trySet("test",3, TimeUnit.MINUTES);
        if(flag){
            System.out.println("插入成功");
        }else{
            System.out.println("redis数据库中已有值，插入失败");
        }

        return userService.searchUserById(2);
    }

    @RequestMapping("/getAllUsers")
    private PageInfo<User> getAllUsers(@RequestParam(name = "pageNum", required = false, defaultValue = "1")
                                               int pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                                               int pageSize) throws Exception{
        return userService.findAllUsers(pageNum,pageSize);
    }
}
