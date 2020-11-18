package com.example.nio.demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        /* 获取selector对象，注册监听:连接就绪事件 */
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        /* 发起连接 */
        socketChannel.connect(new InetSocketAddress("localhost", 8888));

        while (true) {
            selector.select(1000);
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeySet) {
                if (!selectionKey.isValid()) {
                    continue;
                }
                if (selectionKey.isConnectable()) {
                    if (socketChannel.finishConnect()) {
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        doWriteRequest(((SocketChannel) selectionKey.channel()));
                    }
                }
                if (selectionKey.isReadable()) {
                    doRead(selectionKey);
                }
            }
            selectionKeySet.removeAll(selectionKeySet);    //清除所有的key
        }
    }

    /*写*/
    private static void doWriteRequest(SocketChannel socketChannel) throws Exception {
        System.err.println("start connect...");
        ByteBuffer byteBuffer = ByteBuffer.allocate("Hello Server!".getBytes().length);
        byteBuffer.put("Hello Server!".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        if (!byteBuffer.hasRemaining()) {
            System.err.println("Send request success...");
        }
    }

    /*读*/
    private static void doRead(SelectionKey selectionKey) throws Exception {
        SocketChannel socketChannel = ((SocketChannel) selectionKey.channel());
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len = socketChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), 0, len));
    }
}
