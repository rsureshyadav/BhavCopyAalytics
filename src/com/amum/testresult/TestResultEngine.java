package com.amum.testresult;

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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONException;
import org.json.JSONObject;

import com.amum.util.AmumUtil;
import com.amum.util.OutputCSVWriter;

public class TestResultEngine {

	
	public static DecimalFormat df = new DecimalFormat("###.##");
	public static Map<String, JSONObject> jsonMap = new HashMap<>();
	public static void main(String str[]) throws IOException{
		System.out.println("Execution Started......");
		long startTime = System.currentTimeMillis();
		
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("conf/config.properties");
		prop.load(input);
		List<String> inputList;
		List<String> fileNameList = Arrays.asList(prop.getProperty("test.summary.name").split("\\s*,\\s*"));
		
		inputList = getSymbol(prop,fileNameList.get(0));
		if(LocalTime.now().getHour()>=9 && LocalTime.now().getHour()<=16){
			for(String line :inputList){
				String inputArray[] = line.split("\\s*,\\s*");
				String jsonString = getJsonObjectInfo(inputArray[0].toString());
				
				JSONObject jObject = null;
				try {
					jObject = new JSONObject(jsonString);
					jsonMap.put(inputArray[0].toString(), jObject);
				} 
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		for(String fileName : fileNameList ){
			List<String> headerNameList = getHeader(prop,fileName);
			String headerName = "ACTUAL_RESULT, UP_DOWN_AMOUNT, CURRENT_PRICE, "+ headerNameList.get(0);
			List<String> outputList = new ArrayList<>();
			outputList.add(headerName);
			inputList = getSymbol(prop,fileName);
			if(LocalTime.now().getHour()>=9 && LocalTime.now().getHour()<=16){
				for(String line :inputList){
					String inputArray[] = line.split("\\s*,\\s*");
					outputList.add(getOutputOnlineResult(inputArray[0].toString(),line,jsonMap));
				}
			}else{
				inputList = getSymbol(prop,fileName);
				
				for(String line :inputList){
					String inputArray[] = line.split("\\s*,\\s*");
					outputList.add(getOutputOfflineResult(inputArray[0].toString(),line));
				}
			}
			String testPath = prop.getProperty("file.summary.path")+"/TEST_RESULT";
			
			OutputCSVWriter.writeToCsvTestResultSummary(testPath, outputList,fileName);
		}
		
		AmumUtil.executionTime(startTime);
		System.out.println("Execution Completed......");
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
			String url ="https://www.google.com/finance/info?q=NSE:"+symbol;
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
        String readFileName = outputPath+"/"+fileName;
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
		
		
        String readFileName = outputPath+"/"+fileName;
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
