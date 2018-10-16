package com.test.mspringboot.rabbitmq.receive;

import com.test.mspringboot.rabbitmq.topic.Tut5Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Create by Administrator on 2018/10/16
 *
 * @author admin
 */
@Component
public class Tut5Receiver {
    private Logger logger = LoggerFactory.getLogger(Tut5Receiver.class);

    @RabbitListener(queues = "#{autoDeleteQueue5.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in, 5);
    }

    @RabbitListener(queues = "#{autoDeleteQueue6.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 6);
    }


    public void receive(String in, int receiver) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + receiver + " [x] Received '" + in + "'");
        doWork(in);
        watch.stop();
        System.out.println("instance " + receiver + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }


}
