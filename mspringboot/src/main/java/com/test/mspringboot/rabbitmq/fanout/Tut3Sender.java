package com.test.mspringboot.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by Administrator on 2018/10/12
 * 发布订阅
 * @author admin
 */
@Service
public class Tut3Sender {
    private Logger logger = LoggerFactory.getLogger(Tut3Sender.class);
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanout;

    int dots = 0;
    int count = 0;

    public void send(){
        StringBuilder builder = new StringBuilder("Hello");
        if(dots++ == 3){
            dots = 1;
        }
        for(int i =0;i<dots;i++){
            builder.append(".");
        }
        builder.append(Integer.toString(++count));
        String message = builder.toString();
        //向交换机发送信息
        template.convertAndSend(fanout.getName(),"", message);
        logger.info(" [x] Sent '" + message + "'");
    }
}
