package com.vertx.async;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;

public class AsyncCall {

    public static void main(final String[] args) {
        hiCallback(handler -> {
            if (handler.succeeded()) {
                System.out.println(handler.result());
            } else {
                handler.cause().printStackTrace();
            }
        });
    }

    private static void hiCallback(final Handler<AsyncResult<String>> handler) {

        Promise<String> promise = Promise.promise();
        promise.complete("Huan");

        Future<String> future = promise.future();
        future.onComplete(handler);
    }

}
