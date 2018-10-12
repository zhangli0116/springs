package com.frame.rabbitmq.confirm;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Create by Administrator on 2018/10/10
 * 多个worker 会循环被发送
 * @author admin
 */
public class Worker {
    private static final String QUEUE_NAME = "hello";

    private static void doWork(String task) throws InterruptedException{
        for(char ch : task.toCharArray()){
            if(ch == '.'){
                Thread.sleep(1000);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.128");
        factory.setUsername("admin");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body,"UTF-8");
                System.out.println(" [X] received " +message);
                try{
                    doWork(message);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    System.out.println(" [x] done");
                }

            }
        };
        //公平分发，RabbitMQ同一时刻最多接收一条消息，在一个worker完成之前不会在分消息给它，会把消息分给不那么忙的worker
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        //message acknowledged 消息确认标识
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);

    }

}
