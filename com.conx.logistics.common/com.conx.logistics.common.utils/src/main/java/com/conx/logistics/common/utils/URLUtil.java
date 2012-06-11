package com.conx.logistics.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

public class URLUtil {

	public static String readURLAsString(URL url) throws IOException, URISyntaxException
	{
		File file = new File(url.toURI());
		
	    FileInputStream in = new FileInputStream(file);
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));

	    String inputLine;
	    StringBuffer sb = new StringBuffer();
	    while ((inputLine = br.readLine()) != null)
	    	sb.append(inputLine);
	    in.close();		
	    
	    return sb.toString();
	}
}
