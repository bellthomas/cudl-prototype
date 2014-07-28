package com.yrs.emergencie.widget;

import android.location.Location;

import com.yrs.util.EmergencieConfig;
import com.yrs.util.LocationUtil;

/**
 * 
 * Class to add all relevant handling to for the button press on a widget
 *
 */
public class WidgetActivationHandler {
	
	
	public static void HandePress(EmergencieConfig config) {
		//Pre
		LocationUtil.instance.DefaultPinPoint();
		Location here = LocationUtil.instance.best;
		
		//Add Web Pinging
		
		//Norm
		config.Execute();
		
		
		
		//Post
		
		//Maybe Sound playing etc.
	}
	
	
	
	
}
