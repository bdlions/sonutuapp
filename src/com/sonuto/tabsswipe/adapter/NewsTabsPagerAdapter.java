package com.sonuto.tabsswipe.adapter;

import java.util.ArrayList;

import com.sonuto.newstabsswipe.NewsCommonFragment;
import com.sonuto.newstabsswipe.NewsHomeFragment;
import com.sportzweb.JSONObjectModel.NewsTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsTabsPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<NewsTab> tabList = new ArrayList<NewsTab>();
	private String newsList;
	public NewsTabsPagerAdapter(FragmentManager fm, ArrayList<NewsTab> tabList, String newsList) {
		super(fm);
		this.tabList = tabList;
		this.newsList = newsList;
	}

	@Override
	public Fragment getItem(int index) {
		NewsCommonFragment newsCommonFragment =  new NewsCommonFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("tabId", tabList.get(index).getId());
		bundle.putString("newsList", newsList);
		//newsCommonFragment.setArguments(bundle);
		//return newsCommonFragment;
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return tabList.size();
	}

}
