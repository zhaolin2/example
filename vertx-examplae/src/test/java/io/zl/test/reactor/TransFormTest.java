package io.zl.test.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.function.Function;

public class TransFormTest {

    //将链上的一部分封装为为function
    @Test
    public void transTest(){
        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);

        Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .doOnNext(System.out::println)
                .transform(filterAndMap)
                .subscribe(d -> System.out.println("Subscriber to Transformed MapAndFilter: "+d));
    }

    //用作动态转换 对于每个订阅的人 有不用的转换
    @Test
    public void transDeferTest(){
        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);

        Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .doOnNext(System.out::println)
                .transform(filterAndMap)
                .subscribe(d -> System.out.println("Subscriber to Transformed MapAndFilter: "+d));
    }
}
