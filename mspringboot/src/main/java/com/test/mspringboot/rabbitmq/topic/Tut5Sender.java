package com.test.mspringboot.rabbitmq.topic;

import com.test.mspringboot.rabbitmq.direct.Tut4Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by Administrator on 2018/10/16
 *
 * @author admin
 */
@Service
public class Tut5Sender {
    private Logger logger = LoggerFactory.getLogger(Tut5Sender.class);
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    private int index;

    private int count;

    private final String[] keys = {"quick.orange.rabbit",
            "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (++this.index == keys.length) {
            this.index = 0;
        }
        String key = keys[this.index];
        builder.append(key).append(' ');
        builder.append(Integer.toString(++this.count));
        String message = builder.toString();
        template.convertAndSend(topic.getName(), key, message);
        logger.info(" [x] Sent '" + message + "'");
    }

}
