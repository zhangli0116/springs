package com.frame.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Create by Administrator on 2018/10/11
 * Exchange使用  发布/订阅模式 可以有多个接收者同时接收
 * @author admin
 */
public class Publisher {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.128");
        factory.setUsername("admin");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String[] messages = new String[]{"First message.", "Second message..",
                "Third message...", "Fourth message....", "Fifth message....."};
        for(String message : messages){
            //第二个参数为选择键
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("[x] sent " + message);
        }
        channel.close();
        connection.close();
    }


}
