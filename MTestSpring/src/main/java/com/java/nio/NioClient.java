package com.java.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/9/27
 * SocketChannel测试
 * @author admin
 */
public class NioClient {
    //private static Logger logger = LoggerFactory.getLogger(NioClient.class);
    public static void client(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try(SocketChannel socketChannel = SocketChannel.open()){
            socketChannel.configureBlocking(false);//非阻塞通道
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8888));
            if(socketChannel.finishConnect()){
                int i=0;
                //每一秒不断发送信息
                while (true){
                    TimeUnit.SECONDS.sleep(1);
                    String info = "I'm"+ (i++)+"-th information from client";
                    byteBuffer.clear();
                    byteBuffer.put(info.getBytes());
                    byteBuffer.flip();//将position设置为0，limit设置为之前position的位置
                    while(byteBuffer.hasRemaining()){
                        //logger.info(byteBuffer.toString());
                        System.out.println(byteBuffer.toString());
                        socketChannel.write(byteBuffer);
                    }
                }

            }
        }catch (Exception e){
            //logger.error("客户端发生异常错误",e);
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        NioClient.client();
    }
}
