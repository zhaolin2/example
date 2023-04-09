package io.util.provide;

import io.util.spi.test.IEventListener;

public class EchoListener implements IEventListener<String> {


    @Override
    public void onEvent(String event) {
        System.out.println("[echo]spi:"+event);
    }
}
