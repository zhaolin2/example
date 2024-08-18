package io.zl.test.reactor;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class FlowTest {

    @Test
    //0-N 个元素
    public void basicTest(){
        Disposable subscribe = Flux.fromArray(new String[]{"123", "456"}).subscribe(System.out::println);

        subscribe.dispose();

        Flux.fromArray(new String[]{"123", "456"})
                .window(5)
                .subscribe(flux -> {
                    System.out.println(flux);
                });

    }

    @Test
    public void  subscriberTest(){
        SystemSubscriber subscriber = new SystemSubscriber();

         Flux.fromArray(new String[]{"123", "456"}).subscribe(subscriber);
    }

    @Test
    public void subscribeTest(){
        Flux.range(1, 10)
                .doOnRequest(r -> System.out.println("request of " + r))
                .subscribe(new BaseSubscriber<Integer>() {

                    @Override
                    public void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }

                    @Override
                    public void hookOnNext(Integer integer) {
                        System.out.println("Cancelling after having received " + integer);
                        cancel();
                    }
                });
    }
}
