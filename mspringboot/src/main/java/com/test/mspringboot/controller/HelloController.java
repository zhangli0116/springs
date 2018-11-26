package com.test.mspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.test.mspringboot.model.User;
import com.test.mspringboot.property.PersonProperties;
import com.test.mspringboot.property.DbProperties;
import com.test.mspringboot.service.UserService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/9/11
 *
 * @author admin
 */
@Controller
public class HelloController{
    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private PersonProperties personProperties;

    @Autowired
    private DbProperties dbProperties;
    @RequestMapping("/")
    private String home(HttpServletRequest request){
        //测试redis 分布式session
        request.getSession().setAttribute("value","this is a test");
        return "index";
    }



    @RequestMapping("/getUser")
    @ResponseBody
    private User getUser() throws Exception{
        redisTemplate.opsForValue().set("jedis",30,60,TimeUnit.SECONDS);
        //redisson client 对redis操作
        RBucket<String> rBucket = redisson.getBucket("bucket");
        //此key有值返回false,无值则设值并返回true
        boolean flag = rBucket.trySet("test",3, TimeUnit.MINUTES);
        if(flag){
            logger.info("插入成功");
        }else{
            logger.info("redis数据库中已有值，插入失败");
        }

        return userService.searchUserById(2);
    }

    /**
     * 获得缓存数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCacheUser")
    @ResponseBody
    public User getCacheUser() throws Exception{
        TimeUnit.SECONDS.sleep(5);
        logger.info("请求缓存数据");
        return userService.searchCachableUserById(2);
    }

    /**
     * 获得分页数据
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping("/getAllUsers")
    @ResponseBody
    private PageInfo<User> getAllUsers(@RequestParam(name = "pageNum", required = false, defaultValue = "1")
                                               int pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                                               int pageSize) throws Exception{
        return userService.findAllUsers(pageNum,pageSize);
    }


}
