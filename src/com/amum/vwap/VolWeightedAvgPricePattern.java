package com.amum.vwap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Timer;

import com.amum.util.AmumUtil;

public class VolWeightedAvgPricePattern {
public static void main(String str[]){
	System.out.println("Execution Started......");
	long startTime = System.currentTimeMillis();
	Properties prop = new Properties();
	InputStream input = null;
	Path path = Paths.get("output/INTRA_DAY/output.txt");
	int count =0;

	//read the config file
	try {
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		
		IntradayEngine engine = new IntradayEngine();
		Timer timer = new Timer();
		//message prints every 60 seconds, with a 1 second delay
		if(count >= 3){
		timer.schedule(new IntradayEngine(), 0, 6000);
			System.out.println("<<<inside if>>>");
			timer.cancel();
			count++;
		}
		
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
