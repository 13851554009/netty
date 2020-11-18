package com.example.nio.buffer;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        ByteBuffer readonly = buffer.asReadOnlyBuffer();    // 从常规缓冲区中 迁出 只读分区

//        for (int i=0; i< readonly.capacity(); ++i) {
//            byte b = readonly.get( i );
//            b *= 10;
//            readonly.put( i, b );   // 修改只读缓冲区
//        }


        for (int i=0; i< buffer.capacity(); ++i) {
            byte b = buffer.get( i );
            b *= 10;
            buffer.put( i, b );     // 修改原缓冲区
        }
        readonly.position(0);
        readonly.limit(buffer.capacity());

        while (readonly.remaining()>0) {
            System.out.print(readonly.get()+" ");   // 只读缓冲区也被修改了
        }


    }
}
