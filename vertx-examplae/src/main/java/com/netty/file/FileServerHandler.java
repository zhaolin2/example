package com.netty.file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;

@Slf4j
public class FileServerHandler extends ChannelInboundHandlerAdapter {

    private long byteRead;
    private volatile long start = 0;
    private String file_dir = "D:";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);

        if (msg instanceof FileUploadFile){
            FileUploadFile uploadFile= (FileUploadFile) msg;
            log.info("准备写入文件,[file.start:{},file.end:{}]",uploadFile.getStarPos(),uploadFile.getEndPos());

            byteRead = uploadFile.getEndPos();
            long starPos = uploadFile.getStarPos();
            byte[] bytes = uploadFile.getBytes();
            String file_md5 = uploadFile.getFile_md5();

            String path=file_dir + File.separator + file_md5;;

            RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rw");

            randomAccessFile.seek(starPos);
            randomAccessFile.write(bytes);

            starPos=starPos+byteRead;

            if (byteRead>0){
                ctx.writeAndFlush(starPos);
            }else {
                randomAccessFile.close();
                ctx.close();
            }

        }else {
            log.info("当前消息不是file 无法处理 msg:{}",msg);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
