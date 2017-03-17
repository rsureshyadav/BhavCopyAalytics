package com.amum.vwap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import com.amum.util.AmumUtil;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class VolWeightedAvgPricePattern {
public static void main(String str[]){
	System.out.println("Execution Started......");
	long startTime = System.currentTimeMillis();
	Properties prop = new Properties();
	InputStream input = null;
	try {

		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		List<String> symbolItems = Arrays.asList(prop.getProperty("symbol").split("\\s*,\\s*"));
		int count=0;
		for(String symbol :  symbolItems){
			if(!symbol.contains("-")){
				System.out.println("Executing ("+(symbolItems.size() - count) +") ==> "+symbol);
			VWAPEngine.writeToFileOutput(prop,symbol);
			count++;
			}
		}
		String fileName="vwap_summary.csv";
		VWAPEngine.writeToFileSummary(prop,fileName);
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	AmumUtil.executionTime(startTime);
	System.out.println("Execution Completed......");
}
}
