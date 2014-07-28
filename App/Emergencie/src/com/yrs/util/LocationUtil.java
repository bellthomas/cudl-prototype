package com.yrs.util;

import com.yrs.emergencie.MainMenu;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class LocationUtil implements LocationListener{
	
	public double latitude = 0;
	public double longitude = 0;
	public double altitude = 0;
	public double accuracy = 0;
	
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
	
	
	
	
	
	
	//////////////LocationListener Handler Methods
	
	@Override
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		altitude = location.getAltitude();
		accuracy = location.getAccuracy();
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
