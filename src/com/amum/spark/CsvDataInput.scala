package com.amum.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
object CsvDataInput {
  def main(args:Array[String]){
    //E:\\amum\\amumtrade\\BhavCopyAalytics\\input\\dailyreports\\cm05MAY2017bhav.csv
  val sparkConf = new SparkConf().setAppName("sparkSQLExamples").setMaster("local")
  val sparkContext = new SparkContext(sparkConf)
  val sqlContext = new SQLContext(sparkContext)
  var df = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("mode", "DROPMALFORMED").load("E:\\amum\\amumtrade\\BhavCopyAalytics\\input\\dailyreports\\cm05MAY2017bhav.csv");
  df.show();
  df.printSchema()
  
  }
}