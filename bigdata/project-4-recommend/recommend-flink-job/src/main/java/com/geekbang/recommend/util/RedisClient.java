package com.geekbang.recommend.util;

import com.geekbang.recommend.util.Property;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class RedisClient {
    public static Jedis jedis;

    //静态代码块初始化 redis
    static {
        jedis = new Jedis(Property.getStrValue("redis.host"), Property.getIntegerValue("redis.port"));
        jedis.select(Property.getIntegerValue("redis.db"));
        jedis.auth(Property.getStrValue("redis.password"));
    }

    public static String getData(String s) {
        return jedis.get(s);
    }

    public static boolean putData(String key, String value) {
        return jedis.append(key, value) != null;
    }

    public static boolean rpush(String key, List<String> value) {
        for(int i = 0; i < value.size(); i++) {
            jedis.rpush(key, value.get(i));
        }
        return true;
    }

    public static List<String> lrange(String key) {
        return jedis.lrange(key, 0, -1);
    }
}
