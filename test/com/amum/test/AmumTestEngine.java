package com.amum.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.amum.atr.AverageTrueRangePattern;
import com.amum.sma.SimpleMovAvgPattern;
import com.amum.util.AmumEmail;
import com.amum.util.AmumUtil;
import com.amum.util.CommonLogicImplementation;
import com.amum.util.OutputCSVWriter;

public class AmumTestEngine {

	public static void main(String[] args) throws IOException, AddressException, MessagingException {
		System.out.println("Execution Started......");
		long startTime = System.currentTimeMillis();
		
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		
		/*
		 * Executing stock aginst the Input file
		 * VolWeightedAvgPricePattern.execute();
		 */
		AverageTrueRangePattern.execute();
		SimpleMovAvgPattern.execute();
		/*VolWeightedAvgPricePattern.execute();*/
		/*
		 * Pick the Good stock from the file generated by above methods
		 */
		Set<String> finalGoodStock = new TreeSet<>();

		Set<String> atrStock = CommonLogicImplementation.getGoodStockFrmAtr(prop,prop.getProperty("atr.summary.name"));
		finalGoodStock.addAll(atrStock);
		Set<String> smaStock =CommonLogicImplementation.getGoodStockFrmSma(prop,prop.getProperty("sma.summary.name"));
		finalGoodStock.addAll(smaStock);
		//Yet to add other filter criteria
		System.out.println("Final Good Stock ==> "+finalGoodStock);
		String path = prop.getProperty("file.summary.final")+"/"+LocalDate.now();
		
		String fileName ="Good_Stock.csv";
		OutputCSVWriter.writeToCsvFinalFile(path,finalGoodStock,fileName);
		
		String peStocks = CommonLogicImplementation.getGoodStockForThirtyMin(prop,finalGoodStock);
		List<String> copyCat= CommonLogicImplementation.getCopyCatStockInfo(prop);
		System.out.println("Final Copy Cat Stock ==> "+copyCat);

		AmumEmail.execute(path+"/"+fileName,finalGoodStock,peStocks,copyCat);
		AmumUtil.executionTime(startTime);
		System.out.println("Execution Completed......");
	}

}
