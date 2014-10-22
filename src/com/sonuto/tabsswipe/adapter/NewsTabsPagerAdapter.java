package com.sonuto.tabsswipe.adapter;

import java.util.ArrayList;

import com.sonuto.newstabsswipe.NewsCommonFragment;
import com.sonuto.newstabsswipe.NewsHomeFragment;
import com.sportzweb.jsonObject.NewsTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsTabsPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<NewsTab> tabList = new ArrayList<NewsTab>();
	public NewsTabsPagerAdapter(FragmentManager fm, ArrayList<NewsTab> tabList) {
		super(fm);
		this.tabList = tabList;
	}

	@Override
	public Fragment getItem(int index) {
		if(index == 0){
			return new NewsHomeFragment();
		}
		else{
			NewsCommonFragment newsCommonFragment =  new NewsCommonFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("tabId", tabList.get(index).getId());
			newsCommonFragment.setArguments(bundle);
			return newsCommonFragment;
		}

	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return tabList.size();
	}

}
