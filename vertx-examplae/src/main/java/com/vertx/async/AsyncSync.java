package com.vertx.async;

import com.vertx.co.Runner;
import io.vertx.core.Future;
import io.vertx.core.Promise;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class AsyncSync {

    public static void main(final String[] args) throws InterruptedException {
        final String callback = hiCallback();
        System.out.println(callback);
    }

    private static String hiCallback() throws InterruptedException {
        final Future<String> response = hiChoice("Lang");
        final CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> result = new AtomicReference<>("");
        response.onComplete(res -> {
            if (res.succeeded()) {
                // Java中的限制 line = res.result();
                result.set(res.result());
            } else {
                //
                res.cause().printStackTrace();
            }
            latch.countDown();
        });
        // 手动阻塞，破坏黄金法则
//        Fn.safeJvm((JvmActuator) latch::await);
        latch.wait();
        return result.get();
    }

    static Future<String> hiChoice(final String name) {
        final Promise<String> promise = Promise.promise();
        Runner.run(() -> {
            // 当前 执行 不受影响
            System.out.println(Thread.currentThread().getName() + "，" + name);
//            Fn.safeJvm(() -> Thread.sleep(1000));
            if ("Lang".equals(name)) {
                promise.complete(name);
            } else {
                promise.fail(new RuntimeException("Exception, " + name));
            }
        }, "hiAsync");
        return promise.future();
    }
}
