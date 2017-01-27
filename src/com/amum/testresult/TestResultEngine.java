package com.amum.testresult;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class TestResultEngine {

	public static void main(String str[]) throws IOException{
		
		String out = new Scanner(new URL("http://www.google.com").openStream(), "UTF-8").useDelimiter("\\A").next();
		System.out.println(out);
		/*String url ="https://www.google.com/finance/info?q=NSE:STARPAPER";
		String mJsonString = downloadFileFromInternet(url);
		System.out.println(mJsonString);*/
		/*JSONObject jObject = null;
		try {
		    jObject = new JSONObject(mJsonString);
		} 
		catch (JSONException e) {
		    e.printStackTrace();
		}*/
	}

	private static  String downloadFileFromInternet(String url)
	{
	    if(url == null /*|| url.isEmpty() == true*/)
	        new IllegalArgumentException("url is empty/null");
	    StringBuilder sb = new StringBuilder();
	    InputStream inStream = null;
	    try
	    {
	        url = urlEncode(url);
	        URL link = new URL(url);
	        inStream = link.openStream();
	        int i;
	        int total = 0;
	        byte[] buffer = new byte[8 * 1024];
	        while((i=inStream.read(buffer)) != -1)
	        {
	            if(total >= (1024 * 1024))
	            {
	                return "";
	            }
	            total += i;
	            sb.append(new String(buffer,0,i));
	        }
	    }
	    catch(Exception e )
	    {
	        e.printStackTrace();
	        return null;
	    }catch(OutOfMemoryError e)
	    {
	        e.printStackTrace();
	        return null;
	    }
	    return sb.toString();
	}

	private static String urlEncode(String url)
	{
	    if(url == null /*|| url.isEmpty() == true*/)
	        return null;
	    url = url.replace("[","");
	    url = url.replace("]","");
	    url = url.replaceAll(" ","%20");
	    return url;
	}
	/*private static String downloadFileFromInternet(String url) throws IOException {
		String response;
		URL website = new URL(url);
		
		URLConnection conn = website.openConnection();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
			response = reader.lines().collect(Collectors.joining("\n"));
		}
		System.out.println(">>>>>"+response);

        return response;
	}*/
}
