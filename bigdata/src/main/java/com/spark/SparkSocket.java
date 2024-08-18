package com.spark;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;

/**
 * 配套的linux命令
 * nc -l -p 9999
 *
 */
public class SparkSocket {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("SocketWordCountApp");
             JavaStreamingContext context = new JavaStreamingContext(conf, Durations.seconds(2));

        JavaReceiverInputDStream<String> lines = context.socketTextStream("127.0.0.1", 9999);

        JavaPairDStream<String, Integer> dStream = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                .filter(StringUtils::isNotBlank)
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum);


        dStream.print();

        context.start();
        context.awaitTermination();

    }
}
