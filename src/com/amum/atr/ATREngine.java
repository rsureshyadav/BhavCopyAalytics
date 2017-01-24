package com.amum.atr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.amum.util.InputCSVReader;

public class ATREngine {

	public static DecimalFormat df = new DecimalFormat("###.##");

	public static void execute(Properties prop, String symbol) {
		try {
		List<String> inputList =	InputCSVReader.readAsCsv(prop, symbol);
		List<String> highMinusLowList = findHighMinusLow(inputList);
		List<String> currHighMinusPrevCloseList = findCurrHighMinusPrevClose(highMinusLowList);
		List<String> currLowMinusPrevCloseList = findCurrLowMinusPrevClose(currHighMinusPrevCloseList);
		List<String> trueRangeList =findTrueRange(currLowMinusPrevCloseList);
		List<String> avgTrueRangeList = findAvgTrueRange(trueRangeList);
		System.out.println("trueRangeList>>>"+trueRangeList.size());
		System.out.println("avgTrueRangeList>>>"+avgTrueRangeList.size());
		for(String input : avgTrueRangeList){
			System.out.println(input);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static List<String> findAvgTrueRange(List<String> trueRangeList) {
		List<String> avgTrueRangeList = new ArrayList<>(); 
		double prevATR =0.0;
		int count=0;
		double atrAvg=0.0;
		for(String input :trueRangeList){
			String inputArray[] = input.split("\\s*,\\s*");
				if(count<=12){
					atrAvg= atrAvg + Double.parseDouble(inputArray[8]) / 14;
				//	avgTrueRangeList.add(input+","+0.0);
				}else  if (count==13){
					avgTrueRangeList.add(input+","+df.format(atrAvg));
					prevATR=atrAvg;
				}else if (count >=14){
					atrAvg = ((prevATR *13)+ Double.parseDouble(inputArray[8])) / 14;
					prevATR=atrAvg;
					avgTrueRangeList.add(input+","+df.format(atrAvg));
				}
			count++;
		}
		return avgTrueRangeList;
	}

	private static List<String> findTrueRange(List<String> inputList) {
		List<String> trueRangeList = new ArrayList<>(); 
		double value[] = new double[3];
		for(String input :inputList){
			String inputArray[] = input.split("\\s*,\\s*");
			value[0] = Double.parseDouble(inputArray[5]);
			value[1] = Double.parseDouble(inputArray[6]);
			value[2] = Double.parseDouble(inputArray[7]);
			trueRangeList.add(input+","+maxValue(value));
		}
		
		return trueRangeList;
	}

	private static double maxValue(double array[]){
		double max = Arrays.stream(array).max().getAsDouble();
		  return max;
		}
	private static List<String> findCurrLowMinusPrevClose(List<String> inputList) {
		List<String> findCurrLowMinusPrevCloseList = new ArrayList<>();
		int count = 0;
		for(String input :inputList){
		String inputArray[] = input.split("\\s*,\\s*");
		
		if(count == 0){
			findCurrLowMinusPrevCloseList.add(input+","+"0");
		}else{
			String prevCloseArray[] = inputList.get(count-1).split("\\s*,\\s*");
			double prevClose = Double.parseDouble(prevCloseArray[4]);
			double lowValue = Double.parseDouble(inputArray[3]);
			double currLowMinusPrevClose = lowValue - prevClose;
			findCurrLowMinusPrevCloseList.add(input+","+df.format(currLowMinusPrevClose));
			//0			1			2	   3   4
			//STARPAPER,20-DEC-2016,182.35,175,175.8
			//						High   Low  Close
		}
		count++;
		}
		
		return findCurrLowMinusPrevCloseList;
	}

	private static List<String> findCurrHighMinusPrevClose(List<String> inputList) {
		List<String> findCurrHighMinusPrevCloseList = new ArrayList<>();
		int count = 0;
		for(String input :inputList){
		String inputArray[] = input.split("\\s*,\\s*");
		if(count == 0){
			findCurrHighMinusPrevCloseList.add(input+","+"0");
		}else{
			String prevCloseArray[] = inputList.get(count-1).split("\\s*,\\s*");
			double prevClose = Double.parseDouble(prevCloseArray[4]);
			double highValue = Double.parseDouble(inputArray[2]);
			double currHighMinusPrevClose = highValue - prevClose;
			findCurrHighMinusPrevCloseList.add(input+","+df.format(currHighMinusPrevClose));
			//0			1			2	   3   4
			//STARPAPER,20-DEC-2016,182.35,175,175.8
			//						High   Low  Close
		}
		count++;
		}
		
		return findCurrHighMinusPrevCloseList;
	}

	private static List<String> findHighMinusLow(List<String> inputList) {
		List<String> findHighMinusLowList = new ArrayList<>();

		for(String input :inputList){
		String inputArray[] = input.split("\\s*,\\s*");
		double highValue = Double.parseDouble(inputArray[2]);
		double lowValue = Double.parseDouble(inputArray[3]);
		double highMinusLow = highValue - lowValue;
		findHighMinusLowList.add(input+","+df.format(highMinusLow));
			//0			1			2	   3   4
			//STARPAPER,20-DEC-2016,182.35,175,175.8
		   //						High   Low  Close

		}
		
		return findHighMinusLowList;
	}
}


