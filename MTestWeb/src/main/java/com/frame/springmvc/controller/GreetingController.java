package com.frame.springmvc.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import com.frame.springmvc.webSocket.bean.Greeting;
import com.frame.springmvc.webSocket.bean.HelloMessage;

//消息处理
@Controller
public class GreetingController {
	// "/app/hello" is the endpoint that theGreetingController.greeting()method is mapped to handle
	@MessageMapping("/hello")
    @SendTo("/topic/greetings") //@SendTo可以把消息广播到路径上去，例如上面可以把消息广播到”/topic/greetings”,如果客户端在这个路径订阅消息，则可以接收到消息
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("receiving" + message.getName());
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
	
	@RequestMapping("/hello2")
	public String hello(){
		return "hello";
	}
}
