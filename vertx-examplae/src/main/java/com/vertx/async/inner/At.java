package com.vertx.async.inner;

import com.vertx.co.Runner;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public abstract class At {
    public static void hiAsync(final String name,
                        final Handler<AsyncResult<String>> handler) {
        // 每个人开一个线程执行
        Runner.run(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + name);
            handler.handle(Future.succeededFuture("Hi, " + name));
        }, name);
    }

    public static Future<String> hiAsync(final String name) {
        final Promise<String> promise = Promise.promise();
        hiAsync(name, promise);
        return promise.future();
    }

    public static Future<String> hiAsyncError(final String name) {
        final Promise<String> promise = Promise.promise();
        Runner.run(() -> {
            System.out.println(Thread.currentThread().getName() + "，" + name);
            promise.fail(name);
        }, "hiAsync");
        return promise.future();
    }

    public static Future<String> hiAsync(final Supplier<String> supplier) {
        final Promise<String> promise = Promise.promise();
        Runner.run(() -> {
            final String name = supplier.get();
            System.out.println(Thread.currentThread().getName() + "，" + name);
            promise.complete(name);
        }, "hiAsync");
        return promise.future();
    }
}
