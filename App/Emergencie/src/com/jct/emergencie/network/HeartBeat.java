package com.jct.emergencie.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;

import com.yrs.util.LocationUtil;
import com.yrs.util.ServerUtil;

public class HeartBeat {
	
	public HeartBeat() {
		uuid = getRandUUID();
	}
	
	public UUID uuid;
	public int timerToNextBeat = 1000; //TODO choose better value
	
	public UUID getRandUUID() {
		return UUID.randomUUID();
	}
	
	public long[] getUUIData() {
		return new long[] {uuid.getMostSignificantBits(),uuid.getLeastSignificantBits()};
	}
	
	public void runBeat() {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("Request_Type","type.heartbeat");
		data.put("UUID",getUUIData());
		data.put("lat",LocationUtil.instance.best.getLatitude());
		data.put("long",LocationUtil.instance.best.getLongitude());
		try {
			HttpResponse response= ServerUtil.Post("//TODO getURL",data);
			HttpEntity entity = response.getEntity();
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
