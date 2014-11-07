package com.sonuto.session;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.sonuto.users.BusinessProfileInfo;
import com.sonuto.users.UserInfo;

public class SessionManager implements ISessionManager {

	/**
	 * constant values for the preference name and the userinfo
	 * */
	private final String PREF_NAME = "SONUTO_SESSION_PREFERENCE";
	private final String USER_INFO = "USER_INFO";
	private final String BP_INFO = "BP_INFO";
	
	
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
			return false;
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
		return userInfo.getEmail();
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
		editor.putString(USER_INFO, userObj.toString());		
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

	@Override
	public boolean logInUserBusinessProfile(JSONObject userBpobj) {
		setUsersBPInSession(userBpobj);
		return true;
	}
	
	private void setUsersBPInSession(JSONObject usersBpObj){
		Editor editor = sharedPreferences.edit();	
		editor.putString(BP_INFO, usersBpObj.toString());		
		// commit changes
		editor.commit();
	}
	
	@Override
	public BusinessProfileInfo getUsersBusinessProfileInfo() {
		// TODO Auto-generated method stub
		if(sharedPreferences == null){
			throw new NullPointerException();
		}
		
		Gson gson = new Gson();
	    String jsonUsersBpInfo = sharedPreferences.getString(BP_INFO, null);
	    BusinessProfileInfo usersBpInfo = gson.fromJson(jsonUsersBpInfo, BusinessProfileInfo.class);
		return usersBpInfo;
	}
	
	
	@Override
	public int getUsersBusinessProfileId() {
		// TODO Auto-generated method stub
		BusinessProfileInfo userBPInfo = getUsersBusinessProfileInfo();
		if(userBPInfo == null){
			throw new NullPointerException();
		}
		return userBPInfo.getUserBusinessProfileId();
	}
	
	@Override
	public String getUsersBusinessProfileName() {
		// TODO Auto-generated method stub
		BusinessProfileInfo userBPInfo = getUsersBusinessProfileInfo();
		if(userBPInfo == null){
			throw new NullPointerException();
		}
		return userBPInfo.getBusinessProfileName();
	}
	

}
