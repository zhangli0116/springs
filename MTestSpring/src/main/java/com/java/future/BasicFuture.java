package com.java.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/8/9
 * 可完成的future jdk1.8
 * @author admin
 */
public class BasicFuture {
    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();

    static int getMoreData()  {
        System.out.println("begin to start compute");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end to compute,passed " + (System.currentTimeMillis()-t));
        return rand.nextInt(1000);
    }

    public static void main(String[] args) throws Exception{
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(BasicFuture::getMoreData);
        Future<Integer> f = future.whenComplete((v, e)->{
            System.out.println(v);
            System.out.println(e);
        });
        System.out.println(f.get());


    }

}
