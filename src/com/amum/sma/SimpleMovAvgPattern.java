package com.amum.sma;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SimpleMovAvgPattern {

	public static void main(String[] args) throws IOException {
		System.out.println("Execution Started......");
		long startTime = System.currentTimeMillis();
		Properties prop = new Properties();
		InputStream input = null;
		
		StringBuffer buffer = new StringBuffer();
		//read the config file
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		List<String> symbolItems = Arrays.asList(prop.getProperty("symbol").split("\\s*,\\s*"));
		buffer.append("SYMBOL,PREDECTION,MAX_GREEN,MAX_RED,SMA"+System.getProperty("line.separator"));
		for(String symbol :  symbolItems){
			Map<String,String> outputMap =FileReader.execute(prop,symbol);
			FileWrite.execute(prop,symbol,outputMap);
			buffer.append(outputMap.get("ConStockResult")+System.getProperty("line.separator"));
		}
		FileWrite.execute(prop,buffer.toString());
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		
		long elapsedHours = totalTime / hoursInMilli;
		totalTime = totalTime % hoursInMilli;

		long elapsedMinutes = totalTime / minutesInMilli;
		totalTime = totalTime % minutesInMilli;

		long elapsedSeconds = totalTime / secondsInMilli;

		System.out.printf(
		    " %d hours, %d minutes, %d seconds%n",
		     elapsedHours, elapsedMinutes, elapsedSeconds);

		System.out.println("Execution Completed......");
	}

}
