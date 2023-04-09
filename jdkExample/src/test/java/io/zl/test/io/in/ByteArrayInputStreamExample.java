package io.zl.test.io.in;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ByteArrayInputStreamExample {

    @Test
    public void testRead() throws IOException {
        ByteArrayInputStream byteArrayInputStream = getInputStream();
        byte[] bytes=new byte[2048];
        System.out.println("打印读取之前的参数,[byteStream:"+byteArrayInputStream.available());

        byteArrayInputStream.read(bytes);

        System.out.println("打印读取之后的参数,[byteStream:"+byteArrayInputStream.available());

        System.out.println(byteArrayInputStream.available());

        byteArrayInputStream.reset();
        System.out.println("打印reset之后的参数,[byteStream:"+byteArrayInputStream.available());
    }

    public ByteArrayInputStream getInputStream(){
        byte[] bytes = "hello".getBytes(StandardCharsets.UTF_8);
        return new ByteArrayInputStream(bytes);
    }
}
