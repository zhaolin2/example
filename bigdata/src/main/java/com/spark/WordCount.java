//package com.spark;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.spark.SparkConf;
//import org.apache.spark.SparkContext;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.rdd.RDD;
//import scala.Tuple2;
//
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//public class WordCount {
//    public static void main(String[] args) {
//        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("WordCountApp");
//        JavaSparkContext context = new JavaSparkContext(sparkConf);
//
//        JavaRDD<String> lines = context.textFile("data/sc.txt");
//        Map<String, Integer> wordMap = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
//                .filter(StringUtils::isNotBlank)
//                .mapToPair(word -> new Tuple2<>(word, 1))
//                .reduceByKey(Integer::sum)
//                .collectAsMap();
//
//        wordMap.forEach((key,value)->{
//            System.out.println("key:"+key+",value:"+value);
//        });
//
//        context.close();
//
//
//    }
//}
