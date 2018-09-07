package com.java.thread;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by Administrator on 2018/9/6
 * 两个线程间以通信方式打印  12A34B.....5153Z 字符
 * @author admin
 */
public class ThreadTest {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    public void printA() throws Exception{
        lock.lock();
        for(int i=1;i<53;i++){
            System.out.println(i);
            System.out.println(Thread.currentThread().getName());
            queue.put(1);
            if(queue.size()/2==1) {
                queue.clear();
                condition.signal();
                //最后一个元素不需要挂起当前线程了
                if(i<52) {
                    condition.await();
                }
            }
        }
        lock.unlock();
    }

    public void printB() throws Exception{
        lock.lock();
        for(int i =65;i<91;i++){
            System.out.println((char)i);
            System.out.println(Thread.currentThread().getName());
            queue.put(1);
            if(queue.size()/1==1){
                queue.clear();
                condition.signal();
                //最后一个元素不需要挂起当前线程了
                if(i<90) {
                    condition.await();
                }
            }
        }
       lock.unlock();
    }

    public static void main(String[] args) throws Exception{
        ThreadTest test = new ThreadTest();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,10,5, TimeUnit.MINUTES,new LinkedBlockingQueue<>(10));
        pool.execute(()->{
            try {
                test.printA();
            }catch (Exception e){}
        });
        Thread.sleep(40);
        pool.execute(()->{
            try {
                test.printB();
            }catch (Exception e){}
        });
        pool.shutdown();



    }

}
