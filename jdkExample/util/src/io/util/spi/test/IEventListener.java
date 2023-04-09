package io.util.spi.test;

public interface IEventListener<T> {

    void onEvent(T event);

}
