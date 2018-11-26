package com.java.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Create by Administrator on 2018/11/26
 *
 * @author admin
 */
public class CompletableFutureExample {
    public static void test1() throws Exception{
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,60, TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(20));
        //一定需要executor参数？
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "a";
        }).thenApply(s->s.toUpperCase());
       future.whenComplete((r,e)->{
            System.out.println(r);
            executor.shutdown();
        });
        System.out.println("i am ok ");
    }


    public static void test2(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,60, TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(20));
        String[] list = {"a","b","c"};
        List<CompletableFuture> completableFutures = new ArrayList<>();
        for(String str : list){
            completableFutures.add(
                    CompletableFuture.supplyAsync(()->{
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return str;
                    },executor).thenApply(e->e.toUpperCase())
            );

        }
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]))
                .whenComplete((v,th)->{
                    System.out.println("complated");
                    //allOf 生成CompletableFuture<Void>，在所有completableFuture执行完成后结束，不会传递任何执行结果，
                    System.out.println(v);
                    executor.shutdown();
                });

        System.out.println("i am ok ");
    }

    public static void test3(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        //supplyAsync 构造一个CompletableFuture
        //CompletableFuture.completedFuture() 构造一个已完成的future
        List<CompletableFuture> completableFutures =  list.stream().map(e->CompletableFuture.supplyAsync(()-> {
            //异步任务执行
            try {
                //模拟耗时任务
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return e;
        }).thenApply(r->r.toUpperCase())).collect(Collectors.toList());
        //阻塞主线程直到所有全部任务完成
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
        completableFutures.stream().forEach(e->{
            try {
                //获得执行结果
                System.out.println("执行结果" + e.get());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                e1.printStackTrace();
            }
        });


    }

    public static void test4(){
        CompletableFuture.supplyAsync(()-> "hello")
                .thenCombine(CompletableFuture.completedFuture("world"),(s1,s2)->s1+s2)
                .whenComplete((e,r)-> System.out.println(e));

        CompletableFuture.supplyAsync(()-> "hello")
                .thenApply( e -> e.toUpperCase())
                .thenAccept(e -> System.out.println(e));

    }

    public static void main(String[] args) throws Exception {
        Long start = System.currentTimeMillis();
        test4();
        Long end = System.currentTimeMillis();
        System.out.println(end - start +"ms");

    }
}
