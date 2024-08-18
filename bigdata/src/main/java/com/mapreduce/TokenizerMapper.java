package com.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
    //声时一个IntWritable变量，作计数用，每出现一个key，给其一个value=1的值
    IntWritable one = new IntWritable(1);
    Text word = new Text();

    public void map(Object key, Text value, Context context) throws IOException,InterruptedException {
        //Hadoop读入的value是以行为单位的，其key为该行所对应的行号，因为我们要计算每个单词的数目，
        //默认以空格作为间隔，故用StringTokenizer辅助做字符串的拆分，也可以用string.split("")来作。
        StringTokenizer itr = new StringTokenizer(value.toString());
        //遍历每一个单词
        while(itr.hasMoreTokens()) {
            word.set(itr.nextToken());
            context.write(word, one);
        }
    }
}
