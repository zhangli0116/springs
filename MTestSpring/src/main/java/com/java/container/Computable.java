package com.java.container;

/**
 * Create by Administrator on 2018/8/2
 *
 * @author admin
 */
public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
