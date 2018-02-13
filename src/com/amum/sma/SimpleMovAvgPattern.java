package com.amum.sma;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import com.amum.util.AmumUtil;

public class SimpleMovAvgPattern {
	public static void execute()throws IOException{

		System.out.println("Execution SimpleMovAvgPattern Started......");
		long startTime = System.currentTimeMillis();
		Properties prop = new Properties();
		InputStream input = null;
		
		StringBuffer buffer = new StringBuffer();
		//read the config file
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		List<String> symbols = Arrays.asList(prop.getProperty("symbol").split("\\s*,\\s*"));
		Set<String> symbolItems = new TreeSet<>();
		symbolItems.addAll(symbols);
		buffer.append("SYMBOL,PREDECTION,MAX_GREEN,MAX_RED,SMA"+System.getProperty("line.separator"));
		int count =0;
		for(String symbol :  symbolItems){
			if(!symbol.contains("-")){
				System.out.println("Executing ("+(symbolItems.size() - count) +") ==> "+symbol);
				Map<String,String> outputMap =FileReaders.execute(prop,symbol);
				String resultArray[]= outputMap.get("ConStockResult").split("\\s*,\\s*");
				String isNull = resultArray[1];
				if(!isNull.equalsIgnoreCase("null")){
					FileWrite.execute(prop,symbol,outputMap);
					buffer.append(outputMap.get("ConStockResult")+System.getProperty("line.separator"));
				}
				count++;
			}
		}
		FileWrite.execute(prop,buffer.toString());
		AmumUtil.executionTime(startTime);

		System.out.println("Execution Completed......");
	
	}
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
		int count =0;
		for(String symbol :  symbolItems){
			if(!symbol.contains("-")){
				System.out.println("Executing ("+(symbolItems.size() - count) +") ==> "+symbol);
				Map<String,String> outputMap =FileReaders.execute(prop,symbol);
				String resultArray[]= outputMap.get("ConStockResult").split("\\s*,\\s*");
				String isNull = resultArray[1];
				if(!isNull.equalsIgnoreCase("null")){
					FileWrite.execute(prop,symbol,outputMap);
					buffer.append(outputMap.get("ConStockResult")+System.getProperty("line.separator"));
				}
				count++;
			}
		}
		FileWrite.execute(prop,buffer.toString());
		AmumUtil.executionTime(startTime);

		System.out.println("Execution Completed......");
	}

}
