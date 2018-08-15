package com.java.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by Administrator on 2018/8/9
 * 使用线程安全的ConcurrentHashMap 代替 HashMap
 * 缺点：，如果某个线程启动了一个开销很大的计算，而其他线程并不知道
 这个计算正在进行，那么很可能会重复这个计算
 * @author admin
 */
public class Memorizer2<A,V> implements Computable<A,V> {
    private Map<A,V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;
    public Memorizer2(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(result == null){
            result = c.compute(arg);
            cache.put(arg,result);
        }
        return  result;
    }
}
