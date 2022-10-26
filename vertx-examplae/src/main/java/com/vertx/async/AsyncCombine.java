package com.vertx.async;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.up.util.Ut;
//import io.vertx.up.unity.Ux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AsyncCombine {
    public static void main(final String[] args) {
        final List<String> nameSet = new ArrayList<>();
        nameSet.add("Lang");
        nameSet.add("Huan");
        nameSet.add("Han");

        thenCombineT(nameSet.stream().map(At::hiAsync).collect(Collectors.toList()))
                .compose(response -> {
                    response.forEach(System.out::println);
                    return Future.succeededFuture();
                });
    }

    static <T> Future<List<T>> thenCombineT(final List<Future<T>> futures) {
        final List<Future> futureList = new ArrayList<>(futures);
        return CompositeFuture.join(futureList).compose(finished -> {
            final List<T> result = new ArrayList<>();
            Ut.itList(finished.list(),
                    (item, index) -> result.add((T) item));
            return Future.succeededFuture(result);
        });
    }
}
