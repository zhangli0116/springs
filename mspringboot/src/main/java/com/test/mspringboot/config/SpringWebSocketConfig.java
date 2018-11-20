package com.test.mspringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

/**
 * Create by Administrator on 2018/11/19
 * 使用基于内存的简单代理
 *
 * 启动WebSocket消息处理，由消息代理支持
 * java8中接口的使用 default ,static 方法无需必须重写
 * @author admin
 */
@Configuration
@EnableWebSocketMessageBroker
public class SpringWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 定义消息代理，设置消息连接的各种规范请求信息
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //启动一个简单基于内存的消息代理  返回客户端消息前缀/topic
        registry.enableSimpleBroker("/topic");
        //发送个服务端目的地前缀 /app 开头的数据会被@MessageMapping拦截 进入方法体
        registry.setApplicationDestinationPrefixes("/app");
        //点对点用户
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * 添加一个服务端点，来接收客户端的连接。
     * @param registry
     */
   @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
       //添加一个/gs-guide-websocket 端点，客户端通过这个端点进行连接， withSockJS 开启对SockJS支持
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }


}
