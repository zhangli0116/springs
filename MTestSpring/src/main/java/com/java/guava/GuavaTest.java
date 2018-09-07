package com.java.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Administrator on 2018/9/4
 * 布隆过滤器 -- 快速判断一个元素是非存在于某集合中
 * @author admin
 */
public class GuavaTest {

    @Test
    public void test1(){
        int size = 1000000;
        //new 一个布隆过滤器
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),size,0.01);
        //向布隆过滤器中添加元素
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
        List<Integer> list = new ArrayList<Integer>();
        for(int i =size+10000;i<size+20000;i++){
            if(bloomFilter.mightContain(i)){
                list.add(i);
            }
        }
        System.out.println("误判率" + list.size()/10000.0);




    }



}
