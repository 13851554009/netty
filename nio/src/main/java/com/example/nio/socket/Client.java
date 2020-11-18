package com.example.nio.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);//连接服务器
        BufferedReader is = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));//read阻塞，接受信息

        PrintWriter os = new PrintWriter(socket.getOutputStream());
        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
        String line = sin.readLine();
        while (!line.equals("bye")) {
            os.println(line);  //发送数据
            os.flush();
            System.out.println("Client:" + line);
            System.out.println("Server:" + is.readLine());
            line = sin.readLine();
        }
    }
}
