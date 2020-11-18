package com.example.nio.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("E://test.txt");
        FileChannel fc = fin.getChannel();  //构建管道
        ByteBuffer buffer = ByteBuffer.allocate(10);   //设置缓冲区大小 10
        output("初始化", buffer);
        fc.read(buffer);  // 读
        output("读完之后", buffer);
        buffer.flip();
        output("flip之后", buffer); //准备操作之前，先锁定操作范围
        while (buffer.remaining() > 0) { //判断有没有可读数据
            byte b = buffer.get();
            System.out.print(((char) b));
        }
        output("写完之后", buffer);
        buffer.clear();  //可以理解为解锁
        output("clear之后", buffer);
        fin.close(); //最后把管道关闭



        byte array[] = new byte[10];
        ByteBuffer buffer2 = ByteBuffer.wrap( array );  // 包装一个现有的数组

    }


    /**
     * 把这个缓冲里面实时状态给答应出来
     * @param step
     * @param buffer
     */
    public static void output(String step, Buffer buffer) {
        System.out.println(step + " : ");
        System.out.print("capacity: " + buffer.capacity() + ", "); //容量，数组大小
        System.out.print("position: " + buffer.position() + ", ");  //当前操作数据所在的位置，也可以叫做游标
        System.out.println("limit: " + buffer.limit());  //锁定值，flip，数据操作范围索引只能在 position - limit 之间
        System.out.println();
    }
}
