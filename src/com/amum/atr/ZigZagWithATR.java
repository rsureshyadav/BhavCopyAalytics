package com.amum.atr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ZigZagWithATR {

	public static  List<String> longestZigZag(List<Double>  ZigZagInputList){
		List<String> zigzapList = new ArrayList<>();
		int[][] cache = new int[2][];
		//up
		cache[0] = new int[ZigZagInputList.size()];
		Arrays.fill(cache[0], 1);
		//down
		cache[1] = new int[ZigZagInputList.size()];
		Arrays.fill(cache[1], 1);
		int max = 1;
		for (int end = 1; end < ZigZagInputList.size(); end++) {
			for (int start = 0; start < end; start++) {
				//update up array by checking down array
				if (ZigZagInputList.get(end) > ZigZagInputList.get(start) && cache[1][start] + 1 > cache[0][end]) {
					cache[0][end] = cache[1][start] + 1;
					zigzapList.add("UP_TREND");
				}
				//update down array by check up array
				if (ZigZagInputList.get(end) < ZigZagInputList.get(start) && cache[0][start] + 1 > cache[1][end]) {
					cache[1][end] = cache[0][start] + 1;
					zigzapList.add("DOWN_TREND");

				}
				max = Math.max(max, Math.max(cache[0][end], cache[1][end]));
				//System.out.println("max>>>>"+max);
			}
		}
		return zigzapList;
	}	
	
	public static String getZigZagLastRange(String symbol,List<String> avgTrueRangeList,String OUTPUT_HEADER,Properties prop) {
		StringBuffer summaryOutput = new StringBuffer();
		List<Double>  zigZagInputList = new ArrayList<>();
		List<String>  zigZagOutputList = new ArrayList<>();
		List<String>  lastZigZagList = null;
		//To Skip the header
		List<String> resultList = avgTrueRangeList.stream() 
				.filter(line -> !OUTPUT_HEADER. equals (line))	
				.collect(Collectors.toList());
		summaryOutput.append(symbol+",");
		for(String input : resultList){
			String inputArray[] = input.split("\\s*,\\s*");
			zigZagInputList.add(Double.parseDouble(inputArray[9]));
		}
		zigZagOutputList = ZigZagWithATR.longestZigZag(zigZagInputList);

		List<String> zigzagList = Arrays.asList(prop.getProperty("last.zigzag").split("\\s*,\\s*"));
		for(String val :  zigzagList){
			int count = Integer.parseInt(val);
			if(zigZagOutputList.size()>0 && count==1){
				 summaryOutput.append(zigZagOutputList.get(zigZagOutputList.size() - 1)+",");
			}else if(count>1 && zigZagOutputList.size() >= count){
			lastZigZagList= new ArrayList<>();
			for(int i=1; i<=count;i++){
				lastZigZagList.add(zigZagOutputList.get(zigZagOutputList.size() - i));
			}
			Map<String, Long> lastZigZagMap = lastZigZagList.stream()
			            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			 summaryOutput.append(lastZigZagMap.get("UP_TREND")+",");
		     summaryOutput.append(lastZigZagMap.get("DOWN_TREND")+",");
			 }
		}
		return summaryOutput.toString();
	}
}
