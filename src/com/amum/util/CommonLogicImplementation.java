package com.amum.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONException;
import org.json.JSONObject;

import com.amum.atr.ATREngine;
import com.amum.intraday.IntradayEngine;
import com.amum.sma.FileWrite;
import com.amum.util.AmumUtil;
import com.amum.util.OutputCSVWriter;

public class CommonLogicImplementation {

	
	public static DecimalFormat df = new DecimalFormat("###.##");
	public static Map<String, JSONObject> jsonMap = new HashMap<>();
	private static void executeGoodStockWithATR(Properties prop, Set<String> finalGoodStock) throws IOException {
		String OUTPUT_HEADER ="SYMBOL,DATE,HIGH,LOW,CLOSE,HIGH-LOW,CURR_HIGH-PREV_CLOSE,CURR_LOW-PREV_CLOSE,TRUE_RANGE,AVG_TRUE_RANGE";


		List<String> summaryList = new  ArrayList<>();
		List<String> finalSummaryList = new  ArrayList<>();
		int count =0;
		for(String symbol :  finalGoodStock){
			if(!symbol.contains("-")){
				System.out.println("Executing ("+(finalGoodStock.size() - count) +") ==> "+symbol);
				summaryList.addAll(ATREngine.execute(prop, symbol));
				count++;
			}
		}
		finalSummaryList.add(ATREngine.getHeader(prop));
		finalSummaryList.addAll(summaryList);
		
		String testPath = prop.getProperty("file.summary.final")+"/"+LocalDate.now();
		OutputCSVWriter.writeToCsvTestResultSummary(testPath, finalSummaryList,"atr_final_summary");
		
	}


	public static String  getGoodStockForThirtyMin(Properties prop,Set<String> inputList) throws IOException {
		StringBuffer output =new StringBuffer();
		List<String> outputList = new ArrayList<>();
		String headerName = "SYMBOL,CURRENT_PRICE,UP_DOWN_AMOUNT,VOLUME,STOCK_STATUS,NEWS_STATUS,PE_RATIO(18-20),BUY_PRICE,SELL_PRICE";
		outputList.add(headerName);
		int count=0;
		int period = Integer.parseInt(prop.getProperty("intraday.period"));
		String deliveryMode = prop.getProperty("delivery.mode");
		List<String> inputFileList = AmumUtil.getLatestInputFileList(period);
		
		for (String symbol : inputList) {
			
			System.out.println(LocalDateTime.now()+"==> Executing ("+(inputList.size() - count) +") ==> "+symbol);
			Map<String, List<Double>> lastTwoPriceMap  = IntradayEngine.getLastTwoPriceInfo(inputFileList,symbol,deliveryMode);
			List<Double> priceList = lastTwoPriceMap.get("CLOSE_PRICE");

			if(!priceList.isEmpty()){
				String stockStatus = IntradayEngine.stockPriceSentiment(priceList);
				String newsSentiment = IntradayEngine.getNewsSentiment(prop,symbol);
				Map<String,Double> buySellMap = IntradayEngine.getBuySellPrice(lastTwoPriceMap);
				String jsonString = getJsonObjectInfo(symbol);
				
				JSONObject jObject = null;
				try {
					if (jsonString != null) {
						jObject = new JSONObject(jsonString);
						double last_price = Double.parseDouble(jObject.getString("l").replace(",", ""));
						double prev_close_price = Double.parseDouble(jObject.getString("pcls_fix").replace(",", ""));
						double profitOrLoss = last_price - prev_close_price;
						String volume = jObject.getString("vo");
						volume = volume.replace(",", "");
						String peRatio=jObject.getString("pe");
						peRatio = peRatio.replace(",", "");
						if(peRatio.length()>0){
							double peRat = Double.parseDouble(peRatio) ; 
							if(peRat>=18){
								output.append("<tr><td>"+symbol+"</td><td>"+last_price+"</td><td>"+peRatio+"</td></tr>");
								outputList.add(symbol+ "," + last_price + "," + profitOrLoss + "," + volume + "," +stockStatus+","+newsSentiment+","+peRat+","+df.format(buySellMap.get("BUY_PRICE"))+","+df.format(buySellMap.get("SELL_PRICE")));
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			count++;
		}
			String testPath = prop.getProperty("file.summary.final")+"/"+LocalDate.now();
			OutputCSVWriter.writeToCsvTestResultSummary(testPath, outputList,"thirty_min");
			
			return output.toString();
	}


	public static Set<String> getGoodStockFrmVwap(Properties prop, String fileName) throws IOException {
		Set<String> output = new HashSet<>();
		String outputPath=prop.getProperty("file.summary.path");
        String readFileName = outputPath+"/"+LocalDate.now()+"/"+fileName;
        
        Stream <String> lines = Files.lines(Paths.get(readFileName)).filter(line -> !line.startsWith("SYMBOL"));
		 List<String> resultList = lines.collect(Collectors.toList());
		 for(String result : resultList){
			 List<String> inputList = Arrays.asList(result.split("\\s*,\\s*"));

			 if(inputList.get(5).trim().length()>0 
					 && inputList.get(6).trim().length()>0  ){
				 long volume = Long.parseLong(inputList.get(6).trim());
				 double closePrice = Double.parseDouble(inputList.get(5).trim());
				 if(volume >= 100000 
						 && closePrice >= 10 
						 && closePrice <= 200){
						 output.add(inputList.get(0));
				 }
			 }
		 }
		return output;
	}


	public static Set<String> getGoodStockFrmSma(Properties prop, String fileName) throws IOException {
		Set<String> output = new HashSet<>();
		String outputPath=prop.getProperty("file.summary.path");
        String readFileName = outputPath+"/"+LocalDate.now()+"/"+fileName;
        //System.out.println(readFileName);
        
        Stream <String> lines = Files.lines(Paths.get(readFileName)).filter(line -> !line.startsWith("SYMBOL"));
		 List<String> resultList = lines.collect(Collectors.toList());
		 for(String result : resultList){
			 List<String> inputList = Arrays.asList(result.split("\\s*,\\s*"));

			 if(inputList.get(1).trim().length()>0 
					 && inputList.get(2).trim().length()>0 
					 && inputList.get(3).trim().length()>0 ){
				 if(inputList.get(1).equalsIgnoreCase("GREEN")){
					 double greenCount = Double.parseDouble(inputList.get(2));
					 double redCount= Double.parseDouble(inputList.get(3));
					 double res = greenCount / redCount;
					 if(res > 2){
						 output.add(inputList.get(0));
					 }
				 }
			 }
		 }
		return output;
	}


	public static Set<String> getGoodStockFrmAtr(Properties prop, String fileName) throws IOException {
		Set<String> atrSet = new HashSet<>();
		String outputPath=prop.getProperty("file.summary.path");
        String readFileName = outputPath+"/"+LocalDate.now()+"/"+fileName;
        //System.out.println(readFileName);
        
        Stream <String> lines = Files.lines(Paths.get(readFileName)).filter(line -> !line.startsWith("SYMBOL"));
		 List<String> resultList = lines.collect(Collectors.toList());
		 for(String result : resultList){
			 List<String> atrList = Arrays.asList(result.split("\\s*,\\s*"));
			 if( atrList.size() >= 8 
					 &&atrList.get(1).trim().length()>0 
					 && atrList.get(2).trim().length()>0 
					 && atrList.get(4).trim().length()>0 
					 && atrList.get(6).trim().length()>0 ){
				 if(atrList.get(1).equalsIgnoreCase("UP_TREND") 
						 && atrList.get(2).equalsIgnoreCase("3")
						 && atrList.get(4).equalsIgnoreCase("5") 
						 && atrList.get(6).equalsIgnoreCase("10") ){
					 atrSet.add(atrList.get(0));
				 }
			 }
		 }
		return atrSet;
	}


	private static String getOutputOfflineResult(String symbol, String outputLine) {
		String finalOutputString = null;
		String result = null;
		List<String> list = new ArrayList<>();
		try {
			String latestFileName=AmumUtil.getLatestInputFile();
			
			try (Stream<String> stream = Files.lines(Paths.get(latestFileName))) {
				list = stream
						.filter(line -> line.startsWith(symbol))
						.collect(Collectors.toList());
				if(!list.isEmpty() && list.get(0) != null){
					
					String lineArray[]= list.get(0).split("\\s*,\\s*");
					
					double last_price = Double.parseDouble(lineArray[5]);
					double prev_close_price = Double.parseDouble(lineArray[7]);
					double profitOrLoss = last_price - prev_close_price;
					if(profitOrLoss>0){
						result = "UP";
					}else{
						result = "DOWN";
					}
					finalOutputString = result+","+df.format(profitOrLoss)+","+last_price+","+outputLine;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalOutputString;
	}


	private static String getOutputOnlineResult(String symbol, String line,  Map<String, JSONObject> jsonMap) throws IOException {
		String outputLine="null";
		if(symbol != null && symbol.length()>0){
				JSONObject jObject = null;
				String result = null;
				try {
					if(jsonMap.get(symbol) != null){
						jObject = jsonMap.get(symbol);
						double last_price = Double.parseDouble(jObject.getString("l").replace(",", ""));
						double prev_close_price = Double.parseDouble(jObject.getString("pcls_fix").replace(",", ""));
						double profitOrLoss = last_price - prev_close_price;
						if(profitOrLoss>0){
							result = "UP";
						}else{
							result = "DOWN";
						}
						outputLine = result+","+df.format(profitOrLoss)+","+last_price+","+line;
					}
				} 
				catch (JSONException e) {
					e.printStackTrace();
				}
		}
		return outputLine;
	}

	
	private static String getJsonObjectInfo(String symbol) throws IOException {
		String jsonString = null;
		if(symbol != null && symbol.length()>0){
			if(symbol.contains("&")){
				symbol = symbol.replace("&", "%26");
			}
			String url ="http://www.google.com/finance/info?infotype=infoquoteall&q=NSE:"+symbol;
			System.out.println("Executing>>>"+url);
			
			jsonString = downloadFileFromInternet(url);
			if(jsonString != null ){
				jsonString =jsonString.replace("// [", "");
				jsonString =jsonString.replace("]", "");				
			}
		}
		return jsonString;
	}

	private static List<String>  getSymbol(Properties prop, String fileName) throws IOException {
		String outputPath=prop.getProperty("file.summary.path");
        String readFileName = outputPath+"/"+LocalDate.now()+"/"+fileName;
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(readFileName))) {

        	list = stream
					.filter(line -> !line.startsWith("SYMBOL"))
					.map(String::toUpperCase)
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
        return list;
		}

	private static List<String>  getHeader(Properties prop, String fileName) throws IOException {
		String outputPath=prop.getProperty("file.summary.path");
		
		
        String readFileName = outputPath+"/"+LocalDate.now()+"/"+fileName;
        List<String> headerName=new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(readFileName))) {
        	headerName = stream.filter(line -> line.startsWith("SYMBOL")).collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
        return headerName;
	}
	


	private static String downloadFileFromInternet(String httpUrl) throws IOException {
		String response = null;
			URL url = new URL(httpUrl);
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			 if (conn.getResponseCode() == 200) {
				 try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
					 response = reader.lines().collect(Collectors.joining("\n"));
				 }
			 }
		return response;
	}
}
