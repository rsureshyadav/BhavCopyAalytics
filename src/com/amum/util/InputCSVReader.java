package com.amum.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputCSVReader {

	public static List<String> readAsCsv(Properties prop, String symbol) throws IOException {
		List<String> inputList = new ArrayList<>();
		StringBuffer sb = new StringBuffer();

		List<String> fileList = new ArrayList<String>();
		 Map<Date, String> dateTreeMap = new TreeMap<Date, String>(Collections.reverseOrder());
		 
		List<String> list = new ArrayList<>();


		try(Stream<Path> paths = Files.walk(Paths.get("input"))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		            fileList.add(filePath.toString());
		        }
		    });
		} 
		
		for(String fileName : fileList){
			String name =  fileName.replace("input\\cm", "");
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
					inputList.add(nameArray[0]+","+nameArray[10]+","+nameArray[3]+","+nameArray[4]+","+nameArray[5]);
				}
			}
			predCount++;
		}
		return inputList;
	}
}


