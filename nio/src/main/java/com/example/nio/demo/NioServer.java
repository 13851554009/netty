package com.example.nio.demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws Exception {
        /* 获取Channel对象,绑定端口，并设置byte大小,非阻塞模式  */
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888), 1024);
        serverSocketChannel.configureBlocking(false);

        /* 获取selector对象，注册监听:接收连接事件 */
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //通过注册监听channel，监听accept状态
        while (true) {
            selector.select(1000);    // 该调用会阻塞1S，直到至少有一个事件发生
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();  //获取准备就绪的channel，SelectionKey中包含channel信息

            for (SelectionKey selectionKey : selectionKeySet) {
                if (!selectionKey.isValid()) {        //判断Channel是否有效
                    continue;
                }
                /* 根据Channel就绪的事件，做对应处理 */
                if (selectionKey.isAcceptable()) {    // 连接状态
                    acceptConnection(selectionKey, selector);
                }
                if (selectionKey.isReadable()) {    //可读状态
                    System.out.println(readFromSelectionKey(selectionKey));
                }
            }
            selectionKeySet.clear();    //清空set
        }
    }

    /*客户端建立连接*/
    private static void acceptConnection(SelectionKey selectionKey, Selector selector) throws Exception {
        System.err.println("accept connection...");
        ServerSocketChannel ssc = ((ServerSocketChannel) selectionKey.channel()); //建立连接时，只会有ServerSocketChannel会触发，所以转成ServerSocketChannel
        SocketChannel socketChannel = ssc.accept();    // 获取客户端的SocketChannel
        socketChannel.configureBlocking(false);        //设置成非阻塞

        socketChannel.register(selector, SelectionKey.OP_READ); //再次监听，监听read状态，如果该channel有数据读取时，会被select出来
    }

    /*读取数据*/
    private static String readFromSelectionKey(SelectionKey selectionKey) throws Exception {
        SocketChannel socketChannel = ((SocketChannel) selectionKey.channel());
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);    //读取数据到buffer
        int len = socketChannel.read(byteBuffer);    //从buffer中读取数据
        if (len < 0) {    //如果读到-1，说明数据以及传输完成了，可以关闭
            socketChannel.close();
            selectionKey.cancel();    //使selectionKey失效
            return "";
        } else if (len == 0) { //说明没有读到数据
            return "";
        }
        byteBuffer.flip();
        doWrite(selectionKey, "Hello Nio");
        return new String(byteBuffer.array(), 0, len);
    }

    private static void doWrite(SelectionKey selectionKey, String responseMessage) throws Exception {
        System.err.println("Output message...");
        SocketChannel socketChannel = ((SocketChannel) selectionKey.channel());
        ByteBuffer byteBuffer = ByteBuffer.allocate(responseMessage.getBytes().length);
        byteBuffer.put(responseMessage.getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

}
