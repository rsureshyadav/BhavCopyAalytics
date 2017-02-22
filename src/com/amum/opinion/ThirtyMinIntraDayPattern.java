package com.amum.opinion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.amum.util.AmumUtil;

public class ThirtyMinIntraDayPattern {

	public static void main(String[] args) throws IOException {


		System.out.println("Execution Started......");
		long startTime = System.currentTimeMillis();
		Properties prop = new Properties();
		InputStream input = null;

		//read the config file
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		List<String> symbolItems = Arrays.asList(prop.getProperty("symbol").split("\\s*,\\s*"));
		int period = Integer.parseInt(prop.getProperty("intraday.period"));
		String deliveryMode = prop.getProperty("delivery.mode");
		List<String> inputFileList = AmumUtil.getLatestInputFileList(period);
		for(String symbol :  symbolItems){
			if(!symbol.contains("-")){
				IntradayEngine.execute(inputFileList,symbol,deliveryMode);
			}
		}
		AmumUtil.executionTime(startTime);
		System.out.println("Execution Completed......");
	}

}
