package com.test.mspringboot.config;

import com.test.mspringboot.rabbitmq.receive.Tut2Receiver;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Create by Administrator on 2018/10/11
 * @EnableRabbit使用 开启对@RabbitListener检查，注册的BeanPostProcessor则会在bean初始化之后扫描@RabbitListener和@RabbitHandler注解。 ?
 * @EnableScheduling使用 自动定时任务注解
 * rabbitmq 使用队列发布消息配置
 * @author admin
 */
@Configuration
//@EnableRabbit 不知用处注释掉 启动@RabbitListener注解？spring boot中不需要
@EnableScheduling
public class RabbitMqConfig {

    @Bean
    public Queue simple(){
        return new Queue("tut.simple");
    }

    @Bean
    public Queue workqueue(){
        return new Queue("tut.workqueue");
    }


    private static class ReceiverConfig {

        @Bean
        public Tut2Receiver receiver1() {
            return new Tut2Receiver(1);
        }

        @Bean
        public Tut2Receiver receiver2() {
            return new Tut2Receiver(2);
        }
    }




}
