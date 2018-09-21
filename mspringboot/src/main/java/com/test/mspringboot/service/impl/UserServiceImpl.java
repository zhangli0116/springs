package com.test.mspringboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.mspringboot.mapper.UserMapper;
import com.test.mspringboot.model.User;
import com.test.mspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by Administrator on 2018/9/11
 *
 * @author admin
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public User searchUserById(int id) throws Exception {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @param pageNum 页数
     * @param pageSize 每页条数
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<User> findAllUsers(int pageNum, int pageSize) throws Exception {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        //只有紧跟在 PageHelper.startPage 方法后的第一个 Mybatis 的查询（Select）方法会被分页。
        List<User> users = userMapper.selectAllUsers();
        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;
    }
}
