package com.yrs.emergencie.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class EmergencieWidgetProvider extends AppWidgetProvider{
	
	public static final String widgetAction = "emergencie_widget.clickAction";
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		
		for(int i = 0; i < appWidgetIds.length; i++) {
			
			Intent intent = new Intent(context, /**TODO get class to use**/ appWidgetManager.getAppWidgetInfo(i).getClass());
			intent.setAction(widgetAction);
			PendingIntent pendInt = PendingIntent.getBroadcast(context,0,intent,0);
			
			//Do something with intents???
		}
		
		
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if(intent.getAction().equals(widgetAction)) {
			//TODO Run
		}
		
	}
	
	
}
