package com.yrs.emergencie.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.yrs.util.ServerUtil;

public class Test {
	
	public static void main(String[] params) {
		PostTestServerUploadMessage("http://emergencie.hbt.io");
		//try {
		//	ServerUtil.TestPost(null,null);
		//} catch (IOException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	}
	
	
	public static void PostTestServerUploadMessage(String url) {
		Map<String,Object> test = new HashMap<String,Object>();
		
		test.put("this_should_be_a_integer_with_a_value_of_five",5);
		test.put("this_should_be_a_double_of_four_point_two",4.2D);
		test.put("this is a string saying TEST","TEST");
		
		try {
			ServerUtil.Post(url, test);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
