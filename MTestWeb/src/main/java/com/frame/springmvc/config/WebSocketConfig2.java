package com.frame.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.frame.springmvc.webSocket.handler.MyHandler;
import com.frame.springmvc.webSocket.interceptor.SpringWebSocketHandlerInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig2 implements WebSocketConfigurer{
	
	
	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/myHandler").addInterceptors(new SpringWebSocketHandlerInterceptor()).withSockJS();
        //addInterceptors websocket握手的时候进行拦截
        //withSockJS()  支持 sockjs扩展
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }
    
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
	
}
