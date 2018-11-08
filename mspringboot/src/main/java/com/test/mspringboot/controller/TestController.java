package com.test.mspringboot.controller;

import com.test.mspringboot.service.impl.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by Administrator on 2018/11/7
 *
 * @author admin
 */
@Controller
@RequestMapping("/test")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisServiceImpl redisService;

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

}
