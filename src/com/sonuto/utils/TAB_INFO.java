package com.sonuto.utils;

import com.sportzweb.R;

public enum TAB_INFO {
	NEWS_FEED("News Feed", R.drawable.chat_bubble), FRIEND("Friend", R.drawable.followers), MESSAGE("Message", R.drawable.message), APPLICATION("Application", R.drawable.application), SETTINGS("Settings", R.drawable.gear);
	public final String TITLE;
	public final int ICON;

	TAB_INFO(String title, int icon) {
		this.TITLE = title;
		this.ICON = icon;
	}

	public String getValue() {
		return TITLE;
	}
}
