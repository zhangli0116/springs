package com.frame.springmvc.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

//@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	//to configure the message broker 配置消息代理
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic"); //broker 中间人  //设置服务器广播消息的基础路径
        config.setApplicationDestinationPrefixes("/app"); //设置客户端订阅消息的基础路径
		// 应用程序以 /app 为前缀，而 代理目的地以 /topic 为前缀.
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/gs-guide-websocket").withSockJS();
		// 在网页上我们就可以通过这个链接 /server/gs-guide-websocket 来和服务器的WebSocket连接
		// withSockJS允许客户端利用sockjs进行浏览器兼容性处理
		
	}
	
	
	

}
