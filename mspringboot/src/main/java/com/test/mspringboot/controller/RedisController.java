package com.test.mspringboot.controller;

import com.test.mspringboot.service.impl.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Administrator on 2018/11/7
 *
 * @author admin
 */
@Controller
@RequestMapping("/redis")
public class RedisController {
    private Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 测试redis BLPOP命令
     * @return
     */
    @RequestMapping("/redisBlpop")
    @ResponseBody
    public String redisBlpop(){
        logger.info("start...");
        redisService.blPop("list1",3);
        logger.info("end...");
        return "success";
    }

    @RequestMapping("/limitedFlow")
    @ResponseBody
    public String limitedFlow(){
        //模拟一百次发送请求
        for(int i =0;i<9;i++){
            redisService.tryRedisLimitedFlow("mylimit",4,3);
            logger.info("{}",i);
        }
        return "success";
    }

//#######################################################  测试redisTemplate 对redis lua脚本的操作   #######################################################

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
        Object result = redisTemplate.execute(redisScript,new StringRedisSerializer(),new StringRedisSerializer(),list,"60","5");
        return result;
    }


}
