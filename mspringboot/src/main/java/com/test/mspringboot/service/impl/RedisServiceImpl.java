package com.test.mspringboot.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.test.mspringboot.annotation.TimeLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Administrator on 2018/11/7
 *
 * @author admin
 */
@Service
public class RedisServiceImpl {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param key  关键值key
     * @param time 时间
     * @param number 次数
     * @return 1 不限流，0 限流
     */
    public long redisLimitedFlow(String key,String time,String number){
        RedisScript redisScript = RedisScript.of("local times = redis.call('incr',KEYS[1]) if times == 1 then redis.call('expire',KEYS[1],ARGV[1]) end if times > tonumber(ARGV[2]) then return 0 end return 1",
                Long.class);
        List<String> list = new ArrayList<>();
        list.add(key);
        long rel = (long)redisTemplate.execute(redisScript,new StringRedisSerializer(),new StringRedisSerializer(),list,time,number);
        return rel;
    }

    @TimeLogger
    public boolean tryRedisLimitedFlow(){
        String key = "list1";
        Object obj = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bLPop(3,key.getBytes());
            }
        },new StringRedisSerializer());
        System.out.println(obj);
        return true;
    }

}
