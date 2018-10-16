package com.test.mspringboot.config;

import com.test.mspringboot.property.ClusterConfigurationProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Administrator on 2018/9/18
 * @EnableRedissonHttpSession 表示使用Redisson 来讲集群内session保存到redis
 * @EnableCaching spring 缓存注解
 * @author admin
 */
@EnableCaching
@EnableRedissonHttpSession
@Configuration
public class RedisConfig {
  /*  @Autowired
    private ClusterConfigurationProperties clusterConfigurationProperties;

    *//**
     * 配置jedisConnectionFactory
     * 怎么配置redispool 连接池？ spring boot 自动装配RedisConnectionFactory
     * @return
     *//*
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory =  new JedisConnectionFactory(new RedisClusterConfiguration(clusterConfigurationProperties.getNodes()));;
        return jedisConnectionFactory;
    }*/

    /**
     * spring boot 自动装配 RedisConnectionFactory
     * 配置redisTemplate
     * @param redisConnectionFactory
     * @return
     */
    /*@Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //配置序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return  redisTemplate;
    }*/

    /**
     * springboot2.x 使用LettuceConnectionFactory 代替 RedisConnectionFactory
     * 在application.yml配置基本信息后,springboot2.x 能够自动装配 LettuceConnectionFactory 或 RedisConnectionFactory
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(LettuceConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //配置序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return  redisTemplate;
    }

    /**
     * redisson客户端
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException{
        Config config = Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream());
        return Redisson.create(config);
    }

    /**
     * redisson缓存管理器
     * @param redissonClient
     * @return
     */
    @Bean
    CacheManager cacheManager(RedissonClient redissonClient){
        Map<String,CacheConfig> config = new HashMap<>(16);
        // create "testMap" cache with ttl  最大缓存时间 = 24 minutes and maxIdleTime 最大空闲时间 = 12 minutes
        //可以配置多个缓存器 放入map中
        config.put("redissonMap",new CacheConfig(24*60*1000,12*60*1000));
        return  new RedissonSpringCacheManager(redissonClient,config);
    }

}
