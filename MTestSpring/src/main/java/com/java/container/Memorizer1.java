package com.java.container;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Administrator on 2018/8/2
 * 并发缓存器的实现1
 * 缺点：对compute整个方法同步，会带来明显的可伸缩性问题.：每次只有一个线程能够执行compute。如果另一个线程正在计算结
 果，那么其他调用compute的线程可能被阻塞很长时间。如果有多个线程在排队等待还未计算
 出的结果，那么compute方法的计算时间可能比没有“记忆”操作的计算时间更长
 * @author admin
 */
public class Memorizer1<A,V> implements Computable<A,V>  {
    private final Map<A, V> cache = new HashMap<A, V >( ) ;
    private final Computable<A, V> c ;
    public Memorizer1(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
