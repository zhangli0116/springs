package com.frame.spring.service;

import javax.jms.Message;

/**
 * Create by Administrator on 2018/8/7
 *
 * @author admin
 */
public interface MessageService {

    /**
     * 接受消息服务
     */
    void onMessage(Message msg);
}
