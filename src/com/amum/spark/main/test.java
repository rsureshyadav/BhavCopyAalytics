package com.amum.spark.main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.amum.util.AmumUtil;

public class test {

	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
	input = new FileInputStream("conf/config.properties");
	prop.load(input);
	int period = Integer.parseInt(prop.getProperty("intraday.period"));
	List<String> inputFileList = AmumUtil.getLatestInputFileList(period,prop);
	System.out.println(inputFileList);
	}

}
