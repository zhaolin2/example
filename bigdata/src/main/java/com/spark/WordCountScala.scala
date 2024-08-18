package com.spark

import org.apache.spark.{SparkConf, SparkContext}

import scala.jdk.Accumulator

object WordCountScala {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val context = new SparkContext()

    val value = context.textFile("").flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)


    value.foreach(res =>{
      print(res)
    })

    val accumulator = context.longAccumulator("test")
    accumulator.add(2)

  }

}
