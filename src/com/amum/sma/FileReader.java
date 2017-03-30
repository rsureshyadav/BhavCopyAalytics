package com.amum.sma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

	
	public static Map<String,String>   execute(Properties prop, String symbol){

		Map<String,String> outputMap  = null;
		try {
			outputMap = listFileNames(prop, symbol);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputMap;
	}

	private static Map<String,String> listFileNames(Properties prop, String symbol) throws IOException {
		double minPrice=Double.parseDouble(prop.getProperty("min.price"));		
		double maxPrice=Double.parseDouble(prop.getProperty("max.price"));
		Map<String,String> outputMap = new HashMap<String,String>();
		StringBuffer sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("###.##");
		double sma=0.0;
		String result=null;
		String predection=null;
		List<String> fileList = new ArrayList<String>();
		 Map<Date, String> dateTreeMap = new TreeMap<Date, String>(Collections.reverseOrder());
		 
		List<String> list = new ArrayList<>();
		List<String> resultList = new ArrayList<>();
		sb.append("Execution Time :"+LocalDateTime.now());
		sb.append(System.getProperty("line.separator"));
		sb.append(symbol+System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));

		try(Stream<Path> paths = Files.walk(Paths.get(prop.getProperty("dailyrprt.dest.dir")))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		            fileList.add(filePath.toString());
		        }
		    });
		} 
		
		for(String fileName : fileList){
			String replaceName=prop.getProperty("dailyrprt.dest.dir")+"\\cm";
			replaceName=replaceName.replace("/", "\\");
			String name =  fileName.replace(replaceName, "");
			name =  name.replace("bhav.csv", "");

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
    
            try {
                Date date = sdf.parse(name);
                dateTreeMap.put(date,fileName);
            } catch (ParseException ex) {
                ex.printStackTrace();
            } 
		}
		
		int  predCount=1;
		
		for (Map.Entry<Date, String> entry : dateTreeMap.entrySet())
	    {
			if(predCount>Integer.parseInt(prop.getProperty("period"))){
				break;
			}
			try (Stream<String> stream = Files.lines(Paths.get(entry.getValue()))) {
				list = stream
						.filter(line -> line.startsWith(symbol))
						.collect(Collectors.toList());

			} catch (IOException e) {
				e.printStackTrace();
			}

			for(String name : list){

				name=name.substring(0, name.length()-1);
				String nameArray[]= name.split(",");

				if(nameArray.length> 0 && nameArray[0].equalsIgnoreCase(symbol)){
					double closePrice =Double.parseDouble(nameArray[5]);
					if(minPrice<=closePrice && maxPrice>=closePrice){
						double predectionOne = Double.parseDouble(nameArray[5]) - Double.parseDouble(nameArray[2]);
						double predectionTwo = Double.parseDouble(nameArray[6]) - Double.parseDouble(nameArray[2]);
						sma=sma+Double.parseDouble(nameArray[5]) ;
						if(predectionOne > 0.0 && predectionTwo > 0.0){
							result = "GREEN";
						}else if(predectionOne == 0.0 && predectionTwo == 0.0){
							result = "NEAUTRAL";
						}else if(predectionOne == 0.0 && predectionTwo > 0.0){
							result = "NEAUTRAL";
						}else if(predectionOne < 0.0 && predectionTwo == 0.0){
							result = "NEAUTRAL";
						}else{
							result = "RED";
						}
						resultList.add(result);
						sb.append(nameArray[10]+" >> "
								+df.format(predectionOne)+" >> "
								+df.format(predectionTwo)+" >> "
								+result
								+System.getProperty("line.separator"));
					}
				}
			}
			predCount++;
		}
		
		if(sma>0.0){
			//Simple Moving Averages
			int period =Integer.parseInt(prop.getProperty("period"));
			sb.append(System.getProperty("line.separator"));
			sma = sma / period;
			sb.append("Simple Moving Averages : "+df.format(sma));
		}
		
		if(!resultList.isEmpty()){
			Collections.reverse(resultList);
			sb.append(System.getProperty("line.separator"));
			if(resultList.get(0).equals("GREEN")){
				sb.append("TOMMOROW PREDECTION :: GREEN"+System.getProperty("line.separator"));
				predection="GREEN";
			}else if(resultList.get(0).equals("NEAUTRAL")){
				sb.append("TOMMOROW PREDECTION :: NEAUTRAL"+System.getProperty("line.separator"));
				predection="NEAUTRAL";
			}else{
				sb.append("TOMMOROW PREDECTION :: RED"+System.getProperty("line.separator"));
				predection="RED";
			}
		}
		
		int greenOccurrences = Collections.frequency(resultList, "GREEN");
		int redOccurrences  = Collections.frequency(resultList, "RED");
		int neautralOccurrences  = Collections.frequency(resultList, "NEAUTRAL");
		sb.append(System.getProperty("line.separator"));

		sb.append("Maximum occurrence from the selected period:"+System.getProperty("line.separator"));
		sb.append("-------------------------------------------"+System.getProperty("line.separator"));

		sb.append("GREEN >> "+greenOccurrences+System.getProperty("line.separator"));
		sb.append("RED >> "+redOccurrences+System.getProperty("line.separator"));
		sb.append("NEAUTRAL >> "+neautralOccurrences+System.getProperty("line.separator"));
		outputMap.put("SingleStockResult", sb.toString());
		outputMap.put("ConStockResult", symbol+","+predection+","+greenOccurrences+","+redOccurrences+","+df.format(sma));
		return outputMap;
	}
}
