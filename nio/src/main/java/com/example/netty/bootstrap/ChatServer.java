package com.example.netty.bootstrap;

public class ChatServer {

    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception { ...}
            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("服务已启动,监听端口" + port + "");

            ChannelFuture f = b.bind(port).sync(); // 绑定端口，开始接收进来的连接
            f.channel().closeFuture().sync();   // 等待服务器 socket 关闭

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("服务已关闭");
        }
    }

    public static void main(String[] args) {
        try {
            new ChatServer().start(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
