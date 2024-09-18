package com.netty.ws;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.vertx.core.http.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyWebSocketHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof TextWebSocketFrame){
            String text = ((TextWebSocketFrame) msg).text();
            log.info("接收信息成功,txt:{}",text);
            ctx.writeAndFlush(new TextWebSocketFrame("收到(fromServer):"+text));
        }
    }
}
