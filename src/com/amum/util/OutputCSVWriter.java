package com.amum.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class OutputCSVWriter {
public static void writeToCsvFile(String fullPath, String symbol, List<String> outputList ){
	String outputPath = null;
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

	try {
		Files.write(Paths.get(outputPath), outputList);
	} catch (IOException e) {
		e.printStackTrace();
	}
		
}
}
