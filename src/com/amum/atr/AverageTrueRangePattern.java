package com.amum.atr;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.amum.sma.FileReader;
import com.amum.sma.FileWrite;
import com.amum.util.InputCSVReader;

public class AverageTrueRangePattern {

	public static void main(String str[]) throws IOException{

		System.out.println("Execution Started......");
		long startTime = System.currentTimeMillis();
		Properties prop = new Properties();
		InputStream input = null;
		
		StringBuffer buffer = new StringBuffer();
		//read the config file
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		List<String> symbolItems = Arrays.asList(prop.getProperty("symbol").split("\\s*,\\s*"));
		buffer.append("SYMBOL,DATE,HIGH,LOW,CLOSE,HIGH-LOW,CURR_HIGH-PREV_CLOSE,CURR_LOW-PREV_CLOSE,TRUE_RANGE,AVG_TRUE_RANGE"+System.getProperty("line.separator"));
		for(String symbol :  symbolItems){
			ATREngine.execute(prop, symbol);
		}
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
