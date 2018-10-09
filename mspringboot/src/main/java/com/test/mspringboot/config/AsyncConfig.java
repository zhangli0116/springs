package com.test.mspringboot.config;

import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Create by Administrator on 2018/10/9
 * @EnableAsync使用  开启@Async 异步注解
 * 注意：同一个类中调用异步方法不起作用，需要单独建立一个类
 * @author admin
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public AsyncTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //线程允许的空闲时间
        executor.setKeepAliveSeconds(300);
        //工作队列最大的长度
        executor.setQueueCapacity(500);
        return executor;
    }

}
