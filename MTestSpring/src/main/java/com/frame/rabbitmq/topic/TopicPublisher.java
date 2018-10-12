package com.frame.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Create by Administrator on 2018/10/12
 *
 * @author admin
 */
public class TopicPublisher {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.128");
        factory.setUsername("admin");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // direct类型
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        String[] messages = new String[]{"First message.", "Second message..",
                "Third message...", "Fourth message....", "Fifth message....."};
        String selectKey = "item.insert";
        for(String message : messages){
            //第二个参数为路由键
            channel.basicPublish(EXCHANGE_NAME,selectKey,null,message.getBytes());
            System.out.println("[x] sent " + message);
        }
        String selectKey2 = "item.update";
        for(String message : messages){
            //第二个参数为路由键
            channel.basicPublish(EXCHANGE_NAME,selectKey2,null,message.getBytes());
            System.out.println("[x] sent " + message);
        }
        channel.close();
        connection.close();
    }
}
