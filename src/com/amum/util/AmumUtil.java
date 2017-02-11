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
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AmumUtil {

	public static void executionTime(long startTime){
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		
		long elapsedHours = totalTime / hoursInMilli;
		totalTime = totalTime % hoursInMilli;

		long elapsedMinutes = totalTime / minutesInMilli;
		totalTime = totalTime % minutesInMilli;

		long elapsedSeconds = totalTime / secondsInMilli;

		System.out.printf(
		    " %d hours, %d minutes, %d seconds%n",
		     elapsedHours, elapsedMinutes, elapsedSeconds);

	}
	
	public static String getLatestInputFile() throws IOException {
		String latestInputFile=null;
		
		List<String> fileList = new ArrayList<String>();
		Map<Date, String> dateTreeMap = new TreeMap<Date, String>(Collections.reverseOrder());
		
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
		int mapCount=0;
		for (Map.Entry<Date, String> entry : dateTreeMap.entrySet())
	    {
		if(mapCount==0){
			latestInputFile = entry.getValue();
			mapCount++;
		}
	    }
		return latestInputFile;
	}

	public static List<String> getFileName(String path) throws IOException {
		List<String> outputFilePath=new ArrayList<>();
		try(Stream<Path> paths = Files.walk(Paths.get(path))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		        	outputFilePath.add(filePath.toString());
		        }
		    });
		} 
		return outputFilePath;
	}
	
	public static String getLastElementInFile(String fileName) {
		List<String> list = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			list = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list.get(list.size()-1);
	}
	
	public static List<String> getLatestInputFileList(int period) throws IOException {
		List<String> latestInputFile=new ArrayList<>();
		
		List<String> fileList = new ArrayList<String>();
		Map<Date, String> dateTreeMap = new TreeMap<Date, String>(Collections.reverseOrder());
		
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
		int mapCount=0;
		for (Map.Entry<Date, String> entry : dateTreeMap.entrySet())
	    {
		if(mapCount<period){
			latestInputFile.add(entry.getValue());
			mapCount++;
		}
	    }
		return latestInputFile;
	}

	public static void createDir(String fullPath) {
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
        
		
	}

}
