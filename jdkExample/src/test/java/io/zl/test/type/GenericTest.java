package io.zl.test.type;

import io.util.provide.EchoListener;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class GenericTest {

    @Test
    public void testGeneric() throws NoSuchMethodException {
        EchoListener echoListener = new EchoListener();

        Method method = echoListener.getClass().getMethod("onEvent");

        Type returnType = method.getGenericReturnType();

    }
}
