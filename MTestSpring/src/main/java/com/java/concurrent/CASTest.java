package com.java.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Create by Administrator on 2018/11/9
 *
 * @author admin
 */
public class CASTest {
    private static AtomicInteger atomicInt = new AtomicInteger(100);
    private static AtomicStampedReference<Integer> atomicStampedRef =
            new AtomicStampedReference<Integer>(100, 0);

    public static void main(String[] args) {
        atomicStampedRef.compareAndSet(100,101,1,atomicStampedRef.getStamp()+1);
        //如果当前值等于预期值，则设定为新值，成功则返回true
        int i = atomicStampedRef.getReference();
        System.out.println(i);
    }
}
