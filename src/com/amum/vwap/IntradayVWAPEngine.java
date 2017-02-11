package com.amum.vwap;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class IntradayVWAPEngine extends TimerTask {
	private String text;
	String url="http://www.google.com/finance/info?infotype=infoquoteall&q=NSE:STARPAPER";
	@Override
	public void run() {
		text = LocalDateTime.now()+" >> AMUM - Hello World!!!! >> ";
		System.out.println(getText());
			
			
	}
	public String getText() {
		return text;
	}

}
