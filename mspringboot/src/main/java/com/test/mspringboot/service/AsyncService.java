package com.test.mspringboot.service;

/**
 * Create by Administrator on 2018/10/16
 *
 * @author admin
 */
public interface AsyncService {
    /**
     * 耗时任务
     * @throws InterruptedException
     */
    void work() throws  InterruptedException;
}
