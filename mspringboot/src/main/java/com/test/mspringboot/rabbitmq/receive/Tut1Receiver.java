package com.test.mspringboot.rabbitmq.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Create by Administrator on 2018/10/11
 * 一对一 ，队列hello的接受者
 * @RabbitListener使用
 @RabbitListener(containerFactory = "rabbitListenerContainerFactory", bindings = @QueueBinding(
 value = @Queue(value = "${mq.config.queue}", durable = "true"),
 exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.TOPIC),
 key = "${mq.config.key}"), admin = "rabbitAdmin")
 *
 *
 * @author admin
 */
@Component
@RabbitListener(queues = "tut.simple")
public class Tut1Receiver {
    private Logger logger = LoggerFactory.getLogger(Tut1Receiver.class);

    /**
     * 不同的消息类型使用不同的方法来处理
     * @param in
     */
    @RabbitHandler
    public void receive(String in){
        logger.info(" [x] received " + in);
    }
}
