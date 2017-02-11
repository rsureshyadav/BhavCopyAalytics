package com.amum.atr;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.amum.util.AmumUtil;
import com.amum.util.OutputCSVWriter;

public class AverageTrueRangePattern {
	public static String OUTPUT_HEADER ="SYMBOL,DATE,HIGH,LOW,CLOSE,HIGH-LOW,CURR_HIGH-PREV_CLOSE,CURR_LOW-PREV_CLOSE,TRUE_RANGE,AVG_TRUE_RANGE";

	public static void main(String str[]) throws IOException{

		System.out.println("Execution Started......");
		long startTime = System.currentTimeMillis();
		Properties prop = new Properties();
		InputStream input = null;
		/*StringBuffer summaryHeader = new StringBuffer();
		List<String> atrOutputSummary = new ArrayList<>(); 	*/	

		//read the config file
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		List<String> symbolItems = Arrays.asList(prop.getProperty("symbol").split("\\s*,\\s*"));
		List<String> summaryList = new  ArrayList<>();
		List<String> finalSummaryList = new  ArrayList<>();

		for(String symbol :  symbolItems){
			if(!symbol.contains("-")){
				summaryList.addAll(ATREngine.execute(prop, symbol));
			}
		}
		finalSummaryList.add(ATREngine.getHeader(prop));
		finalSummaryList.addAll(summaryList);
		String fullPath = prop.getProperty("file.summary.path")+"/"+LocalDate.now();
		AmumUtil.createDir(fullPath);
		String writePath =fullPath+"/atr_summary.csv";
		OutputCSVWriter.writeToCsvSummaryFile(writePath, finalSummaryList);
		AmumUtil.executionTime(startTime);
		System.out.println("Execution Completed......");
	
	}
}
