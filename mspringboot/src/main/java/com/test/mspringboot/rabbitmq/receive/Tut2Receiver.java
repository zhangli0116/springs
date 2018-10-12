package com.test.mspringboot.rabbitmq.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * Create by Administrator on 2018/10/11
 *
 * @author admin
 */
@RabbitListener(queues = "hello")
public class Tut2Receiver {
    private Logger logger = LoggerFactory.getLogger(Tut2Receiver.class);

    private final int instance;

    public Tut2Receiver(int instance){
        this.instance = instance;
    }

    @RabbitHandler
    public void receive(String in) throws InterruptedException {
        //一个计时工具类
        StopWatch watch = new StopWatch();
        watch.start();
        logger.info("instance " + this.instance +
                " [x] Received '" + in + "'");
        doWork(in);
        watch.stop();
        logger.info("instance " + this.instance +
                " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    /**
     * 模拟任务
     * @param in
     * @throws InterruptedException
     */
    private void doWork(String in) throws  InterruptedException{
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
