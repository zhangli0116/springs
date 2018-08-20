package com.java.container;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Create by Administrator on 2018/8/9
 *
 * @author admin
 */
public class Memorizer4<A,V>{
    private Map<A,Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;
    public Memorizer4(Computable<A, V> c) {
        this.c = c;
    }


    public V compute(A arg) throws InterruptedException {
        while(true){
            Future<V> f = cache.get(arg);
            if(f == null){
                FutureTask<V> ft = new FutureTask<V>(()->{
                    return c.compute(arg);
                });
                f = cache.putIfAbsent(arg,ft);
                if(f == null){
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            }  catch(CancellationException e){
                cache.remove(arg,f);
            } catch(ExecutionException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        ExpensiveFunction function = new ExpensiveFunction();
        Memorizer4<String,BigInteger> m = new Memorizer4<>(function);
        ExecutorService pool = Executors.newCachedThreadPool();
        //开10个线程去执行
        for(int i =0;i<10;i++){
           Future<BigInteger> future = pool.submit(()->{
                return m.compute("100");
            });
        }
        pool.shutdown();
    }
}
