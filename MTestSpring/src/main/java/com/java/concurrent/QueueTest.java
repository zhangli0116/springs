package com.java.concurrent;

import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/11/7
 *
 * @author admin
 */
public class QueueTest {

    @Test
    public void test1() throws Exception{
        LinkedBlockingQueue queue = new LinkedBlockingQueue(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,60, TimeUnit.SECONDS,new LinkedBlockingQueue(10));
        threadPoolExecutor.submit(()->{
            System.out.println("prepare to take..");
            try {
                int i =(int)queue.take();
                System.out.println("i get " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        TimeUnit.SECONDS.sleep(3);
        threadPoolExecutor.submit(()->{
            System.out.println("prepare to put..");
            try {
                queue.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
