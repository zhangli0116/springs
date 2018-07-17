package com.frame.springmvc.webSocket.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * socket处理类
 * @author Administrator
 *
 */
public class MyHandler extends TextWebSocketHandler{
	
	//final 修饰 引用地址不可变  static 类共有
	//其中每一个WebSocketSession 代表和用户建立的一次连接
	private static final Map<String,WebSocketSession> maps= new HashMap<String,WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String sessionId = (String)session.getAttributes().get("SESSION_ID");
		if(sessionId !=null){
			maps.put(sessionId, session);
		}
	}



	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String sessionId = (String)session.getAttributes().get("SESSION_ID");
		if(sessionId !=null){
			maps.remove(sessionId, session);
		}
	}


	
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		System.out.println(session.getAttributes());
		System.out.println("------------Myhandler.handleTextMessage----------" + message);
		System.out.println("当前登录用户数量----------" + maps.entrySet().size());
		if(session.isOpen()){
			//session.sendMessage(new TextMessage("回传消息")); //向前端回传消息
			Iterator<Entry<String, WebSocketSession>> it =  maps.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, WebSocketSession> entry= it.next();
				entry.getValue().sendMessage(message); //实现消息群发
			}
		}
		
        
    }
	
}
