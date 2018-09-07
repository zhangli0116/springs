package com.frame.spring.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/8/14
 *
 * @author admin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-redis.xml"})
public class RedisTest {

    @Resource(name="redissonClient")
    private Redisson redisson;
    @Test
    public void test1(){
        RBucket<String> rBucket =  redisson.getBucket("bucket");
        //原子操作 有则插入并设值过去时间3分钟
        boolean flag = rBucket.trySet("test1",3, TimeUnit.MINUTES);
        if(flag){
            System.out.println("插入成功");
        }else{
            System.out.println("数据库中已有值，插入失败");
        }

    }
}
