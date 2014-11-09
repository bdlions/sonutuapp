package com.sonuto.utils;

import android.support.v4.app.Fragment;

import com.sonuto.tabsswipe.*;
import com.sportzweb.*;

public enum APP_INFO {
	BMI_CALC("BMI Calculator", new BmiCalculatorFragment()), 
	HEALTHY_RECIPE("Healthy Recipe", new HealthyRecipeAppActivity()),
	NEWS("News", new NewsAppActivity()), 
	BLOG("Blog", new BlogAppActivity());
	
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
