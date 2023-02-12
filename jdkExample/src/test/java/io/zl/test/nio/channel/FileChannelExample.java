package io.zl.test.nio.channel;


import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannelExample {

    String resourceFilePath = "test.txt";

    @Test
    public void fileChannelReadTest() throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(2048);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(getRealPath(), "r")) {
            FileChannel channel = randomAccessFile.getChannel();


            int read = channel.read(buffer);

            while (read != -1) {
                System.out.println("readSize:" + read);

                buffer.flip();

                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }

                buffer.clear();
                read = channel.read(buffer);
            }

        }finally {
            buffer.clear();
        }

    }

    public String getRealPath(){
        return this.getClass().getClassLoader().getResource(resourceFilePath).getPath();
    }

    /**
     * 清空并插入
     * @throws IOException
     */
    @Test
    public void fileChannelWriteTest() throws IOException {

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(getRealPath(), "rw")) {
            FileChannel channel = randomAccessFile.getChannel();

            ByteBuffer wrap = ByteBuffer.wrap("test write".getBytes(StandardCharsets.UTF_8));

            //默认从文件头开始重写 应该是默认为0
            int writeSize = channel.write(wrap);
            System.out.println("[writeSize:"+writeSize+"]");

        }

    }

    /**
     * 尾端插入
     * @throws IOException
     */
    @Test
    public void fileChannelWriteAppendTest() throws IOException {

        try (FileChannel fileChannel = FileChannel.open(Paths.get(getRealPath()), StandardOpenOption.APPEND)) {

            ByteBuffer wrap = ByteBuffer.wrap("test write".getBytes(StandardCharsets.UTF_8));

            int writeSize = fileChannel.write(wrap);
            System.out.println("[writeSize:"+writeSize+"]");
        }

    }
}
