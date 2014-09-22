package com.sonuto.session;

import com.sonuto.users.UserInfo;

public interface ISessionManager {
	public boolean isLoggedIn();
	public UserInfo getUserInfo();
	public String getUserName();
	public String getDisplayName();
	public String getEmailAddress();
	public boolean logInUser(String email, String password);
	public boolean logoutUser();
	public int getUserId();
	
}
