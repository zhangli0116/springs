package com.test.mspringboot.rabbitmq.mutil;

import com.test.mspringboot.rabbitmq.receive.Tut1Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by Administrator on 2018/10/11
 *
 * @author admin
 */
@Service
public class Tut2Sender {
    private Logger logger = LoggerFactory.getLogger(Tut2Sender.class);
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

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
        template.convertAndSend(queue.getName(), message);
        logger.info(" [x] Sent '" + message + "'");

    }

}
