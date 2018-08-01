package com.frame.spring.activemq;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消费者1
 * @author Administrator
 *
 */
public class AMQTestMessageListener implements MessageListener{

	@Override
	public void onMessage(Message msg) {
		if(msg instanceof TextMessage){
			TextMessage txtMsg = (TextMessage) msg;
            String message;
			try {
				message = txtMsg.getText();
				//实际项目中拿到String类型的message(通常是JSON字符串)之后，
	            //会进行反序列化成对象，做进一步的处理
	            System.out.println(new Date() + "consumer1 receive txt msg===" + message);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
		}
		
	}

}
