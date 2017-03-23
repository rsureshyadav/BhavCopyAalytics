package com.amum.sma;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

public class Test {

	public static void main(String[] args) throws ParseException, IOException {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		int listCount =5;
		int rowSplit = Integer.parseInt(prop.getProperty("file.row.split"));
		int fromIndex;
		int twoIndex;
		int splitCount = Math.toIntExact(listCount/rowSplit);
		System.out.println("splitCount>>>"+splitCount);
		for(int i=0; i<splitCount; i++){
			System.out.println(i);
			if(i==0){
				fromIndex=0;	
			}else if(i==splitCount-1){
				twoIndex=listCount;
			}
			twoIndex = Math.toIntExact(listCount/rowSplit);
				System.out.println("from_index>>");
				System.out.println("to_index>>");
			
		}
	}

}
