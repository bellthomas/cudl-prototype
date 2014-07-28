package com.yrs.emergencie;

import com.yrs.emergencie.test.Test;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainMenu extends Activity {
	
	public static MainMenu instance;
	
	public MainMenu() {
		instance = this;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        //Test Hook IN
        Test.PostTestServerUploadMessage("https://hbt.io?emergencie=true");
        //while(true){
        //	
        //}
        ////////////
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    
    
}
