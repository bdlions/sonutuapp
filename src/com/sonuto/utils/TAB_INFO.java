package com.sonuto.utils;

import android.support.v4.app.Fragment;

import com.sonuto.tabsswipe.ApplicationsFragment;
import com.sonuto.tabsswipe.FriendsFragment;
import com.sonuto.tabsswipe.MessageFragment;
import com.sonuto.tabsswipe.SettingsFragment;
import com.sonuto.tabsswipe.ActivityNewsFeed;
import com.sportzweb.R;

public enum TAB_INFO {
	NEWS_FEED("News Feed", R.drawable.chat_bubble, new ActivityNewsFeed()), 
	FRIEND("Friend", R.drawable.followers, new FriendsFragment()), 
	MESSAGE("Message", R.drawable.message, new MessageFragment()), 
	APPLICATION("Application", R.drawable.application, new ApplicationsFragment()), 
	SETTINGS("Settings", R.drawable.gear, new SettingsFragment());
	public final String TITLE;
	public final Fragment INSTANCE;
	public final int ICON;

	TAB_INFO(String title, int icon, Fragment instance) {
		this.TITLE = title;
		this.ICON = icon;
		this.INSTANCE = instance;
	}

	public String getValue() {
		return TITLE;
	}
}
