package com.test.mspringboot.controller.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 向客户端发送数据
     * @return
     */
    @RequestMapping("/sendToClient")
    @ResponseBody
    public String sendToClient(){
        Queue queue = new Queue("default");
        rabbitTemplate.convertAndSend(queue.getName(),"i am WebSocketController");
        return "ok";
    }

    /**
     * rabbitmq 中消息被解析为byte
     * @param bytes
     */
    @RabbitListener(queues = "simple")
    @RabbitHandler
    public void receive(byte[] bytes){
        logger.info("server received {}",new String(bytes));
    }
}
