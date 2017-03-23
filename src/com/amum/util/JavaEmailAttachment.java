package com.amum.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaEmailAttachment {

	Properties emailProperties;
    Session mailSession;
    MimeMessage message;
    // Create the message part
    BodyPart messageBodyPart;
    // Create message part for attaching file
    BodyPart messageFilePart;

    // Create a multipart message
    Multipart multipart;
    
    public static void main(String arg[]) throws AddressException,
    MessagingException {

JavaEmailAttachment javaEmail = new JavaEmailAttachment();

javaEmail.setMailServerProperties();
javaEmail.createEmailMessage();
javaEmail.sendEmail();
}

public void setMailServerProperties() {

// gmail's smtp port
String emailPort = "587";
emailProperties = System.getProperties();
emailProperties.put("mail.smtp.port", emailPort);
emailProperties.put("mail.smtp.auth", "true");
emailProperties.put("mail.smtp.starttls.enable", "true");
}

public void createEmailMessage() throws AddressException,
    MessagingException {
String[] toEmails = { "rsureshyadav@gmail.com" };
String emailSubject = "Java Email";
String emailBody = "This is an email sent by JavaMail api";

// 1. Get the session object
mailSession = Session.getDefaultInstance(emailProperties, null);
// 2. Compose message
message = new MimeMessage(mailSession);

for (int i = 0; i < toEmails.length; i++) {
message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
}

message.setSubject(emailSubject);

// 3. Create MimeBodyPart object and set your message text
messageBodyPart = new MimeBodyPart();
messageBodyPart.setText(emailBody);

// 4. Create new MimeBodyPart object and set DataHandler object to this
// object - for attachment

messageFilePart = new MimeBodyPart();

String filename = "Good_Stock.csv";
String fileloc = "E:/amum/amumtrade/BhavCopyAalytics/final/2017-03-24/Good_Stock.csv";
DataSource source = new FileDataSource(fileloc);
messageFilePart.setDataHandler(new DataHandler(source));
messageFilePart.setFileName(filename);

// 5. Create Multipart object and add MimeBodyPart objects to this
// object

multipart = new MimeMultipart();

// Set the body and file
multipart.addBodyPart(messageBodyPart);
multipart.addBodyPart(messageFilePart);

// 6. set the multiplart object to the message object

message.setContent(multipart);

}

public void sendEmail() throws AddressException, MessagingException {
String emailHost = "smtp.gmail.com";

// just the id alone without @ gmail.com
String senderUsername = "amumreach@gmail.com";
String senderEmailPassword = "amumreach";

// 7. send message

Transport transport = mailSession.getTransport("smtp");

transport.connect(emailHost, senderUsername, senderEmailPassword);
transport.sendMessage(message, message.getAllRecipients());
transport.close();
System.out.println("Email sent successfully.");
}

}
