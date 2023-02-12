package io.zl.test.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class DatagramChannelTest {

    public void datagramChannel() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.socket().bind(new InetSocketAddress("127.0.0.1",9999));

        ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
        datagramChannel.read(receiveBuffer);


        datagramChannel.send(ByteBuffer.wrap("test message".getBytes(StandardCharsets.UTF_8)),new InetSocketAddress(90));

        datagramChannel.connect(new InetSocketAddress(80));

    }
}
