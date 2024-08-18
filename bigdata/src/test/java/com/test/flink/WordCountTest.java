package com.test.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.junit.Test;

import javax.swing.*;
import java.util.Arrays;


public class WordCountTest {

    /**
     * mac: nc -l 9999
     * @throws Exception
     */
    @Test
    public void localWordCountTest() throws Exception {
//        Configuration configuration = new Configuration();
//        configuration.set(RestOptions.BIND_ADDRESS, "8087");

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        DataStreamSource<String> source = environment.socketTextStream("127.0.0.1", 9999);

        source.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) throws Exception {
                String[] words = value.split(" ");
                for (String word : words) {
                    out.collect(word);
                }
            }
        }).map(new MapFunction<String, Tuple2<String,Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                return new Tuple2<String,Integer>(value, 1);
            }
        }).keyBy(tuple -> tuple.f0).sum(1).print();


        environment.execute("wordCount");

        environment.wait();




    }
}
