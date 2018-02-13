package com.amum.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object FileReader {
  def main(args: Array[String]) {
     val logFile = "E:\\amum\\amumtrade\\BhavCopyAalytics\\input\\dailyreports\\cm05MAY2017bhav.csv"
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local");
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    
   
    // val numAs = logData.filter(line => line.contains("STARPAPER"))
     //println(numAs);
    //val numAs = logData.filter(line => line.contains("STARPAPER")).count()
    //val numBs = logData.filter(line => line.contains("SBI")).count()
   // println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))   
  }
}