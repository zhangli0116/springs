package com.frame.spring.activemq;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-activemq.xml"})
public class ActivemqTest {
	
	@Autowired
	private AMQSenderServiceImpl amqSenderServiceImpl;
	
	@Test
	public void test() throws Exception{
		amqSenderServiceImpl.sendTextMsg("向消息队列发送消息" + new Date());
		TimeUnit.SECONDS.sleep(2);
		amqSenderServiceImpl.sendTextMsg("向消息队列发送消息" + new Date());
		TimeUnit.SECONDS.sleep(2);
		amqSenderServiceImpl.sendTextMsg("向消息队列发送消息" + new Date());
		TimeUnit.SECONDS.sleep(2);
		amqSenderServiceImpl.sendTextMsg("向消息队列发送消息" + new Date());
	}
	
	@Test
	public void testTopic() throws Exception{
		amqSenderServiceImpl.sendTopicTextMsg("向消息队列发送消息" + new Date());
		TimeUnit.SECONDS.sleep(2);
		amqSenderServiceImpl.sendTopicTextMsg("向消息队列发送消息" + new Date());
		TimeUnit.SECONDS.sleep(2);
		amqSenderServiceImpl.sendTopicTextMsg("向消息队列发送消息" + new Date());
		TimeUnit.SECONDS.sleep(2);
		amqSenderServiceImpl.sendTopicTextMsg("向消息队列发送消息" + new Date());
	}
}
