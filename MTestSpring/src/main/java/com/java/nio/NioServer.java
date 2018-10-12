package com.java.nio;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.expression.spel.ast.Selection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Create by Administrator on 2018/9/27
 *
 * @author admin
 */
public class NioServer {
    private static final int BUF_SIZE = 1024;
    private static final int PORT = 8888;
    private static final int TIMEOUT = 3000;

    /**
     * 观察什么时候会触发accept?
     * 通过建立2个客户端观察，在客户端与服务端第一次建立连接时会触发
     * @param key
     * @throws IOException
     */
    public static void handleAccept(SelectionKey key) throws IOException{
        System.out.println("handleAccept...");
        ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
        SocketChannel sc = ssChannel.accept();
        //设置非阻塞通道
        sc.configureBlocking(false);//注册通道的时候可以附加对象
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);
        String str = "正在接受数据..";
        byteBuffer.put(str.getBytes());
        sc.register(key.selector(),SelectionKey.OP_READ,byteBuffer);
    }

    /**
     * 建立连接后，每次往服务端发生信息会触发
     * @param key
     * @throws IOException
     */
    public static void handleRead(SelectionKey key) throws IOException{
        System.out.println("handleRead...");
        SocketChannel sc = (SocketChannel)key.channel();
        //获得附加的对象
        ByteBuffer buf = (ByteBuffer) key.attachment();
        //将管道中数据读出到缓存中
        long bytesRead = sc.read(buf);
        while(bytesRead > 0){
            //position值设为0，limit设为之前position的位置
            buf.flip();
            //position和limit之间是否还有元素,逐个字节读取
           /* while(buf.hasRemaining()){
                System.out.println((char)buf.get());
            }*/
            //解码
            CharBuffer charBuffer = Charset.forName("utf-8").decode(buf);
            System.out.println(charBuffer.toString());
            //Prepare buffer for reading
            buf.clear();
            bytesRead = sc.read(buf);
        }
        if(bytesRead == -1){
            sc.close();
        }
    }

    /**
     * ?
     * @param key
     * @throws IOException
     */
    public static void handleWrite(SelectionKey key) throws IOException{
        System.out.println("handleWrite.....");
        ByteBuffer buf = (ByteBuffer)key.attachment();
        buf.flip();
        SocketChannel sc =(SocketChannel)key.channel();
        while(buf.hasRemaining()){
            sc.write(buf);
        }
        buf.compact();
    }


    public static void selector(){
        System.out.println("selector...");
        try(Selector selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
        ){
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            //第一个参数 将Channel注册到Selector上   ; 第二个参数 意味着selector在监听channel时感兴趣的事件 有4个事件：Connect连接就绪,Accept接受就绪,Read读就绪,Write写就绪
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                //select() 有不为0的返回值，表示有一个或多个通道准备就绪
                if(selector.select(TIMEOUT) == 0){
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iter =  selector.selectedKeys().iterator();
                while(iter.hasNext()){
                    //通过SelectionKey 可以获得interest集合(4个事件状态),Selector,Channel
                    SelectionKey key = iter.next();
                    if(key.isAcceptable()){
                        handleAccept(key);
                    }
                    if(key.isReadable()){
                        handleRead(key);
                    }
                    if(key.isWritable() && key.isValid()){
                        handleWrite(key);
                    }
                    if(key.isConnectable()){
                        System.out.println("isConnectable = true");
                    }
                    iter.remove();
                }


            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        NioServer.selector();
        int i = ByteBuffer.allocate(10240).limit();
        System.out.println(i);
    }

}
