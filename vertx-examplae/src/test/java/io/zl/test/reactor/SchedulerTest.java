package io.zl.test.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;

public class SchedulerTest {

    public static void main(String[] args) throws InterruptedException {
        final Mono<String> mono = Mono.just("hello ");

        Thread t = new Thread(() -> mono
                .map(msg -> msg + "thread ")
                .subscribe(v ->
                        System.out.println(v + Thread.currentThread().getName())
                )
        );
        t.start();
        t.join();

    }


    //将publishOn后边的操作切换到线程池中执行
    @Test
    public void publishOnTest() throws InterruptedException {

        Flux.range(1, 5)
                .publishOn(Schedulers.parallel()) // 切换到并行线程池执行后续操作
                .map(i -> i * 10) // 在并行线程上执行映射操作
                .subscribe(n -> System.out.println(Thread.currentThread().getName()+",n:"+n)); // 在主线程上消费结果
    }

    //订阅的所有过程都在不同的线程执行
    @Test
    public void subscriptOnTest() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i)
                .subscribeOn(s)
                .map(i -> "value " + i);

        new Thread(() -> flux.subscribe(System.out::println));

        TimeUnit.MINUTES.sleep(3);
    }
}
