package com.netty.file;

import com.netty.constant.NettyConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class FileClient {
    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup executors = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(executors)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientInit());


            ChannelFuture sync = bootstrap.connect("127.0.0.1", NettyConstants.port).sync();
            sync.channel().closeFuture().sync();
        } finally {
            executors.shutdownGracefully();
        }

    }
}
