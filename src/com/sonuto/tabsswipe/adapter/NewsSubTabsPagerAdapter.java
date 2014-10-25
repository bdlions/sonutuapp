package com.sonuto.tabsswipe.adapter;

import java.util.ArrayList;

import com.sonuto.newstabsswipe.NewsCommonFragment;
import com.sonuto.newstabsswipe.NewsHomeFragment;
import com.sportzweb.jsonObject.NewsTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsSubTabsPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<NewsTab> subTabList = new ArrayList<NewsTab>();
	private String newsList;
	public NewsSubTabsPagerAdapter(FragmentManager fm, ArrayList<NewsTab> subTabList, String newsList) {
		super(fm);
		this.subTabList = subTabList;
		this.newsList = newsList;
	}

	@Override
	public Fragment getItem(int index) {
		/*NewsCommonFragment newsCommonFragment =  new NewsCommonFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("tabId", subTabList.get(index).getId());
		bundle.putString("newsList", newsList);
		newsCommonFragment.setArguments(bundle);
		return newsCommonFragment;*/
		return new Fragment();
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 1;
	}

}
