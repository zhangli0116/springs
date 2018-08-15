package com.frame.spring.service.impl;

import com.frame.spring.service.MessageService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Date;

/**
 * Create by Administrator on 2018/8/7
 *
 * @author admin
 */
@Service
public class MessageServiceImpl implements MessageService{
    /**
     * 多线程接受消息服务
     */
    @Async
    @Override
    public void onMessage(Message msg) {
        if(msg instanceof TextMessage){
            TextMessage txtMsg = (TextMessage) msg;
            String message;
            try {
                message = txtMsg.getText();
                //实际项目中拿到String类型的message(通常是JSON字符串)之后，
                //会进行反序列化成对象，做进一步的处理
                System.out.println(Thread.currentThread().getName() + "; receive time " + new Date() + " consumer1 receive txt msg===" + message);
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
