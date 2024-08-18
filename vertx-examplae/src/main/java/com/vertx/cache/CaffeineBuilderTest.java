package com.vertx.cache;


import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import reactor.util.annotation.NonNull;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CaffeineBuilderTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(3)).build(new CacheLoader<String, String>() {
            @Override
            public @Nullable String load(@NonNull String s) throws Exception {
                System.out.println(Thread.currentThread().getName()+"开始等待");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName()+"等待结束");

                return "123";
            }
        });


        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println(cache.get("13"));
        });

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println(cache.get("34"));
        });

        CompletableFuture.runAsync(() -> {
            System.out.println(cache.get("34"));
        });

        CompletableFuture.runAsync(() -> {
            System.out.println(cache.get("34"));
        });

        CompletableFuture.runAsync(() -> {
            System.out.println(cache.get("34"));
        });

        CompletableFuture.runAsync(() -> {
            System.out.println(cache.get("34"));
        });

        CompletableFuture.runAsync(() -> {
            System.out.println(cache.get("34"));
        });

        CompletableFuture.allOf(future,future1).get();

        System.out.println("结束");



    }
}
