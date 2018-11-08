package com.test.mspringboot.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.test.mspringboot.annotation.TimeLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.keyvalue.core.IdentifierGenerator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/11/7
 *
 * @author admin
 */
@Service
public class RedisServiceImpl {
    private Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 非阻塞式控流
     * @param key  关键值key
     * @param time 时间 单位second
     * @param number 次数
     * @return true 不限流，false 限流
     */
    public boolean redisLimitedFlow(String key,int time,int number){
        RedisScript redisScript = RedisScript.of("local times = redis.call('incr',KEYS[1]) if times == 1 then redis.call('expire',KEYS[1],ARGV[1]) end if times > tonumber(ARGV[2]) then return 0 end return 1",
                Long.class);
        List<String> list = new ArrayList<>();
        list.add(key);
        long rel = (long)redisTemplate.execute(redisScript,new StringRedisSerializer(),new StringRedisSerializer(),list,String.valueOf(time),String.valueOf(number));
        return rel == 1 ? true :false;
    }

    /**
     * redis 队列阻塞操作
     * @param key
     * @param timeout 超时时间 second
     */
    @TimeLogger
    public void blPop(String key,int timeout){
        String key2 = key + "BlockingQueue";
        List<Object> obj = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                //队列没有元素会阻塞操作，直到队列获取新的元素或超时
                return connection.bLPop(timeout,key2.getBytes());
            }
        },new StringRedisSerializer());
        //超时返回 [null]
        logger.info(obj.toString());
    }

    /**
     * 阻塞式分布锁,尝试获得锁,阻塞时间不会超过timeout
     * @param key
     * @param value
     * @param timeout
     */
    public boolean tryLock(String key,String value,int timeout){
        while(true){
            if(tryLock(key, value, String.valueOf(timeout))){
                return true;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key,String value){
        String lockKey = key + "Lock";
        String lua = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end ";
        RedisScript redisScript = RedisScript.of(lua,Long.class);
        redisTemplate.execute(redisScript,new StringRedisSerializer(),new StringRedisSerializer(),Collections.singletonList(lockKey),value);
    }

    /**
     * 分布式锁 立即返回true 或 false
     * @param key
     * @param value
     * @param leaseTime 到时间释放锁,避免执行任务挂掉，导致锁无法被释放
     * @return
     */
    public boolean tryLock(String key,String value,String leaseTime){
        String lockKey = key + "Lock";
        String lua = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then redis.call('expire',KEYS[1],ARGV[2]) return 1 end return 0";
        RedisScript redisScript = RedisScript.of(lua,Long.class);
        long rel = (long)redisTemplate.execute(redisScript,new StringRedisSerializer(),new StringRedisSerializer(), Collections.singletonList(lockKey),value,leaseTime);
        return rel == 1 ? true : false;
    }




    /**
     * 向队列中添加数据
     * @param key
     */
    public void rPush(String key){
        String uuid = UUID.randomUUID().toString();
        if(tryLock( key,uuid,"60")){
            String key2 = key + "BlockingQueue";
            String value = "value";
            redisTemplate.opsForList().rightPush(key2,value);
            unlock(key,uuid);
        }

    }

    /**
     * 阻塞式限流控制
     * @param key
     * @param time
     * @param number
     */
    public void tryRedisLimitedFlow(String key,int time,int number){
        if(!redisLimitedFlow(key,time,number)){
            ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
            //新开子线程
            executor.execute(()->{
                while(true){
                    if(redisLimitedFlow(key,time,number)){
                        //redis队列中添加元素，释放阻塞
                        rPush(key);
                        break;
                    }
                }
            });
            //阻塞当前线程
            blPop(key,60);
        }
    }


}
