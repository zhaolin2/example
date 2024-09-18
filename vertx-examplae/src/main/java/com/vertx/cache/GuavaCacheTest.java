package com.vertx.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class GuavaCacheTest {
    public static void main(String[] args) {
        Cache<String, User> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES).build();

        User user = new User();
        user.setName("12");
        user.setPass("234");

        cache.put("2",user);

        User cacheUser = cache.getIfPresent("2");
        cacheUser.setPass("123");

        System.out.println(cache.getIfPresent("2"));
    }
}
