package com.test.mspringboot.rabbitmq.direct;

import com.test.mspringboot.rabbitmq.fanout.Tut3Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by Administrator on 2018/10/15
 * 路由
 * @author admin
 */
@Service
public class Tut4Sender {
    private Logger logger = LoggerFactory.getLogger(Tut4Sender.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    private int index;

    private int count;

    private final String[] keys = {"orange", "black", "green"};

    /**
     * 三次分发给不同的 key
     */
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (++this.index == 3) {
            this.index = 0;
        }
        String key = keys[this.index];
        builder.append(key).append(' ');
        builder.append(Integer.toString(++this.count));
        String message = builder.toString();
        template.convertAndSend(direct.getName(), key, message);
        logger.info(" [x] Sent '" + message + "'");
    }
}
