package com.vertx.async;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.up.runtime.Runner;

public class AsyncOther {

    public static void main(final String[] args) {
        hiAsyncError("Lang").compose(result -> {
            // 这里不再输出
            System.out.println(result);
            return Future.succeededFuture();
        }).compose(res -> {
            System.out.println("res");
            return Future.succeededFuture();
            //代码分支 为之前的异常处理
        }).otherwise(ex -> {
            ex.printStackTrace();
            return "Huan";
        })
                .compose(finalResult -> {
            // 受影响的位置
            System.err.println(finalResult);
            return Future.succeededFuture();
        });

        hiAsyncError("123").compose((res) -> {
            System.out.println("123");
            return Future.succeededFuture("123");
        },ex -> {
            ex.printStackTrace();
            return Future.failedFuture(ex);
        }).onComplete(r -> {
            System.out.println("complete");
            System.out.println(r);
        });

//        hiAsyncError("123").map(Integer::new).com
    }

    static Future<String> hiAsyncError(final String name) {
        final Promise<String> promise = Promise.promise();
        Runner.run(() -> {
            // 当前 执行 不受影响
            System.out.println(Thread.currentThread().getName() + "，" + name);
            promise.fail(new RuntimeException("Exception, " + name));
        }, "hiAsync");
        return promise.future();
    }
}
