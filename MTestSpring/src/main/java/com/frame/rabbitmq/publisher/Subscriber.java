package com.frame.rabbitmq.publisher;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Create by Administrator on 2018/10/11
 *
 * @author admin
 */
public class Subscriber {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.128");
        factory.setUsername("admin");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String queueName = channel.queueDeclare().getQueue();
        System.out.println("queueName :" +queueName);
        //第三个参数为绑定键
        channel.queueBind(queueName,EXCHANGE_NAME,"");
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
