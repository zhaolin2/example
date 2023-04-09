package io.util.spi;

import io.util.spi.test.IEventListener;

import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class SPIMain {
    public static void main(String[] args) {
        ServiceLoader<IEventListener> load = ServiceLoader.load(IEventListener.class);
//        var listeners2 = ServiceLoader.load(IEventListener.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
//
//        for (IEventListener iEventListener : listeners2) {
//            iEventListener.onEvent("ajhsfgahfg");
//        }
        load.iterator().forEachRemaining(listener -> {
            listener.onEvent("ajsfgsdhjfghjs");
        });
    }
}
