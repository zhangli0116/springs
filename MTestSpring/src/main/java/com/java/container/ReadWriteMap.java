package com.java.container;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Create by Administrator on 2018/8/3
 * 读写锁测试
 * @author admin
 */
public class ReadWriteMap<K,V> {
    private final Map<K,V> map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = lock. readLock ();
    private final Lock w = lock.writeLock ();

    public ReadWriteMap(Map<K,V> map){
        this.map = map;
    }

    public V put(K key ,V value){
        w.lock();
        try {
            System.out.println("put   " + System.currentTimeMillis());
            return  map.put(key,value);
        } finally {
            w.unlock();
        }
    }

    public V get(K key){
        r.lock();
        try {
            System.out.println("get   " + System.currentTimeMillis());
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteMap readWriteMap = new ReadWriteMap(new HashMap<String,Object>());
        ThreadFactory namedThreadFactory =  new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(50, 100, 200, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(10),namedThreadFactory);
        for(int i=0;i<50;i++){
            pool.execute(()->{
                //System.out.println(readWriteMap.get("a"));
                readWriteMap.put("c","C");
            });

        }
        for(int i=0;i<50;i++){
            pool.execute(()->{
                readWriteMap.get("c");
            });

        }
        pool.shutdown();
    }

}
