package io.zl.test.channel;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AsyncFileChannelTest {

    @Test
    public void asyncFileChannelTest() throws IOException {
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("123"), StandardOpenOption.APPEND);

        ByteBuffer readBuffer = ByteBuffer.allocate(2048);
        fileChannel.read(readBuffer,0);


        fileChannel.read(readBuffer, 0, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result = " + result);

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });

        ByteBuffer writeBuffer = ByteBuffer.allocate(2048);
        fileChannel.write(writeBuffer,0);


        fileChannel.write(writeBuffer, 0, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("bytes written: " + result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Write failed");
                exc.printStackTrace();
            }
        });

    }
}
