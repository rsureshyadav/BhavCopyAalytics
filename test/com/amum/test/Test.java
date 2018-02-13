package com.amum.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.amum.util.CommonLogicImplementation;

public class Test {

	public static void main(String[] args) throws IOException {
		String httpUrl ="https://finance.google.com/finance?output=json&q=JYOTISTRUC";

		String response = null;
		JSONObject jObject = null;
			URL url = new URL(httpUrl);
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// System.out.println("conn.getResponseCode()>>>>"+conn.getResponseCode());
			 if (conn.getResponseCode() == 200) {
				 try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
					 response = reader.lines().collect(Collectors.joining("\n"));
					 response = response.replaceAll("\\s+","");
					 response =response.replace("//[{", "{");
					 response =response.replace("}]}]", "}]}");
					// response = response.replace("[","\"dummy amum\",");
					// response =response.replace(",}", "}");
					// response="{\"age\":100,\"name\":\"mkyong.com\",\"messages\":[\"msg 1\",\"msg\",\"msg 3\"]}";
					 System.out.println(response.trim());
					 jObject = new JSONObject(response);
					 	System.out.println(">>>>>>>>><<<<<<<<<<<<<<<"+jObject.getString("c"));
					 	double last_price = Double.parseDouble(jObject.getString("l"));
					 	System.out.println(last_price);
					 	double prev_close_price = 0;
					 	String pc = jObject.getString("c");
					 	if(pc.contains("-")){
					 		String value = 	pc.replace("-", "");
						 	prev_close_price = Double.parseDouble(value);
						 	prev_close_price = last_price + prev_close_price;
						 	System.out.println(prev_close_price);	
					 	}else{
					 	String value = 	pc.replace("+", "");
					 	prev_close_price = Double.parseDouble(value);
					 	prev_close_price = last_price - prev_close_price;
					 	System.out.println(prev_close_price);
					 	}
						//double last_price = Double.parseDouble(jObject.getString("l").replace(",", ""));
						//double prev_close_price = Double.parseDouble(jObject.getString("pcls_fix").replace(",", ""));
						double profitOrLoss = last_price - prev_close_price;
					 	System.out.println(profitOrLoss);

						String volume =jObject.getString("vo");
						//volume=volume.replace(",", "");
						//System.out.println(">>>>>"+last_price+","+profitOrLoss+","+volume);
				 }
			 }
	}

}
