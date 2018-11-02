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
        request.getSession().setAttribute("value","this is a test");
        return "index";
    }



    @RequestMapping("/getUser")
    @ResponseBody
    private User getUser() throws Exception{
        redisTemplate.opsForValue().set("jedis",30,60,TimeUnit.SECONDS);
        RBucket<String> rBucket = redisson.getBucket("bucket");
        boolean flag = rBucket.trySet("test",3, TimeUnit.MINUTES);
        if(flag){
            logger.info("插入成功");
        }else{
            logger.info("redis数据库中已有值，插入失败");
        }

        return userService.searchUserById(2);
    }


    @RequestMapping("/getCacheUser")
    @ResponseBody
    public User getCacheUser() throws Exception{
        logger.info("请求缓存数据");
        return userService.searchCachableUserById(2);
    }

    @RequestMapping("/getAllUsers")
    @ResponseBody
    private PageInfo<User> getAllUsers(@RequestParam(name = "pageNum", required = false, defaultValue = "1")
                                               int pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                                               int pageSize) throws Exception{
        return userService.findAllUsers(pageNum,pageSize);
    }

    /**
     * lua脚本执行测试
     * @return
     */
    @RequestMapping("/limit1")
    @ResponseBody
    public String limit1(){
        String lua = "return KEYS[1]";
        DefaultRedisScript redisScript = new DefaultRedisScript(lua);
        //设置返回值
        redisScript.setResultType(Integer.class);
        List<String> list = new ArrayList<>();
        list.add("10");// Integer can not cast to String ，所以KEYS和ARGV参数值 必须为string? java.lang.IllegalStateException: null
        int re =  (int)redisTemplate.execute(redisScript,list); //10  ,第二个参数一定得有，且不能为空的list
        return "ok";
    }

    /**
     * 操作lua脚本文件
     * @return
     */
    @RequestMapping("/limit")
    @ResponseBody
    public Object limit(){
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setLocation(new ClassPathResource("limit.lua"));
        redisScript.setResultType(String.class);
        List<String> list = new ArrayList<>();
        list.add("door");
        //定义 keys,argv和result序列化方式
        Object result = redisTemplate.execute(redisScript,new StringRedisSerializer(),new StringRedisSerializer(),list,"60","5");
        return  result;
    }

    /**
     * 操作lua脚本字符串
     * @return
     */
    @RequestMapping("/limit3")
    @ResponseBody
    public Object limit3(){
        RedisScript redisScript = RedisScript.of("local times = redis.call('incr',KEYS[1]) if times == 1 then redis.call('expire',KEYS[1],ARGV[1]) end if times > tonumber(ARGV[2]) then return 0 end return 1",
                Long.class);
        List<String> list = new ArrayList<>();
        list.add("door3");
        Object result = redisTemplate.execute(redisScript,list,"60","5");
        return result;
    }

}
