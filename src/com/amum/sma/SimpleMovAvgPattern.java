package com.amum.sma;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.amum.util.AmumUtil;

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
		AmumUtil.executionTime(startTime);

		System.out.println("Execution Completed......");
	}

}
