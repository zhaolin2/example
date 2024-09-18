package com.netty.file;

import com.netty.constant.NettyConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileServer {
    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap
                    .group(loopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ServerInit());

            ChannelFuture future = bootstrap.bind(NettyConstants.port).sync();
            log.info("绑定端口成功");

            future.channel().closeFuture().sync();
            log.info("关闭成功");
        } finally {
            loopGroup.shutdownGracefully();
        }


    }
}
