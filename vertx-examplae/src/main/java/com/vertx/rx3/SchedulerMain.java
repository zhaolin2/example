package com.vertx.rx3;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Schedulers.computation()：在后台在固定数量的专用线程上运行计算密集型工作。大多数异步运算符都使用它作为默认值Scheduler。
 * Schedulers.io()：在一组动态变化的线程上运行类似 I/O 或阻塞操作。
 * Schedulers.single()：以顺序和先进先出的方式在单个线程上运行工作。
 * Schedulers.trampoline()：在其中一个参与线程中以顺序和 FIFO 方式运行工作，通常用于测试目的。
 */
public class SchedulerMain {
    public static void main(String[] args) throws InterruptedException {
        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish


        Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> v * v)
                .sequential()
                .blockingSubscribe(System.out::println);
    }
}
