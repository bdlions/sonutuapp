package com.sonuto.utils;

import android.support.v4.app.Fragment;

import com.sonuto.tabsswipe.*;
import com.sportzweb.*;

public enum SETTINGS_INFO {
	CREATE_BUSINESS_PROFILE("Create Business Profile", new BmiCalculatorFragment()), 
	ACCOUNT_SETTINGS("Account Settings", new HealthyRecipeAppActivity()),
	PROFILE_SETTINGS("Profile Settings", new NewsAppActivity()),
	LOGOUT("Log out", new ServiceDirectoryActivity());
	
	public final String TITLE;
	public final Fragment INSTANCE;

	SETTINGS_INFO(String title, Fragment instance) {
		this.TITLE = title;
		this.INSTANCE = instance;
	}

	public String getValue() {
		return TITLE;
	}
}
