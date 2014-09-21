package com.sampanit.sonutoapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PersistentUser {

	private static final String USERNAME = "userinfo";
	private static final String EMAIL = "email";
	private static final String USERDATA = "userdata";
	private static final String PASSWORD = "password";
	
	// App Preferences
	private static final String PREFS_FILE_NAME = "AppPreferences";

	public static String getPassword(final Context ctx) {
		return "123456";
	}

	public static void setPassword(final Context ctx, final String data) {
		
	}

	public static String getUserName(final Context ctx) {
		return "omar";
	}

	public static String getUserInfo(final Context ctx) {
		return "user";
	}
	
	public static String getEmail(final Context ctx) {
		return "omar";
	}

	
	public static void setEmail(final Context ctx, final String data) {
		
	}
	
	public static void setUserName(final Context ctx, final String data) {
		
	}

	public static void setUserInfo(final Context ctx, final String data) {
		
	}

	public static void logOut(Context c) {

		

	}

	public static void setLogin(Context c) {

	}

	public static boolean isLogged(Context c) {
		return true;
	}

}
