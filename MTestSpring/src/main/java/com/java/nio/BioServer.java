package com.java.nio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Create by Administrator on 2018/9/27
 *  传统Socket服务端
 * @author admin
 */
public class BioServer {
    //private static Logger logger = LoggerFactory.getLogger(NioServer.class);
    public static void oldServer(){
        try(ServerSocket serverSocket = new ServerSocket(8888);
        ){
            int recvMgsSize = 0;
            byte[] recvBuf = new byte[1024];
            while(true){
                Socket clntSocket = serverSocket.accept();
                SocketAddress  clientAddress = clntSocket.getRemoteSocketAddress();
                //logger.info("Handling client at " + clientAddress);
                System.out.println("Handling client at " + clientAddress);
                InputStream inputStream = clntSocket.getInputStream();
                while((recvMgsSize = inputStream.read(recvBuf))!=-1){
                    byte[] temp = new byte[recvMgsSize];
                    System.arraycopy(recvBuf,0,temp,0,recvMgsSize);
                    //logger.info(new String(temp));
                    System.out.println(new String(temp));
                }
                inputStream.close();
            }

        }catch (Exception e){
            //logger.error("服务端发生异常错误",e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BioServer.oldServer();
    }

}
