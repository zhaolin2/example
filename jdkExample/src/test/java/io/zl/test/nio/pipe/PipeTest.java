package io.zl.test.nio.pipe;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;

public class PipeTest {

    @Test
    public void pipeTest() throws IOException {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sinkChannel = pipe.sink();

        sinkChannel.write(ByteBuffer.wrap("test".getBytes(StandardCharsets.UTF_8)));

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        Pipe.SourceChannel sourceChannel = pipe.source();
        sourceChannel.read(readBuffer);
        System.out.println(readBuffer.position());


    }
}
