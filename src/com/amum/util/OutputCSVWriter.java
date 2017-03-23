package com.amum.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class OutputCSVWriter {
public static void writeToCsvFile(String fullPath, String symbol, List<String> outputList ){
	String outputPath = null;
	Path path = Paths.get(fullPath);
    //if directory exists?
    if (!Files.exists(path)) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            //fail to create directory
            e.printStackTrace();
        }
    }
	outputPath=fullPath+"/stockoutput_"+LocalDate.now()+"_"+symbol+".csv";

	try {
		Files.write(Paths.get(outputPath), outputList);
	} catch (IOException e) {
		e.printStackTrace();
	}
		
}
public static void writeToCsvSummaryFile(String fullPath, List<String> outputList ){

	try {
		Files.write(Paths.get(fullPath), outputList);
	} catch (IOException e) {
		e.printStackTrace();
	}
		
}

public static void writeToCsvFinalFile(String fullPath, Set<String> outputList,String inputFileName ){
	Path path = Paths.get(fullPath);
    //if directory exists?
    if (!Files.exists(path)) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            //fail to create directory
            e.printStackTrace();
        }
    }

	try {
		Files.write(Paths.get(fullPath+"/"+inputFileName), outputList);
	} catch (IOException e) {
		e.printStackTrace();
	}
		
}
public static void writeToCsvTestResultSummary(String fullPath, List<String> outputList,String inputFileName ){
	String outputPath = null;
	Path path = Paths.get(fullPath);
    //if directory exists?
    if (!Files.exists(path)) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            //fail to create directory
            e.printStackTrace();
        }
    }
	try {
		if(inputFileName.startsWith("atr")){
			outputPath=fullPath+"/ATR_"+getDateTime()+".csv";
		}else if(inputFileName.startsWith("sma")){
			outputPath=fullPath+"/SMA_"+getDateTime()+".csv";
		}else if(inputFileName.startsWith("vwap")){
			outputPath=fullPath+"/VWAP_"+getDateTime()+".csv";
		}else if(inputFileName.startsWith("thirtymin")){
			outputPath=fullPath+"/THIRTYMIN_INTRADAY_"+getDateTime()+".csv";
		}else if(inputFileName.startsWith("atr_final_summary")){
			outputPath=fullPath+"/ATR_FINAL_SUMMARY_RESULT_"+getDateTime()+".csv";
		}else if(inputFileName.startsWith("thirty_min")){
			outputPath=fullPath+"/THIRTYMIN_"+getDateTime()+".csv";
		}
		Files.write(Paths.get(outputPath), outputList);
	} catch (IOException e) {
		e.printStackTrace();
	}
}
private  final static String getDateTime()  
{  
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");  
    return df.format(new Date());  
}  
}
