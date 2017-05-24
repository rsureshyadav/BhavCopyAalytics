package com.amum.source;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.map.HashedMap;

public class InputSourceOneModeling {

	public static void main(String[] args) {
		 File f = null;
	      File[] paths;
	      
	      try {  
	          
	          // create new file
	          f = new File("input/dailyreports");
	          
	          // returns pathnames for files and directory
	          paths = f.listFiles();
	          
	          // for each pathname in pathname array
	          for(File path:paths) {
	          Map<String,String> nameMap  = getS1FileName(path);
	          System.out.println(nameMap.get("FILE_NAME"));
	         // System.out.println(nameMap.get("DATE"));
	          createOutputFile(path,nameMap.get("FILE_NAME"));
	          

	          }
	          
	       } catch(Exception e) {
	          
	          // if any error occurs
	          e.printStackTrace();
	       }

	}

	private static void createOutputFile(File path, String fName) throws IOException {
		List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path.toURI()))) {
      	  list = br.lines().collect(Collectors.toList());
        }
        
        String outputFileName="input/s1/"+fName;
        Files.write(Paths.get(outputFileName), list);
		
	}
	
	private static Map<String,String> getS1FileName(File path) throws ParseException {
		Map<String,String> nameMap = new HashedMap();
		 String fileName = path.getName();
         fileName=fileName.replace("cm", "");
         fileName=fileName.replace("bhav.csv", "");
         SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
         Date date = (Date) sdf.parse(fileName);
         
         Format newDate = new SimpleDateFormat("YYYY-MM-dd");
        // SimpleDateFormat newDate = new SimpleDateFormat("ddMMyyyy");
         String newFileName = newDate.format(date);
         newFileName="AMUM_S1_"+newFileName+".csv";
         
         SimpleDateFormat newDate1 = new SimpleDateFormat("dd-MM-yyyy");
         String date1 = newDate1.format(date);
         nameMap.put("FILE_NAME", newFileName);
         nameMap.put("DATE", date1);
		return nameMap;
	}

/*	private static Map<String,String> getS1FileName(File path) throws ParseException {
		Map<String,String> nameMap = new HashedMap();
		 String fileName = path.getName();
         fileName=fileName.replace("cm", "");
         fileName=fileName.replace("bhav.csv", "");
         SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
         Date date = (Date) sdf.parse(fileName);
         SimpleDateFormat newDate = new SimpleDateFormat("ddMMyyyy");
         String newFileName = newDate.format(date);
         newFileName="AMUM_S1_"+newFileName+".csv";
         SimpleDateFormat newDate1 = new SimpleDateFormat("dd-MM-yyyy");
         String date1 = newDate1.format(date);
         nameMap.put("FILE_NAME", newFileName);
         nameMap.put("DATE", date1);
		return nameMap;
	}*/

}
