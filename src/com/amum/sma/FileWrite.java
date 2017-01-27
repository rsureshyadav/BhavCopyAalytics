package com.amum.sma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;

public class FileWrite {

	public static void execute(Properties prop, String symbol, Map<String,String> outputMap) throws IOException{
		String outputPath= prop.getProperty("file.output.path");
		String fullPath = outputPath+"/SMA/"+LocalDate.now();
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
		outputPath=fullPath+"/stockoutput_"+LocalDate.now()+"_"+symbol+".txt";
		
		Files.write(Paths.get(outputPath), outputMap.get("SingleStockResult").getBytes());
	}
	
	public static void execute(Properties prop,String output) throws IOException{
		String fileName= prop.getProperty("file.summary.path");
		fileName = fileName+"/sma_summary_"+LocalDate.now()+".csv";
		Files.write(Paths.get(fileName), output.getBytes());
	}
}
