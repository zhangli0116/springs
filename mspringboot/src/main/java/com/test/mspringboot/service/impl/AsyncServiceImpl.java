package com.test.mspringboot.service.impl;

import com.test.mspringboot.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/10/16
 *
 * @author admin
 */
@Service
public class AsyncServiceImpl implements AsyncService{

    @Async
    @Override
    public void work() throws  InterruptedException{
        TimeUnit.SECONDS.sleep(1);
    }
}
