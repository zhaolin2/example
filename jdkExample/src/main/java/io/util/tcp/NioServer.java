package io.util.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static String httpResponse = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 38\r\n" +
            "Content-Type: text/html\r\n" +
            "\r\n" +
            "<html><body>Hello World!</body></html>";

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));

        int validOps = serverSocketChannel.validOps();
        serverSocketChannel.register(selector,validOps,null);


        while (true){
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                //怎么知道连接的是我现在处理的事件
                if (selectionKey.isAcceptable()){
                    ServerSocketChannel selectionServerSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = selectionServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);

                    System.out.println("接受连接,[address:"+socketChannel.getRemoteAddress()+"]");


                }else if (selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    ByteBuffer readBuffer = ByteBuffer.allocate(2048);
                    socketChannel.read(readBuffer);

                    String res=new String(readBuffer.array()).trim();
                    System.out.println("message:"+res);

                    if (res.contains("close")){
                        socketChannel.close();
                        System.out.println("关闭连接,[address:"+socketChannel.getRemoteAddress()+"]");
                    }else {
                        byte[] httpResponseBytes = httpResponse.getBytes(StandardCharsets.UTF_8);
                        socketChannel.write(ByteBuffer.wrap(httpResponseBytes));
                    }

                }else if (selectionKey.isWritable()){

                }

                iterator.remove();
            }
        }
    }
}
