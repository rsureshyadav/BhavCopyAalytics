package com.amum.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BulkReportDownloader {

    public static void main(String[] args) throws IOException, ParseException {
        List<String> urlList = new ArrayList<>();
        Properties prop = new Properties();
        InputStream input = null;
        input = new FileInputStream("conf/config.properties");
        prop.load(input);
        
        urlDownloader(urlList,prop);
        
    }

    private static void urlDownloader(List<String> urlList,Properties prop) {
        String saveDir=prop.getProperty("bulkrprt.dest.dir");
        String targetURL="https://www.nseindia.com/content/equities/bulk.csv";
            System.out.println(targetURL);
                try {
                    downloadFile(targetURL, saveDir);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
    }
    public static void downloadFile(String fileURL, String saveDir)
            throws IOException {

        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        int BUFFER_SIZE = 4096;

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }
 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
 
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String srcFilePath = saveDir + File.separator + fileName;
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(srcFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
           /* String destFilePath=srcFilePath.replace("bulk.csv", "bulkrprt_"+LocalDate.now()+".csv");
            renameFile(srcFilePath,destFilePath);*/
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

	private static void renameFile(String srcFilePath, String destFilePath) {
		Path f = Paths.get(srcFilePath);
        Path rF = Paths.get(destFilePath);
        try {
            Files.move(f, rF, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File was successfully renamed");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to rename file");
        }    
		
	}

}
