package com.amum.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amum.util.CommonLogicImplementation;

public class Test {

	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		CommonLogicImplementation.getCopyCatStockInfo(prop);
		
	}

}
