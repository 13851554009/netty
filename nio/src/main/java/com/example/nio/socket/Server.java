package com.example.nio.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);   //发布服务
        Socket socket = server.accept();    //连接阻塞,通过accept方法获取一个连接。

        BufferedReader is = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));    //socket.getInputStream() 读阻塞
        System.out.println("Client:" + is.readLine());  //拿到客户端的信息

        PrintWriter os = new PrintWriter(socket.getOutputStream());

        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));  //socket.getOutputStream() 写阻塞
        String line = sin.readLine();
        while (!line.equals("bye")) {
            os.println(line);//输出数据
            os.flush();
            System.out.println("Server:" + line);
            System.out.println("Client:" + is.readLine());
            line = sin.readLine();
        }
        os.close();
        is.close();
        socket.close();
    }
}
