package config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.client.RedisClient;
import org.redisson.config.Config;

public class Redissons {

    static private Config getConfig(){
        // 1. Create config object
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        return config;

// Sync and Async API
//        RedissonClient redisson = Redisson.create(config);

// Reactive API
//        RedissonReactiveClient redissonReactive = redisson.reactive();

// RxJava3 API
//        RedissonRxClient redissonRx = redisson.rxJava();
    }

    static public RedissonClient getRedissionClient(){
        return Redisson.create(getConfig());
    }
}
