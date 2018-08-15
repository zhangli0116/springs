package com.frame.springmvc.service;

import java.util.Map;

import com.frame.springmvc.bean.User;

public interface UserService {
	/**
	 * 通过id查询用户信息
	 */
	User searchUserById(int id) throws Exception;

	/**
	 * 检查用户是否存在
	 */
	int checkUser(User user) throws Exception;

	/**
	 * 检查用户是否存在
	 */
	int checkUser2(int id, String password) throws Exception;
	
	int updateUser(Map<String, Object> map);
}
