package com.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServerUtil {
	
	/**
	 * 
	 * @param url - page to visit and return the result
	 * @return string - data from visit
	 * @throws IOException - on error visiting
	 */
	public static String visitURl(String url)throws IOException {
		URL visit = new URL(url);
		//Open connections
		
				URLConnection conn = visit.openConnection();
				conn.connect();
				InputStream stream = conn.getInputStream();
				
				//Read stream
				
				int nRead;
				byte[] bytes = new byte[16384];
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				while ((nRead = stream.read(bytes, 0, bytes.length)) != -1) {
				  buffer.write(bytes, 0, nRead);
				}
				buffer.flush();
				
				//Parse response to string
				
				String out = "";
				for(byte BYTE : buffer.toByteArray()) {
					out = out + ((char)BYTE);
				}
				
				
				return out;
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public static String makeRequest(String[] actions,Map<?,?>[] params) throws IOException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("emie_actions",actions);
		map.put("emie_parameters",params);		
		String jString = JsonHelper.toString(map);
		String encoded = URLEncoder.encode(jString);
		return ServerUtil.visitURl("http://emergencie.hbt.io/api?emie_request="+encoded);
	}
	
	public static String makeNearbyHospitalRequest(double latitude, double longitude) throws IOException{
		String[] actions = new String[] {"NearestHospital"};
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat",latitude);
		params.put("long",longitude);
		Map<?,?>[] parameters = new Map<?,?>[]{params};		
		return makeRequest(actions, parameters);
	}
	
	public static String makeNearbyStreetRequest(double latitude, double longitude) throws IOException{
		String[] actions = new String[] {"NearestStreet"};
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat",latitude);
		params.put("long",longitude);
		Map<?,?>[] parameters = new Map<?,?>[]{params};		
		return makeRequest(actions, parameters);
	}
	
	public static String makeNearbyTownRequest(double latitude, double longitude) throws IOException{
		String[] actions = new String[] {"LatLongToLocal"};
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat",latitude);
		params.put("long",longitude);
		Map<?,?>[] parameters = new Map<?,?>[]{params};		
		return makeRequest(actions, parameters);
	}
	
	/**
	 * 
	 * @param article - what to search for
	 * @return json ready to parse
	 * @throws IOException
	 */
	public static String makeArticlesRequest(String article) throws IOException {
		String[] actions = new String[]{"GetArticles"};
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("search",article);
		Map<?,?>[] parameters = new Map<?,?>[]{params};
		return makeRequest(actions, parameters);
	}
	/**
	 * 
	 * @param article
	 * @return string array of size 3 with article urls
	 * @throws IOException
	 */
	public static String[] parsedArticlesRequest(String article) throws IOException {
		String response = makeArticlesRequest(article);
		List<Map<String, Object>> lists = JsonHelper.getListedJsonsWithCommaFormatting(response);
		Map<String,Object> map = lists.get(0);
		try{
			return new String[] {(String) map.get("article_0"),(String) map.get("article_1"),(String) map.get("article_2")};
		}catch(Exception e) {
			return new String[] {"ERROR","ERROR","ERROR"};
		}
	}
	/**
	 * 
	 * @param latitude
	 * @param longitude
	 * @return map with "name"->name(String) and "distance"->dist(double)
	 * @throws IOException
	 */
	public static Map<String,Object> parsedStreetRequest(double latitude, double longitude) throws IOException {
		String response = makeNearbyStreetRequest(latitude,longitude);
		List<Map<String,Object>> lists = JsonHelper.getListedJsonsWithCommaFormatting(response);
		try {
			Map<String,Object> map = lists.get(0);
			if(map.get("distance") instanceof String) {
				System.out.println("StringDist = " + map.get("distance"));
				String str_dist = (String)map.get("distance");
				map.put("distance",Double.parseDouble(str_dist.substring(1,str_dist.length() - 1)));
			}
			return map;
		}catch(Exception e) {
			Map<String,Object> error = new HashMap<String,Object>();
			error.put("name","ERROR");
			error.put("distance",Double.MAX_VALUE);
			return error;
		}
	}
	
	
	public static Map<String,Object> parsedTownRequest(double latitude, double longitude) throws IOException {
		String response = makeNearbyTownRequest(latitude, longitude);
		List<Map<String,Object>> lists = JsonHelper.getListedJsonsWithCommaFormatting(response);
		try {
			Map<String,Object> map = lists.get(0);
			if(map.get("distance") instanceof String) {
				System.out.println("StringDist = " + map.get("distance"));
				String str_dist = (String)map.get("distance");
				map.put("distance",Double.parseDouble(str_dist.substring(1,str_dist.length() - 1)));
			}
			return map;
		}catch(Exception e) {
			Map<String,Object> error = new HashMap<String,Object>();
			error.put("name","ERROR");
			error.put("distance",Double.MAX_VALUE);
			error.put("countryName","ERROR");
			return error;
		}
	}
	
	
	
	
	
	
	//Use to test if the responses from the parsed requests are errored data
	public static boolean isErroredData(Object object) {
		if(object instanceof Map<?,?>) {
			try {
				if(((Map<String,Object>)object).get("name") == "ERROR") {
					return true;
				}
			}catch(Exception e) {}
		}
		if(object.getClass().isArray()) {
			try{
				Object[] array = JsonHelper.toObjectArray(object);
				if(array instanceof String[]) {
					if(((String[])array)[0] == "ERROR") {
						return true;
					}
				}
				
			}catch(Exception e) {}
		}
		
		
		
		
		return false;
	}
	
	
	
}

	
	
