package com.amum.opinion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.amum.atr.ATREngine;
import com.amum.util.AmumUtil;
import com.amum.util.OutputCSVWriter;

public class ThomsonReutersOpinion {

	public static void main(String[] args) throws IOException {

		System.out.println("Execution Started......");
		long startTime = System.currentTimeMillis();
		Properties prop = new Properties();
		InputStream input = null;

		//read the config file
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		List<String> symbolItems = Arrays.asList(prop.getProperty("symbol").split("\\s*,\\s*"));

		for(String symbol :  symbolItems){
			if(!symbol.contains("-")){
				System.out.println(symbol);			
			}
		}
	 
		AmumUtil.executionTime(startTime);
		System.out.println("Execution Completed......");
	
	}

}
