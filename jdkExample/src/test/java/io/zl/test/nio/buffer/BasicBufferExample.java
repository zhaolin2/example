package io.zl.test.nio.buffer;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * position和limit取决于当前模式是读模式还是还是写模式
 *
 * capacity:最大容量
 * position:下一个要操作的位置(无论是读或者写) 最大为capacity-1
 * limit:当前可操作的最大限制 写模式下为capacity 读模式下为写入的最大位置
 */
public class BasicBufferExample {


    @Test
    public void basicBuffer(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        printBufferProperties(byteBuffer);

        byteBuffer.putChar('a');

        printBufferProperties(byteBuffer);

        byteBuffer.flip();
        printBufferProperties(byteBuffer);


        System.out.println(byteBuffer.getChar());

        printBufferProperties(byteBuffer);

    }


    public static void printBufferProperties(Buffer buffer){
        System.out.println("[capacity:"+buffer.capacity()+",position:"+buffer.position()+",limit:"+buffer.limit()+"]");
    }

    @Test
    public void compactBuffer(){
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        buffer.put("test hello world".getBytes(StandardCharsets.UTF_8));

        buffer.flip();

        buffer.get();
        buffer.get();

        //压缩缓冲区 把后边未读的字节移动到开始为止
        buffer.compact();

        buffer.flip();
        printChar(buffer);
    }

    @Test
    public void markBuffer(){
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        buffer.put("test hello world".getBytes(StandardCharsets.UTF_8));

        buffer.flip();

        buffer.mark();
        buffer.get();
        buffer.reset();

        printChar(buffer);
    }

    public void printChar(ByteBuffer buffer){
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
    }


}
