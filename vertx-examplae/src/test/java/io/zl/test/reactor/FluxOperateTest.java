package io.zl.test.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxOperateTest {

    @Test
    public void testZipWith(){
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);
    }

    @Test
    public void combineLatest(){
        Flux<String> flux2 = Flux.just("A", "B", "C");
        Flux<Integer> flux1 = Flux.just(1,3);

        Flux<String> combinedFlux = Flux.combineLatest(
                flux1,
                flux2,
                (num, letter) -> num + letter
        );

        combinedFlux.subscribe(
                result -> System.out.println("Combined: " + result)
        );
    }
}
