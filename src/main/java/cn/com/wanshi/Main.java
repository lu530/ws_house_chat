package cn.com.wanshi;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static void handler(Socket clientSocket) throws Exception {
        byte[] bytes = new byte[1024];
        System.out.println("准备read。。。");
        int read = clientSocket.getInputStream().read(bytes);
        System.out.println("read完毕！");
        if(read != -1){
            System.out.println("接收到客户端的数据：" + new String(bytes, 0, read));
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8001);
        while(true){
            System.out.println("等待连接。。。");
            // 阻塞住了
            Socket clientSocket = serverSocket.accept();

            System.out.println("有客户端连接了。。。");

            handler(clientSocket);
        }
    }

}