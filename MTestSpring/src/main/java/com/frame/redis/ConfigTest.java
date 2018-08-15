package com.frame.redis;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/8/13
 * 测试redisson使用
 * @author admin
 */
public class ConfigTest {

    @Test
    public void test1(){
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress("redis://192.168.56.128:7000", "redis://192.168.56.128:7001")
                .addNodeAddress("redis://192.168.56.128:7002");
        RedissonClient redisson =  Redisson.create(config);
        RMap<String, String> map = redisson.getMap("anyMap");
        //映射字段锁 锁住某个key
        RLock lock = map.getLock("key1");
        lock.lock();
        try{
            String str = map.get("key1");
            System.out.println(str);
        }finally {
            lock.unlock();
        }



    }

    @Test
    public void test2(){
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress("redis://192.168.56.128:7000", "redis://192.168.56.128:7001")
                .addNodeAddress("redis://192.168.56.128:7002");
        RedissonClient redisson =  Redisson.create(config);
        RMapCache<String,String> mapCache = redisson.getMapCache("anyMap");
        mapCache.delete();
        //mapCache.put("key1","key1",5,TimeUnit.MINUTES);
        //String value = mapCache.get("key1");
        //System.out.println(value);
        RBucket<String> rBucket = redisson.getBucket("bucket");
        boolean flag = rBucket.trySet("test",3, TimeUnit.MINUTES);
        if(flag){
            System.out.println("插入成功");
        }else{
            System.out.println("数据库中已有值，插入失败");
        }

    }


    public static void main(String[] args) {
       /* Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress("redis://127.0.0.1:7000", "redis://127.0.0.1:7001")
                .addNodeAddress("redis://127.0.0.1:7002");*/


        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.56.128:6379");
        RedissonClient redisson = Redisson.create(config);
        RedissonReactiveClient client =  Redisson.createReactive(config);
        RAtomicLongReactive longObject = client.getAtomicLong("mylong");
        // 异步流执行方式
        Publisher<Boolean> csPublisher = longObject.compareAndSet(3,401); //仅当当前值==预期值时，原子性地将值设置为给定的更新值。
        Publisher<Long> getPublisher = longObject.get();
        System.out.println(getPublisher);


        client.shutdown();

    }
}
