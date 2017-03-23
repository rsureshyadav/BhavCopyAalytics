package com.amum.util;

import com.way2sms.SMS;

public class AmumSms {

	public static void main(String[] args) {
		String username="9962564273";
		String password="W3928T";
		String number="9941226440";
		String message="hiiiii";
		String proxy="null";
		SMS smsClient=new SMS();
		smsClient.send( username, password, number, message,proxy);

	}

}
