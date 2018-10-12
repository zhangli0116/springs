package com.java.nio;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

/**
 * Create by Administrator on 2018/9/26
 * java nio 异步IO 测试
 * @author admin
 */
public class NIOTest {

    @Test
    public void test1() throws  Exception{
        RandomAccessFile aFile =  new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\filter.log","rw");
        FileChannel fileChannel = aFile.getChannel();
        //分配空间
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将数据写入
        int bytesRead = fileChannel.read(byteBuffer);
        while(bytesRead!=-1){
            //如果没有，就是从文件最后开始读取的，当然读出来的都是byte=0时候的字符，将指针重新指向开头
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()){
                System.out.println((char) byteBuffer.get());
            }
            //position指到最后一个未读元素的正后面，limit指向capacity
            byteBuffer.compact();//与byteBuffer.clear()区别
            bytesRead = fileChannel.read(byteBuffer);
        }


    }

}
