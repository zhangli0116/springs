package com.test.mspringboot.controller.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Create by Administrator on 2018/11/21
 * websocket 整合 rabbitmq 测试
 * @author admin
 */
@Controller
@RequestMapping("/webSocket")
public class WebSocketController {
    private Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("direct2")
    private DirectExchange direct;

    /**
     * 向客户端发送数据
     * @return
     */
    @RequestMapping("/sendToClient")
    @ResponseBody
    public String sendToClient(@RequestParam("rand") String rand){
        Queue queue = new Queue("default");
        rabbitTemplate.convertAndSend(queue.getName(),"i am queue");
        rabbitTemplate.convertAndSend(direct.getName(), "default", "i am directExchange", (message)->{
            //向message中插入消息
            message.getMessageProperties().setHeader("rand",rand);
            return message;
        });
        return "ok";
    }

    /**
     * rabbitmq 中消息被解析为byte
     * @param bytes
     */
    /*@RabbitListener(queues = "simple1")
    @RabbitHandler
    public void receive(byte[] bytes){
        logger.info("server received {}",new String(bytes));
    }
    */

    /*@RabbitListener(queues = "simple")
    @RabbitHandler
    public void receive(Message message){
        System.out.println(message);
    }*/

    /**
     * 接受body 和 header 数据
     * @Payload 和 @Headers
     * @Header("") 具体某个值
     * @param bytes
     * @param headers
     */
    @RabbitListener(queues = "simple")
    @RabbitHandler
    public void receive(@Payload byte[] bytes, @Headers Map<String,Object> headers)  {
        logger.info("server received headers :{}",headers);
        logger.info("server received body :{}",new String(bytes));
    }
}
