package com.frame.spring.activemq;

import com.frame.spring.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

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

	@Autowired
	private MessageService messageService;

	@Override
	public void onMessage(Message msg) {
		messageService.onMessage(msg);
	}

}
