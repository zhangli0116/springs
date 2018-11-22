package com.test.mspringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Create by Administrator on 2018/11/20
 *
 * @author admin
 */
//@Configuration
//@EnableWebSocketMessageBroker
public class SpringWebSocketByRabbitMqConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //发送服务端目的地址前缀 ，被@SubscribeMapping与@MessageMapping 拦截
        registry.setApplicationDestinationPrefixes("/app");
        // 使用rabbitmq 作为消息代理
        registry.enableStompBrokerRelay("/topic").setRelayHost("192.168.56.132").setRelayPort(15674);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //允许socketJS 跨域
        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("*").withSockJS();
        //普通的 stomp client进行连接
        registry.addEndpoint("/gs-guide-websocket");
    }
}
