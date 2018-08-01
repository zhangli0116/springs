package com.frame.spring.activemq;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * 生产者服务
 * @author Administrator
 *
 */
@Service("amqSenderService")
public class AMQSenderServiceImpl {
	/**
	 * 
	 */
	@Resource(name="jmsTemplate")
	private JmsTemplate jmsTemplate;
	
	/**
	 * 
	 */
	@Resource(name="destinationQueue")
	private Destination destinationQueue;
	
	/**
	 * 
	 */
	@Resource(name="destinationTopic")
	private Destination destinationTopic;
	
	public void sendTextMsg(String message){
		//或调用jmsTemplate配置的messageConverter，这里我们配置的SimpleMessageConverter
		jmsTemplate.convertAndSend(destinationQueue, message);
		//手动实现转换
		/*jmsTemplate.send(destinationQueue,new MessageCreator(){
			@Override
			public Message createMessage(Session session) throws JMSException { 
				return session.createTextMessage(message);
			}
			
		});*/
	}
	
	public void sendTopicTextMsg(String message){
		jmsTemplate.send(destinationTopic,new MessageCreator(){
			@Override
			public Message createMessage(Session session) throws JMSException { 
				return session.createTextMessage(message);
			}
			
		});	
		
	}
}
