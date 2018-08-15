package com.frame.springmvc.cache.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Administrator on 2018/8/14
 *
 * @author admin
 */
@Configuration
@EnableCaching
public class RedissonCacheConfig {

    @Bean(destroyMethod="shutdown")
    RedissonClient redisson() throws IOException {
        Config config = new Config();
        //config.setCodec(new JsonJacksonCodec()); 默认
        config.useClusterServers()
                .addNodeAddress("redis://192.168.56.128:7000", "redis://192.168.56.128:7001","redis://192.168.56.128:7002");
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient){
        Map<String,CacheConfig> config = new HashMap<String,CacheConfig>();
        // create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
        config.put("testMap",new CacheConfig(2*60*1000,1*60*1000));
        return  new RedissonSpringCacheManager(redissonClient,config);
    }

}
