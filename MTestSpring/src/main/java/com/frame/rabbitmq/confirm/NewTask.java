package com.frame.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Create by Administrator on 2018/10/10
 * 消息确认，如果某个message已经被consumer接收，那么该message就会从queue中移除。通过消息确认机制实现。
 * @author admin
 */
public class NewTask {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.128");
        factory.setUsername("admin");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String[] messages = new String[]{"First message.", "Second message..",
                "Third message...", "Fourth message....", "Fifth message....."};
        for(String message : messages){
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("[x] Sent " + message);
        }
        channel.close();
        connection.close();
    }

}
