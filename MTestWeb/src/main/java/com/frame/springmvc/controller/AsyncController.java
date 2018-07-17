package com.frame.springmvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.springmvc.bean.User;
import com.frame.springmvc.service.impl.AsyncServiceImpl;

@Controller
public class AsyncController {
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@RequestMapping("/async/test1")
	public String test1() throws InterruptedException, ExecutionException{
		List<Future> list = new ArrayList<Future>();
		for(int i =0 ;i<5 ;i++){
			list.add(asyncServiceImpl.task1());
			 
		}
		while(true){
			if(list.get(list.size()-1).isDone()) break;
		}
		for(int i=0;i<list.size();i++){
			Future<List> task = list.get(i);
			List l = task.get();
			System.out.println(l);
			
			
		}
		
		return "hello";
	}
}
