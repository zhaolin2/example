package io.zl.test.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SocketChannelTest {


    private static byte[] request = null;

    static {
        StringBuffer temp = new StringBuffer();
        temp.append("GET http://localhost:8080/test HTTP/1.1\r\n");
        temp.append("Host: 127.0.0.1:8080\r\n");
        temp.append("Connection: keep-alive\r\n");
        temp.append("Cache-Control: max-age=0\r\n");
        temp
                .append("User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.47 Safari/536.11\r\n");
        temp
                .append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
        temp.append("Accept-Encoding: gzip,deflate,sdch\r\n");
        temp.append("Accept-Language: zh-CN,zh;q=0.8\r\n");
        temp.append("Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n");
        temp.append("\r\n");
        request = temp.toString().getBytes();
    }

    @Test
    public void baseTest() throws IOException, InterruptedException {
        Selector selector = Selector.open();


        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_READ);

        while (!socketChannel.finishConnect()) {
            System.out.println("未完成链接");
            Thread.sleep(200);
        }

        ByteBuffer requestBuffer = ByteBuffer.wrap(request);
        socketChannel.write(requestBuffer);

        try {
            int select = selector.select(2000);
            if (select == 0) {
                System.out.println("请求超时");
                socketChannel.close();
                return;
            }
            // 遍历每个有IO操作Channel对应的SelectionKey
            for (SelectionKey sk : selector.selectedKeys()) {
                if (sk.isReadable()) {
                    // 使用NIO读取Channel中的数据
                    SocketChannel sc = (SocketChannel) sk.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    sc.read(buffer);
                    buffer.flip();
                    String receivedString = StandardCharsets.UTF_8.newDecoder().decode(buffer).toString();
                    System.out.println("接收到来自服务器：" + sc.socket().getRemoteSocketAddress() + "的信息：" + receivedString);
                    sk.interestOps(SelectionKey.OP_READ);
                    buffer.clear();
                }
                selector.selectedKeys().remove(sk);
            }

        } finally {
            socketChannel.close();
        }


    }
}
