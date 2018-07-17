package com.frame.springmvc.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.frame.springmvc.bean.User;



@Mapper
public interface UserMapper {
	
	//通过id 查询用户信息
	public User findUserById(int id) throws Exception;
	
	//检查用户是否存在 方式一
	public int checkUser(User user) throws Exception;
	
	//检查用户是否存在 方式二  @Param注解的作用是给参数命名,参数命名后就能根据名字得到参数值
	public int checkUser2(@Param("ids") int id, @Param("password") String password) throws Exception;
	
	
	//向用户表中插入信息 ()
	public int updateUser(Map<String, Object> map);
	
}
