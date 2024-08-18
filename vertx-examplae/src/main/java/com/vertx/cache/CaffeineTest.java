package com.vertx.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;

public class CaffeineTest {
    public static void main(String[] args) {
        User user = new User();
        user.setName("12");
        user.setPass("234");
        Cache<String, User> cache = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(2)).build();

        cache.put("2",user);

        User cacheUser = cache.getIfPresent("2");
        cacheUser.setPass("123");

        System.out.println(cache.getIfPresent("2"));
    }
}
