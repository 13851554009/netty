package com.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectorDemo {

    private int port;

    private Selector getSelector() throws IOException {

        /* 获取通道，设置成非阻塞模式 */
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);

        ServerSocket socket = channel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        socket.bind(address);   // 绑定端口

        /* 获取selector对象，注册监听 */
        Selector sel = Selector.open();
        channel.register(sel, SelectionKey.OP_ACCEPT);

        return sel;

    }
}
