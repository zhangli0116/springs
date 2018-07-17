package com.frame.springmvc.webSocket.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor{

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Before Handshake");
		if (request instanceof ServletServerHttpRequest) {
			HttpSession session = createSession(request);
			if (session != null) {
				String sessionId = (String) session.getAttribute("SESSION_ID");
				if(sessionId == null){
					sessionId = session.getId();
					attributes.put("SESSION_ID",sessionId);
				}
				System.out.println("------sessionId--------" + sessionId);
				
			}
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		System.out.println("After Handshake");
		HttpSession session = createSession(request);
		String sessionId = (String) session.getAttribute("SESSION_ID");
		System.out.println("----------------------" + sessionId); // ?
		
	}
	
	private HttpSession createSession(ServerHttpRequest request){
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			HttpSession session = serverRequest.getServletRequest().getSession();
			return session;
		}
		return null;
	}
	
	
	
}
