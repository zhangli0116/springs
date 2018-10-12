package com.frame.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Create by Administrator on 2018/10/10
 * 单个生产者
 * @author admin
 */
public class Send {
    private final static String QUEUE_NAME = "hello world";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.128");
        factory.setUsername("admin");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello world";
        //第一个参数 为"" 表示使用默认的Exchange
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }

}
