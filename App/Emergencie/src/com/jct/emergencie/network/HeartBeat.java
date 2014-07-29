package com.jct.emergencie.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	
	
	public void runBeat() {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("emie_heartbeat",new long[] {uuid.getMostSignificantBits(),uuid.getLeastSignificantBits()});
		data.put("emie_location",new double[] {LocationUtil.instance.best.getLatitude(),LocationUtil.instance.best.getLongitude()});
		try {
			HttpResponse response= ServerUtil.Post("http://emergencie.hbt.io/heartbeat",data);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			BufferedInputStream buffIn = new BufferedInputStream(stream);
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
