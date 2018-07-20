package com.frame.springmvc.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.frame.springmvc.annotation.TimeLogger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.frame.springmvc.bean.User;
import com.frame.springmvc.mapper.UserMapper;
import com.frame.springmvc.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {
	
	private UserMapper userMapper;

	@TimeLogger
	public User searchUserById(int id) throws Exception {
		return userMapper.findUserById(id);
		
	}
	public int checkUser(User user) throws Exception {
		return userMapper.checkUser(user);
	}
	
	public int checkUser2(int id, String password) throws Exception {
		return userMapper.checkUser2(id,password);
	}
	
	public int updateUser(Map<String, Object> map) {	
		return userMapper.updateUser(map);
	}
	
	public UserMapper getUserMapper() {
		return userMapper;
	}
	
	@Resource(name="userMapper")
	//@Resource(type=UserMapper.class)
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	
	

}
