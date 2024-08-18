package com.spark;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StreamKafka {
    public static void main(String[] args) throws InterruptedException {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("WordCountApp");
        JavaStreamingContext context = new JavaStreamingContext(sparkConf, Durations.seconds(2));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);
        Collection<String> topics = Arrays.asList("messages");

        context.checkpoint("./.checkpoint");


        JavaInputDStream<ConsumerRecord<String, String>> messages =
                KafkaUtils.createDirectStream(
                        context,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaParams));

        JavaPairDStream<String, String> results = messages
                .mapToPair(record -> new Tuple2<>(record.key(), record.value()));
        JavaDStream<String> lines = results
                .map(Tuple2::_2);
        JavaDStream<String> words = lines
                .flatMap(x -> Arrays.asList(x.split("\\s+")).iterator());
        JavaPairDStream<String, Integer> wordCounts = words
                .mapToPair(s -> new Tuple2<>(s, 1)).reduceByKey(Integer::sum);

//        wordCounts.foreachRDD(
//                javaRdd -> {
//                    Map<String, Integer> wordCountMap = javaRdd.collectAsMap();
//                    for (String key : wordCountMap.keySet()) {
//                        List<Word> wordList = Arrays.asList(new Word(key, wordCountMap.get(key)));
//                        JavaRDD<Word> rdd = streamingContext.sparkContext().parallelize(wordList);
//                        javaFunctions(rdd).writerBuilder(
//                                "vocabulary", "words", mapToRow(Word.class)).saveToCassandra();
//                    }
//                }
//        );

        context.start();
        context.awaitTermination();
    }
}
