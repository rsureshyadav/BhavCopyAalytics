package com.amum.spark;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkWordCount {

	public static void main(String[] args) {
		System.out.println("Execution SimpleMovAvgPattern Started......");
		long startTime = System.currentTimeMillis();
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("conf/config.properties");
			prop.load(input);
			//E:\amum\amumtrade\BhavCopyAalytics\input\dailyreports\cm07APR2017bhav.csv
			String fileName="input\\dailyreports\\cm07APR2017bhav.csv";
			wordCount(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private static void wordCount(String fileName) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Work Count App");
        JavaSparkContext sc = new JavaSparkContext(conf);
         JavaRDD<String> input = sc.textFile( fileName );
        System.out.println(">>>>"+input.count());
        
     /*   JavaRDD<String> rdd = sc.textFile(fileName);
        JavaPairRDD<String, Integer> counts = rdd.flatMap(
          new FlatMapFunction<String, String>() {
            public Iterator<String> call(String x) {
              return (Iterator<String>) Arrays.asList(x.split(" "));
            }}).mapToPair(new PairFunction<String, String, Integer>(){
                public Tuple2<String, Integer> call(String x){
                  return new Tuple2(x, 1);
                }}).reduceByKey(new Function2<Integer, Integer, Integer>(){
                    public Integer call(Integer x, Integer y){ return x+y;}});
        
        counts.saveAsTextFile("input\\dailyreports\\output.txt");*/
       /* SparkContext context = new SparkContext(conf);
        SparkSession sparkSession = new SparkSession(context);
        Dataset<Row> df = sparkSession.read().format("com.databricks.spark.csv").option("header", true).option("inferSchema", true).load(fileName);

        System.out.println("========== Print Schema ============");
        df.printSchema();
        System.out.println("========== Print Data ==============");
        df.show();
        System.out.println("========== Print title ==============");
        df.select("title").show();*/
	}

}
