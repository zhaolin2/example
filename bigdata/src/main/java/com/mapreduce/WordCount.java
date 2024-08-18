package com.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        //判断一下命令行输入路径/输出路径是否齐全，即是否为两个参数
        if(otherArgs.length != 2) {
            System.err.println("Usage: wordcount <in> <out>");
            System.exit(2);
        }

        //此程序的执行，在hadoop看来是一个Job，故进行初始化job操作
        Job job = new Job(conf, "wordcount");
        //可以认为成，此程序要执行WordCount.class这个字节码文件
        job.setJarByClass(WordCount.class);
        //在这个job中，我用TokenizerMapper这个类的map函数
        job.setMapperClass(TokenizerMapper.class);
        //在这个job中，我用MyReducer这个类的reduce函数
        job.setCombinerClass(MyReducer.class);
        job.setReducerClass(MyReducer.class);
        //在reduce的输出时，key的输出类型为Text
        job.setOutputKeyClass(Text.class);
        //在reduce的输出时,value的输出类型为IntWritable
        job.setOutputValueClass(IntWritable.class);
        //初始化要计算word的文件的路径
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        //初始化要计算word的文件的之后的结果的输出路径 
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        //提交job到hadoop上去执行了，意思是指如果这个job真正的执行完了则主函数退出了，若没有真正的执行完就退出了。
        System.exit(job.waitForCompletion(true)?0:1);
    }
}
