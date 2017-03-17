package com.amum.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsReader {

	public static Date getLastMarketNewsDate(String symbol,Document doc) {
		List<String> newsDateList = new ArrayList<>();
		List<Date> dateList = new ArrayList<>();

		Elements listQuestions = doc.select(".leftcontainer div:nth-child(15) div");
		for(Element question: listQuestions) {
			if(question.text().length()>0){
				String line =  question.text();
				line = line.substring(line.lastIndexOf("-")+1, line.length());
				newsDateList.add(line);
			}
		}
		
		for(String date : newsDateList){
			if(date.length()>0){
				if(date.contains("-hour(s)-ago")){
					 DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					 Date tdate = new Date();
			        Date finalDate = AmumUtil.convertStringToDate(dateFormat.format(tdate));
					dateList.add(finalDate);
				}else if(date.contains("day(s) ago")){
					String range = date.replace("day(s) ago", "").trim();
			        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			        Calendar cal = Calendar.getInstance();
			        cal.add(Calendar.DATE, -Integer.parseInt(range));    
			        Date finalDate = AmumUtil.convertStringToDate(dateFormat.format(cal.getTime()));
					dateList.add(finalDate);
				}else if(date.length()==12){
					date=date.replace(" ", "-");
					date = date.substring(1, date.length());
					Date finalDate = AmumUtil.convertStringToDate(date);
					dateList.add(finalDate);
				}else {
					Date finalDate = AmumUtil.convertStringToDate("01-JAN-1900");
					dateList.add(finalDate);	
				}
			}
		}
		if(!dateList.isEmpty()){
			Collections.sort(dateList);
			return dateList.get(dateList.size()-1);
		}else{
			Date finalDate = AmumUtil.convertStringToDate("01-JAN-1900");
			return finalDate;

		}
	}

	public static Date getLastNseNewsDate(String symbol,Document doc) {
		List<String> newsDateList = new ArrayList<>();
		List<Date> dateList = new ArrayList<>();
		Elements listQuestions = doc.select(".leftcontainer div:nth-child(16) div");
		for(Element question: listQuestions) {
			if(question.text().length()>0){
				String line =  question.text();
				line = line.substring(line.lastIndexOf("-")+1, line.length());
				newsDateList.add(line);
			}
		}
		
		for(String date : newsDateList){
			if(date.length()>0){
				if(date.contains("-hour(s)-ago")){
					 DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					 Date tdate = new Date();
			        Date finalDate = AmumUtil.convertStringToDate(dateFormat.format(tdate));
					dateList.add(finalDate);
				}else if(date.contains("day(s) ago")){
					String range = date.replace("day(s) ago", "").trim();
					 DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			        Calendar cal = Calendar.getInstance();
			        cal.add(Calendar.DATE, -Integer.parseInt(range));    
			        Date finalDate = AmumUtil.convertStringToDate(dateFormat.format(cal.getTime()));
					dateList.add(finalDate);
				}else if(!date.matches(".*[^a-z].*")  && date.length()==12){
					date=date.replace(" ", "-");
					date = date.substring(1, date.length());
					Date finalDate = AmumUtil.convertStringToDate(date);
					dateList.add(finalDate);
				}else {
					Date finalDate = AmumUtil.convertStringToDate("01-JAN-1900");
					dateList.add(finalDate);	
				}
			}
		}
		if(!dateList.isEmpty()){
			Collections.sort(dateList);
			return dateList.get(dateList.size()-1);
		}else{
			Date finalDate = AmumUtil.convertStringToDate("01-JAN-1900");
			return finalDate;

		}
	}

}
