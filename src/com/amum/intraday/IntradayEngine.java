package com.amum.intraday;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.amum.util.AmumUtil;
import com.amum.util.NewsReader;

public class IntradayEngine {

	public static Map<String, Double> getBuySellPrice(Map<String, List<Double>> lastTwoPriceMap) {
		Map<String,Double> bsMap = new HashMap<>();
		List<Double> highPriceList = lastTwoPriceMap.get("HIGH_PRICE");
		List<Double> lowPriceList = lastTwoPriceMap.get("LOW_PRICE");
		List<Double> prevPriceList = lastTwoPriceMap.get("PREV_CLOSE");
	  	double dayRange = highPriceList.get(0) - lowPriceList.get(0);
	  	double totalBuySell = dayRange / 3;
	  	double buyPrice = prevPriceList.get(0) - totalBuySell;
	  	double sellPrice = prevPriceList.get(0) + totalBuySell;
	  	bsMap.put("BUY_PRICE", buyPrice);
	  	bsMap.put("SELL_PRICE", sellPrice);
		return bsMap;
	}

	public static String stockPriceSentiment(List<Double> closePriceList) {
		String result = "STRONG_STOCK";
			double lastClosePrice = closePriceList.get(0);
			for(double closePrice : closePriceList){
				if(closePrice>lastClosePrice){
					result="WEEK_STOCK";
				}
			}
		return result;
	}

	public static Map<String, List<Double>> getLastTwoPriceInfo(List<String> inputFileList, String symbol,	String deliveryMode) throws IOException {
		Map<String, List<Double>> priceMap = new HashMap<>();
		List<Double> openPrice = new ArrayList<>();
		List<Double> highPrice= new ArrayList<>();
		List<Double> lowPrice= new ArrayList<>();
		List<Double> closePrice= new ArrayList<>();
		List<Double> lastPrice= new ArrayList<>();
		List<Double> prevClose= new ArrayList<>();
		
		for(String filename :inputFileList){
			String stockInfo = readFile(filename,symbol,deliveryMode);
			String lineArray[]=stockInfo.split("\\s*,\\s*");
			if(lineArray.length > 10 ){
				if(lineArray[2] !=  null){
					openPrice.add(Double.parseDouble(lineArray[2]));
				}else{
					openPrice.add(0.0);
				}
				if(lineArray[3] !=  null){
					highPrice.add(Double.parseDouble(lineArray[3]));
				}else{
					highPrice.add(0.0);
				}
				if(lineArray[4] !=  null){
					lowPrice.add(Double.parseDouble(lineArray[4]));
				}else{
					lowPrice.add(0.0);
				}
				if(lineArray[5] !=  null){
					closePrice.add(Double.parseDouble(lineArray[5]));
				}else{
					closePrice.add(0.0);
				}
				if(lineArray[6] !=  null){
					lastPrice.add(Double.parseDouble(lineArray[6]));
				}else{
					lastPrice.add(0.0);
				}
				if(lineArray[7] !=  null){
					prevClose.add(Double.parseDouble(lineArray[7]));
				}else{
					prevClose.add(0.0);
				}
			}
			
		}
		priceMap.put("OPEN_PRICE", openPrice);
		  priceMap.put("HIGH_PRICE", highPrice);
		  priceMap.put("LOW_PRICE", lowPrice);
		  priceMap.put("CLOSE_PRICE", closePrice);
		  priceMap.put("LAST_PRICE", lastPrice);
		  priceMap.put("PREV_CLOSE", prevClose);
		return priceMap;
	}

	public static String getNewsSentiment(String symbol) throws IOException {
		String newsStatus = null;

		  String newsURL="http://money.rediff.com/companies/"+symbol;
			Document doc = Jsoup.connect(newsURL).get();
			Date marketNewsDate = NewsReader.getLastMarketNewsDate(symbol,doc);
			LocalDate markLocaDate = AmumUtil.convertDateToLocalDate(marketNewsDate);
			//System.out.println("Last Market News::"+markLocaDate);
			Date nseNewsDate = NewsReader.getLastNseNewsDate(symbol,doc);
			LocalDate nseNewsLocalDate = AmumUtil.convertDateToLocalDate(nseNewsDate);
			//System.out.println("Last NSE News::"+nseNewsLocalDate);

			LocalDate today = LocalDate.now();
			LocalDate yesterday = today.minusDays(1);
			LocalDate dayBeforeYesterday = today.minusDays(2);

			if(!today.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !today.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
				if(today.equals(markLocaDate)){
					newsStatus="NEWS";
				}else if(yesterday.equals(markLocaDate)){
					newsStatus="NEWS";
				}else if(dayBeforeYesterday.equals(markLocaDate)){
					newsStatus="NEWS";
				}else if(today.equals(nseNewsLocalDate)){
					newsStatus="NEWS";
				}else if(yesterday.equals(nseNewsLocalDate)){
					newsStatus="NEWS";
				}else if(dayBeforeYesterday.equals(nseNewsLocalDate)){
					newsStatus="NEWS";
				}else{
					newsStatus="NO_NEWS";
				}
			} if(today.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
				 today = LocalDate.now();
				 today = today.minusDays(1);
				 yesterday = today.minusDays(2);
				 dayBeforeYesterday = today.minusDays(3);
				 if(today.equals(markLocaDate)){
						newsStatus="NEWS";
					}else if(yesterday.equals(markLocaDate)){
						newsStatus="NEWS";
					}else if(dayBeforeYesterday.equals(markLocaDate)){
						newsStatus="NEWS";
					}else if(today.equals(nseNewsLocalDate)){
						newsStatus="NEWS";
					}else if(yesterday.equals(nseNewsLocalDate)){
						newsStatus="NEWS";
					}else if(dayBeforeYesterday.equals(nseNewsLocalDate)){
						newsStatus="NEWS";
					}else{
						newsStatus="NO_NEWS";
					}
			}else if(today.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
				 today = LocalDate.now();
				 today = today.minusDays(2);
				 yesterday = today.minusDays(3);
				 dayBeforeYesterday = today.minusDays(3);
				 if(today.equals(markLocaDate)){
						newsStatus="NEWS";
					}else if(yesterday.equals(markLocaDate)){
						newsStatus="NEWS";
					}else if(dayBeforeYesterday.equals(markLocaDate)){
						newsStatus="NEWS";
					}else if(today.equals(nseNewsLocalDate)){
						newsStatus="NEWS";
					}else if(yesterday.equals(nseNewsLocalDate)){
						newsStatus="NEWS";
					}else if(dayBeforeYesterday.equals(nseNewsLocalDate)){
						newsStatus="NEWS";
					}else{
						newsStatus="NO_NEWS";
					}
			}
			
		return newsStatus;
	}
	public static String readFile(String filename, String symbol, String deliveryMode) throws IOException {
		
		 Stream <String> lines = Files.lines(Paths.get(filename)).filter(line -> line.startsWith(symbol)&& line.contains(","+deliveryMode+","));
		 List<String> resultList = lines.collect(Collectors.toList());
		 String result = resultList.stream().map(Object::toString).collect(Collectors.joining());
  		 return result;
	}

	
}
