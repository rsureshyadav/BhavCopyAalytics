package com.amum.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestEmail {

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
	public static void execute(String path,Set<String> symbol,String peStocks,List<String> copyCat,String thirtyMinOutput) throws AddressException, MessagingException{
	StringBuffer  copyCatBuff = new StringBuffer();
		if(copyCat.size()>0){
			Collections.reverse(copyCat);
			copyCatBuff.append("<hr><table border=\"5\">");
			copyCatBuff.append("<tr><td>Date</td><td><b>Symbol</b></td><td>Company Name</td><td><b>Buy/Sell</b></td><td>Quantity</td><td>Avg Price</td></tr>");
			for(String line : copyCat){
				line=line.replace(",","</td><td>");
				copyCatBuff.append("<tr><td>"+line+"</td></tr>");
			}
			copyCatBuff.append("</table>");
		}else{
			copyCatBuff.append("No Information Found On COPY CAT!!!");			
		}
		 generateAndSendEmail(path,symbol,peStocks,copyCatBuff.toString(),thirtyMinOutput);
		System.out.println("\n\n ===> AMUM has just sent an Email successfully. Check your email..");

	}
	private static void generateAndSendEmail(String path,Set<String> symbol,String peStocks,String copyCat,String thirtyMinOutput) throws AddressException, MessagingException {
		// Step1
				System.out.println("\n 1st ===> setup Mail Server Properties..");
				mailServerProperties = System.getProperties();
				mailServerProperties.put("mail.smtp.port", "587");
				mailServerProperties.put("mail.smtp.auth", "true");
				mailServerProperties.put("mail.smtp.starttls.enable", "true");
				System.out.println("Mail Server Properties have been setup successfully..");
		 
				// Step2
				System.out.println("\n\n 2nd ===> get Mail Session..");
				getMailSession = Session.getDefaultInstance(mailServerProperties, null);
				generateMailMessage = new MimeMessage(getMailSession);
				generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("jayac251@gmail.com"));
				generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("rsureshyadav@gmail.com"));
				generateMailMessage.setSubject("!!! "+LocalDateTime.now()+" => Stock Market Notification!!!");
				String emailBody = "<h1>Today's Over All Best Stock For Analysis</h1> <hr> <br> <p>"+symbol + "</p>\n<br><br>"
						+ "<h1>Best Thirty Min Trading Stock (9:15 AM - 9:45 AM)</h1>"
						+ "<hr><table border=\"5\"><tr><td><b>Symbol</b></td><td><b>Price</b></td><td><b>Profit/Loss</b></td><td><b>Volume</b></td></tr>"
						+ thirtyMinOutput+"</table>"
						+ "<h1>Best PE Ratio Stock</h1>"
						+ "<hr><table border=\"5\"><tr><td><b>Symbol</b></td><td><b>Price</b></td><td><b>PE Ratio</b></td></tr>"
						+ peStocks+"</table>"
						+"<h1>Ninja Copy Cat Stocks</h1> <hr> <br> <p>"+copyCat + "</p>\n<br><br>"
						+ "<br><br> Regards, <br>AMUM Admin";
				generateMailMessage.setContent(emailBody, "text/html");
				System.out.println("Mail Session has been created successfully..");
		 
				// Step3
				System.out.println("\n\n 3rd ===> Get Session and Send mail");
				Transport transport = getMailSession.getTransport("smtp");
		 
				// Enter your correct gmail UserID and Password
				// if you have 2FA enabled then provide App Specific Password
				transport.connect("smtp.gmail.com", "amumreach@gmail.com", "amumreach");
				transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
				transport.close();	
	}
	public static void main(String[] args) {
		String path="<testing>";
		Set<String> set= new HashSet();
		set.add("<testing>");
		String peStocks="<testing>";
		List<String> copyCat=new ArrayList();
		copyCat.add("<testing>");
		String thirtyMinOutput="<testing>";
		try {
			execute( path, set, peStocks, copyCat, thirtyMinOutput);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
