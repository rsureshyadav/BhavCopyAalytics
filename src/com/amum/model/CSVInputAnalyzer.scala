package com.amum.model

import scala.collection.mutable.ArrayBuffer
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql._
import java.io._

case class CSVInputColumn(symbol: String,series: String,open: Float,high: Float,low: Float,close: Float,last: Float,prevclose: Float,tottrdqty: Float,tottrdval: Float,timestamp: String,totaltrades: Float,isin: String)
object CSVInputAnalyzer {
def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("CSVInputAnalyzer").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    import sqlContext._
    var dateLine = "'22-MAY-2017'"
    var sym_name=""
    val w = new BufferedWriter(new FileWriter("final/ModelRunOutput.txt"))

    val file1RDD = sc.textFile("input/s1/AMUM_S1_2017-05-22.csv")
    val finalFile1=  file1RDD.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }

    val file2RDD = sc.textFile("input/s1/AMUM_S1_2017-05-19.csv")
    val finalFile2=  file2RDD.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }

    val file3RDD = sc.textFile("input/s1/AMUM_S1_2017-05-18.csv")
    val finalFile3=  file3RDD.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }
    
    
    val file1df=finalFile1.map (_.split(",")).map(p => CSVInputColumn(p(0).toString(),p(1).toString(),p(2).toFloat,p(3).toFloat,p(4).toFloat,p(5).toFloat,p(6).toFloat,p(7).toFloat,p(8).toFloat,p(9).toFloat,p(10).toString(),p(11).toFloat,p(12).toString())).toDF()
    val file2df=finalFile2.map (_.split(",")).map(p => CSVInputColumn(p(0).toString(),p(1).toString(),p(2).toFloat,p(3).toFloat,p(4).toFloat,p(5).toFloat,p(6).toFloat,p(7).toFloat,p(8).toFloat,p(9).toFloat,p(10).toString(),p(11).toFloat,p(12).toString())).toDF()
    val file3df=finalFile3.map (_.split(",")).map(p => CSVInputColumn(p(0).toString(),p(1).toString(),p(2).toFloat,p(3).toFloat,p(4).toFloat,p(5).toFloat,p(6).toFloat,p(7).toFloat,p(8).toFloat,p(9).toFloat,p(10).toString(),p(11).toFloat,p(12).toString())).toDF()
    

    
    val srcfiledf = file1df.union(file2df).union(file3df).registerTempTable("srctable")
     val query_exec=sqlContext.sql(s"""select symbol from srctable where timestamp=$dateLine and last BETWEEN 99 AND 100""")

   val symbols_count=query_exec.count.toInt
   println(">>>>>>>>>>>>.."+symbols_count)
   val symbols_name=query_exec.collect
      for(i <- 0 to symbols_count-1)
      {
        sym_name=symbols_name(i).mkString("")
        println(sym_name)
      val finalQuery= s"""WITH  Q1 AS (select symbol, last ,timestamp from srctable where timestamp=$dateLine and symbol='$sym_name') ,  Q2 AS (select a.symbol,a.last from srctable a left join Q1 b on a.symbol = b.symbol where a.timestamp <> b.timestamp) , Q3 AS (select Q2.symbol,Q2.last from Q2,Q1 where Q2.last > Q1.last and Q1.symbol='$sym_name')  select * from Q3"""
     val fin= sqlContext.sql(finalQuery).count().toInt
      
           if(fin > 0)
      {
        	   println(s"$sym_name,FALSE")
        	   w.write(s"$sym_name,FALSE"+"\n")
      }else{
        	  println(s"$sym_name,TRUE")
        	  w.write(s"$sym_name,TRUE"+"\n")
      }
      }
          w.close()
    
  }
}