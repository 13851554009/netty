package com.example.nio.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileInputDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("E://test.txt");
        FileChannel fc = fin.getChannel();  // 获取通道
        ByteBuffer buffer = ByteBuffer.allocate(1024);  // 创建缓冲区
        fc.read(buffer);    // 将数据从channel 读到 buffer
        buffer.flip();
        while (buffer.remaining() > 0) {
            byte b = buffer.get();
            System.out.print(((char) b));
        }
        fin.close();
    }
}
