package com.java.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Create by Administrator on 2018/8/9
 * 缺点：判断为null 和cache.put（）非原子性的“先检查在执行”的操作
 * @author admin
 */
public class Memorizer3<A,V> implements Computable<A,V> {
    private Map<A,Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;
    public Memorizer3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if(f == null){
            FutureTask<V> ft = new FutureTask<V>(()->{
                return c.compute(arg);
            });
            f = ft;
            cache.put(arg,f);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
