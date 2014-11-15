package com.sonuto.utils;

import android.support.v4.app.Fragment;

import com.sonuto.applications.xstreambanter.SportsActivity;
import com.sportzweb.*;

public enum APP_INFO {
	BMI_CALC("BMI Calculator", new BmiCalculatorActivity()), 
	HEALTHY_RECIPE("Healthy Recipe", new HealthyRecipeAppActivity()),
	NEWS("News", new NewsAppActivity()),
	SERVICE_DIRECTORY("Service Directory", new ServiceDirectoryActivity()),
	BLOG("Blog", new BlogAppActivity()),
	XSTREAM_BANTER("Xstream Banter", new SportsActivity());
	
	public final String TITLE;
	public final Fragment INSTANCE;

	APP_INFO(String title, Fragment instance) {
		this.TITLE = title;
		this.INSTANCE = instance;
	}

	public String getValue() {
		return TITLE;
	}
}
