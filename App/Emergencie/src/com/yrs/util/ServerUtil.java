package com.yrs.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerUtil {
	
	
	public static void Post(String url,Map<String,Object> map) throws JSONException,ClientProtocolException,IOException{
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject json = new JSONObject();
		for(String key : map.keySet()) {
			json.put(key, map.get(key));
		}
		//TODO chosen format for server
		
		
		//HttpResponse response = 
		client.execute(post);
	}
	
	
	
	
	
}
