package com.vertx.async;

import io.vertx.core.Future;
import io.vertx.up.atom.Refer;

public class FutureFirst {
    public static void main(final String[] args) {
        final Future<String> success = Future.succeededFuture("Ok!");
        final Future<String> failure = Future.failedFuture("Ko!");
        System.out.println(success);
        System.out.println(failure);

        Refer refer = new Refer();
        refer.add("123");

//        Promise<Object> promise = Promise.promise();
//        promise.complete("123");
    }
}
