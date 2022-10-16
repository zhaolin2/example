package com.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;

public class BlockReceiverVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
//        Vertx vertx  = Vertx.vertx();
        EventBus eventBus = vertx.eventBus();
//        MessageConsumer<String> consumer = eventBus.consumer("sleep");
//
//        consumer.handler(message -> {
//            String body = message.body();
//            System.out.println("receiveMessage,body"+body);
//            message.reply("111");
//        });

        eventBus.<String>consumer("sleep",hanlder -> {
            String address = hanlder.address();
            String body = hanlder.body();
            System.out.println("收到消息,address:"+address+",body:"+body);

            hanlder.reply("123");
            System.out.println("回复消息");
        });
    }
}
