package com.test.mspringboot.rabbitmq.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Create by Administrator on 2018/10/15
 * 在接受者 广播的路由写法一致
 * @author admin
 */
@Component
public class Tut4Receiver {
    private Logger logger = LoggerFactory.getLogger(Tut4Receiver.class);

    @RabbitListener(queues="#{autoDeleteQueue3.name}")
    public void receive1(String in ) throws InterruptedException{
        receive(in,3);
    }

    @RabbitListener(queues="#{autoDeleteQueue4.name}")
    public void receive2(String in ) throws InterruptedException{
        receive(in,4);
    }

    public void receive(String in, int receiver) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        logger.info("instance " + receiver + " [x] Received '" + in + "'");
        doWork(in);
        watch.stop();
        logger.info("instance " + receiver + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
