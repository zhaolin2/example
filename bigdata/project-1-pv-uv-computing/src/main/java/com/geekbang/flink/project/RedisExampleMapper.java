package com.geekbang.flink.project;

import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

public class RedisExampleMapper implements RedisMapper<Tuple4<Long, Long, Long, Integer>> {

    @Override
    public RedisCommandDescription getCommandDescription() {
        return new RedisCommandDescription(RedisCommand.SET);
    }

    @Override
    public String getKeyFromData(Tuple4<Long, Long, Long, Integer> data) {
        return String.valueOf(data.f0) + String.valueOf(data.f1);
    }

    @Override
    public String getValueFromData(Tuple4<Long, Long, Long, Integer> data) {
        return String.valueOf(data.f2) + String.valueOf(data.f3);
    }
}