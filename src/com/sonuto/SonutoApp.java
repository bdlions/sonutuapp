package com.sonuto;

import android.app.Application;
import android.content.Context;

public class SonutoApp extends Application{
	private static Context context;
	
	public void onCreate(){
        super.onCreate();
        SonutoApp.context = getApplicationContext();
    }
	
	public static Context getAppContext(){
		return context;
	}

}
