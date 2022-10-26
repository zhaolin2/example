package com.vertx.threadsafe;

import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;

public class BlockCodeMain {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.executeBlocking(promise -> {
            promise.complete("23");
        },res -> {
            System.out.println("res:"+res);
        });

        WorkerExecutor executor = vertx.createSharedWorkerExecutor("123");
        executor.executeBlocking(promise -> {
            promise.complete("123");
        },res -> {
            System.out.println("123"+res);
        });


        executor.close();
    }
}
