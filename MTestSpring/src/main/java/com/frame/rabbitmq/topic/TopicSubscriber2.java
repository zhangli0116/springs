package com.frame.rabbitmq.topic;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Create by Administrator on 2018/10/12
 *
 * @author admin
 */
public class TopicSubscriber2 {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.128");
        factory.setUsername("admin");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String queueName = channel.queueDeclare().getQueue();
        System.out.println("queueName :" +queueName);
        //第三个参数为绑定键,相同的selectKey 才能被接收
        String selectKey = "*.update";
        channel.queueBind(queueName,EXCHANGE_NAME,selectKey);
        System.out.println(" [*] waiting for messages.To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel){
            //处理传送
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println(" [x] received " + message );

            }
        };

        channel.basicConsume(queueName,true,consumer);
    }
}