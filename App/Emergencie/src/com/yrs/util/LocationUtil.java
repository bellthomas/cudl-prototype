package com.yrs.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.yrs.emergencie.MainMenu;

public class LocationUtil implements LocationListener{
	
	public static final LocationUtil instance = new LocationUtil();
	
	public Location best = null;
	
	public boolean isEnabled = false;
	
	
	private LocationManager locManager = (LocationManager) MainMenu.instance.getSystemService(Context.LOCATION_SERVICE);
	
	//Interface stuff
	/**
	 * Registers with hyper sensitivity to ensure high accuracy of locating the person in distress
	 * 
	 * @return if it is enabled successfully
	 */
	public void Enable() {
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,0,this);
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100,0,this);
		isEnabled = true;
	}
	
	public void Disable() {
		locManager.removeUpdates(this);
		isEnabled = false;
	}
	
	
	public void DefaultPinPoint() {
		Enable();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {}
		Disable();
	}
	
	
	
	//////////////LocationListener Handler Methods
	
	@Override
	public void onLocationChanged(Location location) {
		//Check if the new location falls outside of the old range
		if((Math.pow(location.getLatitude() - best.getLatitude(),2)
				+ Math.pow(location.getLongitude() - best.getLongitude(),2)) 
				> Math.pow(location.getAccuracy() + best.getAccuracy(),2)) {
					//Is a new location
					best = location;
		}else{
			//Is similar - check which is better
			if(location.getAccuracy() < best.getAccuracy()) {
				best = location;
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		//TODO
	}

	@Override
	public void onProviderEnabled(String provider) {
		//TODO
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		//TODO
		
	}
	
	
	
}
