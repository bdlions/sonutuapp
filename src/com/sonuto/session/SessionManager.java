package com.sonuto.session;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.sonuto.users.UserInfo;

public class SessionManager implements ISessionManager {

	/**
	 * constant values for the preference name and the userinfo
	 * */
	private final String PREF_NAME = "SONUTO_SESSION_PREFERENCE";
	private final String USER_INFO = "USER_INFO";
	
	
	private Context appContext;
	private SharedPreferences sharedPreferences;
	
	public SessionManager(Context context){
		appContext = context;
		sharedPreferences = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}
	
	@Override
	public boolean isLoggedIn() {
		// TODO Auto-generated method stub
		if(sharedPreferences == null){
			throw new NullPointerException();
		}
		String userInfo = sharedPreferences.getString(USER_INFO, null);
		return userInfo != null ? true : false;
	}

	@Override
	public UserInfo getUserInfo() {
		// TODO Auto-generated method stub
		if(sharedPreferences == null){
			throw new NullPointerException();
		}
		
		Gson gson = new Gson();
	    String jsonUserInfo = sharedPreferences.getString(USER_INFO, null);
	    UserInfo userInfo = gson.fromJson(jsonUserInfo, UserInfo.class);
		return userInfo;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		UserInfo userInfo = getUserInfo();
		if(userInfo == null){
			throw new NullPointerException();
		}
		return userInfo.getUserName();
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		UserInfo userInfo = getUserInfo();
		if(userInfo == null){
			throw new NullPointerException();
		}
		return userInfo.getFirstName() + " " + userInfo.getLastName();
	}

	@Override
	public String getEmailAddress() {
		// TODO Auto-generated method stub
		UserInfo userInfo = getUserInfo();
		if(userInfo == null){
			throw new NullPointerException();
		}
		return userInfo.getUserName();
	}

	@Override
	public boolean logInUser(JSONObject userObj) {
		// TODO Auto-generated method stub
			setUserInSession(userObj);
			return true;

	}

	@Override
	public boolean logoutUser() {
		// TODO Auto-generated method stub
		if(sharedPreferences == null){
			throw new NullPointerException();
		}
		
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
		
		return true;
	}
	
	private void setUserInSession(JSONObject userObj){
		Editor editor = sharedPreferences.edit();
		/**
		 * Create a fake user for this time
		 * */
//		UserInfo userInfo = new UserInfo();
//		userInfo.setFirstName("alamgir");
//		userInfo.setLastName("kabir");
//		userInfo.setUserId(1);
//		userInfo.setUserName("abc@yahoo.com");
		/***/
		
		UserInfo userInfo = new UserInfo();
		
		userInfo.setUserId(1);
		
		try {
			userInfo.setFirstName(userObj.get("first_name").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			userInfo.setLastName(userObj.get("last_name").toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			userInfo.setUserName(userObj.get("email").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
	    String jsonUserInfo = gson.toJson(userInfo);
	     
		editor.putString(USER_INFO, jsonUserInfo);
		
		// commit changes
		editor.commit();
	}

	@Override
	public int getUserId() {
		// TODO Auto-generated method stub
		UserInfo userInfo = getUserInfo();
		if(userInfo == null){
			throw new NullPointerException();
		}
		return userInfo.getUserId();
	}

}
