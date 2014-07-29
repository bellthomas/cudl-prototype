package com.yrs.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerUtil {
	
	
	public static HttpResponse Post(String url,Map<String,Object> map) throws JSONException,ClientProtocolException,IOException{
		System.out.println("Post_Test");
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
	    
	    HttpContext context = new BasicHttpContext();
	    
	    HttpResponse resp = client.execute(post, context);
	    return resp;
	    
	}
	
	
	
}
