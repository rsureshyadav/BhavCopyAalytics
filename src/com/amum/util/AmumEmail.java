package com.amum.util;

import java.time.LocalDateTime;
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

public class AmumEmail {

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
	public static void execute(String path,Set<String> symbol,String peStocks,List<String> copyCat) throws AddressException, MessagingException{
		 generateAndSendEmail(path,symbol,peStocks,copyCat);
		System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");

	}
	private static void generateAndSendEmail(String path,Set<String> symbol,String peStocks,List<String> copyCat) throws AddressException, MessagingException {
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
						+ "<h1>Best PE Ratio Stock</h1>"
						+ "<hr><table border=\"1\"><tr><td><b>Symbol</b></td><td><b>Price</b></td><td><b>PE Ratio</b></td></tr>"
						+ peStocks+"</table>"
						+"<h1>Copy Cat Stock</h1> <hr> <br> <p>"+copyCat + "</p>\n<br><br>"
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
		// TODO Auto-generated method stub

	}

}
