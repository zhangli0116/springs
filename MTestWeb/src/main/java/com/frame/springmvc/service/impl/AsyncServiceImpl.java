package com.frame.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.frame.springmvc.bean.User;

@Service("asyncService")
public class AsyncServiceImpl {
	
	@Async
	public Future<List<User>> task1() throws InterruptedException{
		User user = new User();
		user.setUsername("x");
		User user2 = new User();
		user2.setUsername("z");
		List<User> list = new ArrayList<User>();
		list.add(user);
		list.add(user2);
		Thread.sleep(1500);
		return new AsyncResult<List<User>>(list);
		
	}
}
