package com.example.nio.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileOutputDemo {
    static private final byte message[] = {83, 111, 109, 101, 32, 98};

    static public void main(String args[]) throws Exception {
        FileOutputStream fout = new FileOutputStream("E://test.txt");
        FileChannel fc = fout.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for (int i = 0; i < message.length; ++i) {
            buffer.put(message[i]);
        }
        buffer.flip();
        fc.write(buffer);   // 数据从 Channel 写入到 Buffer 中
        fout.close();
    }
}
