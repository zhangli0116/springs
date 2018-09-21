package com.test.mspringboot.service;

import com.github.pagehelper.PageInfo;
import com.test.mspringboot.model.User;

/**
 * Create by Administrator on 2018/9/11
 *
 * @author admin
 */
public interface UserService {
    User searchUserById(int id) throws  Exception;

    PageInfo<User> findAllUsers(int pageNum,int pageSize) throws Exception;
}
