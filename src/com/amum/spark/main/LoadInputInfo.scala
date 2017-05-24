package com.amum.spark.main

import java.io.File
import java.util.Properties
import java.io.FileInputStream
import java.util.Calendar
object LoadInputInfo {
  def main(args : Array[String]){
    
	  println("hello world!!!!")
	  val prop = new Properties()
    prop.load(new FileInputStream("conf/sparkconf.properties"))
	  getInputFileNames(prop.getProperty("amum.inputfile.analysis"))
	 // println(getListOfFiles("input/dailyreports"))
  }
  def getInputFileNames(count: String) {
    val now = Calendar.getInstance()
    val currentHour = now.get(Calendar.DATE)
    println(currentHour)
  }

}