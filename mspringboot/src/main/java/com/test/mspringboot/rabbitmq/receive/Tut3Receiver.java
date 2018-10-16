package com.test.mspringboot.rabbitmq.receive;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Create by Administrator on 2018/10/15
 *
 * @author admin
 */
@Component
public class Tut3Receiver {
    private Logger logger = LoggerFactory.getLogger(Tut3Receiver.class);

    /**
     * @RabbitListener 监听队列
     * @param in
     * @throws InterruptedException
     */
    @RabbitListener(queues="#{autoDeleteQueue1.name}")
    public void receive1(String in ) throws InterruptedException{
        receive(in,1);
    }

    @RabbitListener(queues="#{autoDeleteQueue2.name}")
    public void receive2(String in) throws InterruptedException{
        receive(in,2);
    }

    @RabbitListener(queues="#{autoDeleteQueue3.name}")
    public void receive3(String in ) throws InterruptedException{
        receive(in,3);
    }

    @RabbitListener(queues="#{autoDeleteQueue4.name}")
    public void receive4(String in ) throws InterruptedException{
        receive(in,4);
    }

    @RabbitListener(queues="#{autoDeleteQueue5.name}")
    public void receive5(String in ) throws InterruptedException{
        receive(in,5);
    }

    @RabbitListener(queues="#{autoDeleteQueue6.name}")
    public void receive6(String in ) throws InterruptedException{
        receive(in,6);
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
