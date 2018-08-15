package com.java.container;

import java.math.BigInteger;

/**
 * Create by Administrator on 2018/8/2
 *
 * @author admin
 */
public class ExpensiveFunction implements Computable<String, BigInteger>  {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        System.out.println("正在进行计算....");
        // 在经过长时向的计算后
        return new BigInteger(arg);
    }
}
