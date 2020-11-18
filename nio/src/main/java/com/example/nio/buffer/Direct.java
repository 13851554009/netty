package com.example.nio.buffer;

public class Direct {
    public static void main(String[] args) {
        long startTime2 = System.currentTimeMillis();
        ByteBuf buffer = null;
        for (int i = 0; i < loop; i++) {
            buffer = Unpooled.directBuffer(1024);
            buffer.writeBytes(CONTENT);
        }
        endTime = System.currentTimeMillis();
        System.out.println("非内存池分配缓冲区耗时" + (endTime - startTime2) + "ms.");
    }
}
