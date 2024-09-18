package com.netty.file;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@Slf4j
public class FileClientHandler extends ChannelInboundHandlerAdapter {

    private int byteRead;
    private volatile Long start = 0L;
    private volatile Long lastLength = 0L;
    public RandomAccessFile randomAccessFile;
    private FileUploadFile fileUploadFile;

    public FileClientHandler(FileUploadFile ef) {
        if (ef.getFile().exists()) {
            if (!ef.getFile().isFile()) {
                log.info("Not a file :" + ef.getFile());
                return;
            }
        }
        this.fileUploadFile = ef;
    }

    public void channelActive(ChannelHandlerContext ctx) {
        try {
            randomAccessFile = new RandomAccessFile(fileUploadFile.getFile(), "r");
            randomAccessFile.seek(fileUploadFile.getStarPos());
            lastLength =  randomAccessFile.length() / 1000;
            byte[] bytes = new byte[Math.toIntExact(lastLength)];
            if ((byteRead = randomAccessFile.read(bytes)) != -1) {
                fileUploadFile.setEndPos(byteRead);
                fileUploadFile.setBytes(bytes);
                ctx.writeAndFlush(fileUploadFile);
                log.info("文件写入成功,[file.start:{},file.end:{}]",fileUploadFile.getStarPos(),fileUploadFile.getEndPos());
            } else {
                log.info("文件已经读完");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Long) {
            start = (Long) msg;
            if (start != -1) {
                randomAccessFile = new RandomAccessFile(fileUploadFile.getFile(), "r");
                randomAccessFile.seek(start);
                long a =  randomAccessFile.length() - start;
                long b =  randomAccessFile.length() / 1000;
                log.info("块儿长度：" + b);
                log.info("长度：" + a);

                if (a < b) {
                    lastLength = a;
                }
                log.info("length:{}",lastLength);
                byte[] bytes = new byte[Math.toIntExact(lastLength)];
                log.info("-----------------------------" + bytes.length);
                if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length() - start) > 0) {
                    log.info("byte 长度：" + bytes.length);
                    fileUploadFile.setEndPos(byteRead);
                    fileUploadFile.setBytes(bytes);
                    fileUploadFile.setStarPos(start);
                    try {
                        ctx.writeAndFlush(fileUploadFile);
                        log.info("文件写入成功,[file.start:{},file.end:{}]",fileUploadFile.getStarPos(),fileUploadFile.getEndPos());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    randomAccessFile.close();
                    ctx.close();
                    log.info("文件已经读完--------" + byteRead);
                }
            }
        }
    }

    // @Override
    // public void channelRead(ChannelHandlerContext ctx, Object msg) throws
    // Exception {
    // log.info("Server is speek ："+msg.toString());
    // FileRegion filer = (FileRegion) msg;
    // String path = "E://Apk//APKMD5.txt";
    // File fl = new File(path);
    // fl.createNewFile();
    // RandomAccessFile rdafile = new RandomAccessFile(path, "rw");
    // FileRegion f = new DefaultFileRegion(rdafile.getChannel(), 0,
    // rdafile.length());
    //
    // log.info("This is" + ++counter + "times receive server:["
    // + msg + "]");
    // }

    // @Override
    // public void channelReadComplete(ChannelHandlerContext ctx) throws
    // Exception {
    // ctx.flush();
    // }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    // @Override
    // protected void channelRead0(ChannelHandlerContext ctx, String msg)
    // throws Exception {
    // String a = msg;
    // log.info("This is"+
    // ++counter+"times receive server:["+msg+"]");
    // }
}
