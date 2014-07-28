package com.yrs.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerUtil {
	
	
	public static void Post(String url,Map<String,Object> map) throws JSONException,ClientProtocolException,IOException{
		System.out.println("Post_Test");
		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe("THIS IS POSTING");
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject json = new JSONObject();
		for(String key : map.keySet()) {
			json.put(key, map.get(key));
		}
		StringEntity string = new StringEntity(json.toString());
		//StringEntity string = new StringEntity("{'query':'Pizza','locations':[94043,90210]}");
		post.setEntity(string);
		
		post.setHeader("Accept", "application/json");
	    post.setHeader("Content-type", "application/json");
	    
	    ResponseHandler res = new BasicResponseHandler();
	    HttpClient resp = client.execute(post, res);
	    
	    
	    
		//TODO chosen format for server
		//String dat = json.toString();
		
		//post.addHeader("JSON_DATA",dat);
		//client.execute(post);
		//HttpResponse response = client.execute(post);
		//Header header = response.getFirstHeader("");
		//System.out.println(header.getValue());
	}
	
	public static void TestPost(String r,Map<String,Object> map) throws IOException{
		String urlParameters = "param1=a&param2=b&param3=c";
		String request = "https://hbt.io?emergencie=true";
		URL url = new URL(request); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(false); 
		connection.setRequestMethod("POST"); 
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		connection.setUseCaches (false);

		DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		connection.disconnect();
	}
	
	
	
}
