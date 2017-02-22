package com.amum.opinion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.amum.util.AmumUtil;
import com.amum.vwap.VWAPEngine;

public class IntradayEngine {

/*	private double prevPrice;
	private double prevCurrPriceDayTwo;
	private String presentNews;
	private String prevNews;
	private String prevNewsDayTwo;
	private double prevDayHigh;
	private double prevDayLow;
	private double dayRange;
	private double dayBuyOrSell;
	private double currDayHigh;
	private double currDayLow;
	private double buyPrice;
	private double sellPrice;
	private double strongStock;
	private double weekStock;*/
/*	private double openPrice[];
	private double highPrice[];
	private double lowPrice[];
	private double closePrice[];
	private double lastPrice[];
	private double prevClose[];*/
	private String news[];
	private double dayRange;
	private double dayBuyOrSell;
	private double buyPrice;
	private double sellPrice;
	private double strongStock;
	private double weekStock;
	
	public static void execute(List<String> inputFileList,String symbol,String deliveryMode) throws IOException {
		/*Map<String,List<Double>> priceMap;
		List<Double> openPrice = null;
		List<Double> highPrice= null;
		List<Double> lowPrice= null;
		List<Double> closePrice= null;
		List<Double> lastPrice= null;
		List<Double> prevClose= null;*/
		
		Map<String, List<Double>> priceMap = new HashMap<>();
		List<Double> openPrice = new ArrayList<>();
		List<Double> highPrice= new ArrayList<>();
		List<Double> lowPrice= new ArrayList<>();
		List<Double> closePrice= new ArrayList<>();
		List<Double> lastPrice= new ArrayList<>();
		List<Double> prevClose= new ArrayList<>();
		
		for(String filename :inputFileList){
			String stockInfo = readFile(filename,symbol,deliveryMode);
			String lineArray[]=stockInfo.split("\\s*,\\s*");
			if(lineArray.length > 10 ){
				if(lineArray[2] !=  null){
					openPrice.add(Double.parseDouble(lineArray[2]));
				}else{
					openPrice.add(0.0);
				}
				if(lineArray[3] !=  null){
					highPrice.add(Double.parseDouble(lineArray[3]));
				}else{
					highPrice.add(0.0);
				}
				if(lineArray[4] !=  null){
					lowPrice.add(Double.parseDouble(lineArray[4]));
				}else{
					lowPrice.add(0.0);
				}
				if(lineArray[5] !=  null){
					closePrice.add(Double.parseDouble(lineArray[5]));
				}else{
					closePrice.add(0.0);
				}
				if(lineArray[6] !=  null){
					lastPrice.add(Double.parseDouble(lineArray[6]));
				}else{
					lastPrice.add(0.0);
				}
				if(lineArray[7] !=  null){
					prevClose.add(Double.parseDouble(lineArray[7]));
				}else{
					prevClose.add(0.0);
				}
			}
			
		}	
		  priceMap.put("OPEN_PRICE", openPrice);
		  priceMap.put("HIGH_PRICE", highPrice);
		  priceMap.put("LOW_PRICE", lowPrice);
		  priceMap.put("CLOSE_PRICE", closePrice);
		  priceMap.put("LAST_PRICE", lastPrice);
		  priceMap.put("PREV_CLOSE", prevClose);
		  System.out.println(symbol);
		System.out.println(openPrice);
		System.out.println(highPrice);
		System.out.println(lowPrice);
		System.out.println(closePrice);
		System.out.println(lastPrice);
		System.out.println(prevClose);
		
	}
	public static String readFile(String filename, String symbol, String deliveryMode) throws IOException {
		
		 Stream <String> lines = Files.lines(Paths.get(filename)).filter(line -> line.startsWith(symbol)&& line.contains(","+deliveryMode+","));
		 List<String> resultList = lines.collect(Collectors.toList());
		 String result = resultList.stream().map(Object::toString).collect(Collectors.joining());
  		 return result;
	}

	
}
