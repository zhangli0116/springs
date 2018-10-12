package com.frame.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Create by Administrator on 2018/10/11
 * 路由选择
 * @author admin
 */
public class RoutePublisher {
    private static final String EXCHANGE_NAME = "route";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.128");
        factory.setUsername("admin");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // direct类型
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String[] messages = new String[]{"First message.", "Second message..",
                "Third message...", "Fourth message....", "Fifth message....."};
        String selectKey = "error";
        for(String message : messages){
            //第二个参数为选择键
            channel.basicPublish(EXCHANGE_NAME,selectKey,null,message.getBytes());
            System.out.println("[x] sent " + message);
        }
        channel.close();
        connection.close();
    }
}
