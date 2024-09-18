package com.netty.file;

import io.netty.channel.*;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class ClientInit extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

//        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));

        FileUploadFile uploadFile = new FileUploadFile();
        File file = new File("F:\\back\\11.mkv");
//        File file = new File("F:\\back\\11.mkv");
        uploadFile.setFile(file);
        uploadFile.setFile_md5(file.getName());
        uploadFile.setStarPos(0);

        pipeline.addLast(new FileClientHandler(uploadFile));

        log.info("初始化client的pipline完毕");
    }
}
