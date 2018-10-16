package com.test.mspringboot.rabbitmq.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Create by Administrator on 2018/10/11
 * 生产者
 * @author admin
 */
@Service
public class Tut1Sender {
    private Logger logger = LoggerFactory.getLogger(Tut1Sender.class);
    /**
     * spring提供的rabbit模板
     */
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("simple")
    private Queue queue;

    public void send(){
        String message = "hello world";
        template.convertAndSend(queue.getName(),message);
        logger.info(" [x] sent " + message);
    }
}
