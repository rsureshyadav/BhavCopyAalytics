package com.amum.UrlExtractor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.amum.atr.ATREngine;
import com.amum.sma.FileReader;
import com.amum.sma.FileWrite;
import com.amum.util.AmumUtil;
import com.amum.util.OutputCSVWriter;


public class YahooFinanceExecution {

	public static void main(String[] args) throws IOException {
		List<String> yahooGainerList = getGainerSymbol();

		System.out.println("Execution ATR Started.....");
		long startTime = System.currentTimeMillis();
		Properties prop = new Properties();
		InputStream input = null;
		StringBuffer summaryHeader = new StringBuffer();
		List<String> atrOutputSummary = new ArrayList<>(); 		

		//read the config file
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
	    //System.out.println(yahooGainerList);

		List<String> summaryList = new  ArrayList<>();
		List<String> finalSummaryList = new  ArrayList<>();

		for(String symbol :  yahooGainerList){
			summaryList.addAll(ATREngine.execute(prop, symbol));
		}
		finalSummaryList.add(ATREngine.getHeader(prop));
		finalSummaryList.addAll(summaryList);

		String writePath =prop.getProperty("file.summary.path")+"/yahoo_atr_volatile_summary_";
		OutputCSVWriter.writeToCsvSummaryFile(writePath, finalSummaryList);
		AmumUtil.executionTime(startTime);
		System.out.println("Execution ATR Completed......");
	
		System.out.println("Execution SMA Started......");
		long startSMATime = System.currentTimeMillis();

		StringBuffer buffer = new StringBuffer();
		buffer.append("SYMBOL,PREDECTION,MAX_GREEN,MAX_RED,SMA"+System.getProperty("line.separator"));
		for(String symbol :  yahooGainerList){
			Map<String,String> outputMap =FileReader.execute(prop,symbol);
			FileWrite.execute(prop,symbol,outputMap);
			buffer.append(outputMap.get("ConStockResult")+System.getProperty("line.separator"));
		}
		FileWrite.executeYahoo(prop,buffer.toString());
		AmumUtil.executionTime(startSMATime);

		System.out.println("Execution SMA Completed......");

	}

	private static List<String> getGainerSymbol() {
		List<String> gainerList = new ArrayList<>();
		try
		{
			List<String> hrefList = new ArrayList<>();
			Document doc = Jsoup.connect("https://in.finance.yahoo.com/gainers?e=ns").get();
		    Element content = doc.getElementById("yfitp");
		    Elements rows = content.select("table tbody tr");
		    for (Element link : rows) {
		    	Elements linkHref = link.select("tr td");
				 for (Element nameRow : linkHref) {
					 Elements ahrefLink = nameRow.getElementsByTag("a");
					 if(ahrefLink.text() != null && ahrefLink.text().length()>0){
						 hrefList.add(ahrefLink.text());
					 }
				 }
		    }
		    
		    for(String name:hrefList){
		    	if(name.contains(".NS")){
		    		name = name.replace(".NS", "");
		    		gainerList.add(name.trim());
		    	}
		    }
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}  
		return gainerList;
	}

}
