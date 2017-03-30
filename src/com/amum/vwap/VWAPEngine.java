package com.amum.vwap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.amum.util.AmumUtil;

public class VWAPEngine {

	public static String execute(String filename, String symbol) {
		//String stockInfo = null;
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			list = stream
					.filter(line -> line.startsWith(symbol))
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!list.isEmpty()){
			return list.get(0);
		}
		return symbol ;
	}

	public static void writeToFileSummary(Properties prop, String fileName) throws IOException{
		String summaryPath= prop.getProperty("file.summary.path");
		String fullPath = summaryPath+"/"+LocalDate.now();
		Path path = Paths.get(fullPath);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }
        
        summaryPath=fullPath+"/"+fileName;
        String header="SYMBOL,DATE,OPEN,HIGH,LOW,CLOSE,VOLUME,VOLUMExCLOSE,13-VWAP,20-VWAP";
        List<String>  finalSummList = new ArrayList<>();
        finalSummList.add(header);
        List<String> outputList = getLastVWAPQuote(prop);
        finalSummList.addAll(outputList);
		Files.write(Paths.get(summaryPath),finalSummList );

	}


	private static List<String> getLastVWAPQuote(Properties prop) throws IOException {
		List<String>  lastVWAPQuote = new ArrayList<>();
		String outputPath= prop.getProperty("file.output.path");
		String fullPath = outputPath+"/VWAP/"+LocalDate.now();
		List<String> fileNameList = AmumUtil.getFileName(fullPath);
		for(String fileName : fileNameList){
			String stockName = fileName.substring(fileName.indexOf("VWAP_"),fileName.lastIndexOf(".csv"));
			stockName =stockName.replace("VWAP_", "");
			String line = AmumUtil.getLastElementInFile(fileName);
			lastVWAPQuote.add(stockName+","+line);
		}
		return lastVWAPQuote;
	}


	public static void writeToOutputFile(Properties prop,  String symbol, List<String> stockInfoList) throws IOException {
		String outputPath= prop.getProperty("file.output.path");
		String fullPath = outputPath+"/VWAP/"+LocalDate.now();
		
		Path path = Paths.get(fullPath);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }
		outputPath=fullPath+"/VWAP_"+symbol+".csv";
		Files.write(Paths.get(outputPath), stockInfoList);

	}


	public static Long getSumInLong(List<Long> sumList) {
		return  sumList.stream().mapToLong(Long::longValue).sum();
	}

	public static void writeToFileOutput(Properties prop, String symbol) throws IOException {
		double minPrice=Double.parseDouble(prop.getProperty("min.price"));		
		double maxPrice=Double.parseDouble(prop.getProperty("max.price"));
		String date;
		double open;
		double high;
		double low;
		double close;
		long volume;
		long volumexclose;
		long sumOne;
		long sumTwo;
		
		int innerSumOneCount=0;
		int innerSumTwoCount=0;
		double vwapOne = 0;
		double vwapTwo=0;
		int period = Integer.parseInt(prop.getProperty("period"));

		List<String> inputFileList = AmumUtil.getLatestInputFileList(period,prop);
		List<Long> volumeList = new ArrayList<>();
		List<Long> volumexList= new ArrayList<>();
		String header="DATE,OPEN,HIGH,LOW,CLOSE,VOLUME,VOLUMExCLOSE,13-VWAP,20-VWAP";
		List<String> stockInfoList = new ArrayList<>();
		List<String> stockList = new ArrayList<>();
		stockList.add(header);
		for(String filename :inputFileList){
			String stockInfo = VWAPEngine.execute(filename,symbol);
			String nameArray[]= stockInfo.split(",");
					stockInfoList.add(stockInfo);
		}
		Collections.reverse(stockInfoList);
		int count=0;
		for(String line : stockInfoList){
			String lineArray[]=line.split("\\s*,\\s*");
			if(lineArray.length > 10 ){
				
				if(lineArray[10] !=  null){
					date=lineArray[10];
				}else{
					date="99-JAN-9999";
				}
				if(lineArray[2] !=  null){
					open=Double.parseDouble(lineArray[2]);
				}else{
					open=0.0;
				}
				if(lineArray[3] !=  null){
					high=Double.parseDouble(lineArray[3]);
				}else{
					high=0.0;
				}
				if(lineArray[4] !=  null){
					low=Double.parseDouble(lineArray[4]);
				}else{
					low=0.0;
				}
				if(lineArray[5] !=  null){
					close=Double.parseDouble(lineArray[5]);
				}else{
					close=0.0;
				}
				if(lineArray[8] !=  null){
					volume=Long.parseLong(lineArray[8]);
				}else{
					volume=0;
				}
				
				volumexclose=(long) (volume * close);
				
				volumeList.add(volume);
				volumexList.add(volumexclose);
				if(count>=13){
					List<Long> sumeOneList = volumexList.subList(innerSumOneCount, volumexList.size()-1);
					List<Long> sumeTwoList = volumeList.subList(innerSumOneCount, volumeList.size()-1);
					sumOne = VWAPEngine.getSumInLong(sumeOneList);
					sumTwo = VWAPEngine.getSumInLong(sumeTwoList);
					vwapOne = sumOne / sumTwo;
					innerSumOneCount++;
				}
				if(count>=20){
					List<Long> sumeOneList = volumexList.subList(innerSumTwoCount, volumexList.size()-1);
					List<Long> sumeTwoList = volumeList.subList(innerSumTwoCount, volumeList.size()-1);
					sumOne = VWAPEngine.getSumInLong(sumeOneList);
					sumTwo = VWAPEngine.getSumInLong(sumeTwoList);
					vwapTwo = sumOne / sumTwo;
					innerSumTwoCount++;
				}
				count++;
				stockList.add(date+","+open+","+high+","+low+","+close+","+volume+","+volumexclose+","+vwapOne+","+vwapTwo);
			}
		}
		VWAPEngine.writeToOutputFile(prop,symbol,stockList);
	}


	
}
