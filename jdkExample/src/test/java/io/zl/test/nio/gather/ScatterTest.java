package io.zl.test.nio.gather;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class ScatterTest {

    /**
     * channel 同时 读取/写入 多个buffer
     */
    @Test
    public void scatterExample() throws IOException {
        ByteBuffer headerBuffer = ByteBuffer.allocate(20);
        ByteBuffer bodyBuffer = ByteBuffer.allocate(30);

        FileChannel open = FileChannel.open(Paths.get(""));
        ByteBuffer[] buffers={headerBuffer,bodyBuffer};
        open.read(buffers);

        open.write(buffers);
    }
}
