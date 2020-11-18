package com.example.nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedBuffer {
    static private final int start = 0, size = 1024;

    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("E://test.txt", "rw");  // 定义文件
        FileChannel fc = raf.getChannel();

        /* 把缓冲区跟文件系统进行映射关联 */
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
        mbb.put(0, (byte) 97);
        mbb.put(1023, (byte) 122);  // 文件内容 随着操作缓冲区内容的改变 而改变
        raf.close();
    }
}
