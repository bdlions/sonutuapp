package com.sonuto.tabsswipe.adapter;

import java.util.ArrayList;

import com.sonuto.newstabsswipe.BlogsCommonFragment;
import com.sonuto.newstabsswipe.NewsCommonFragment;
import com.sonuto.newstabsswipe.NewsHomeFragment;
import com.sportzweb.jsonObject.BlogsTab;
import com.sportzweb.jsonObject.NewsTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BlogsTabsPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<BlogsTab> tabList = new ArrayList<BlogsTab>();
	private String blogsList;
	public BlogsTabsPagerAdapter(FragmentManager fm, ArrayList<BlogsTab> tabList, String blogsList) {
		super(fm);
		this.tabList = tabList;
		this.blogsList = blogsList;
	}

	@Override
	public Fragment getItem(int index) {
		BlogsCommonFragment blogsCommonFragment =  new BlogsCommonFragment();
		Bundle bundle = new Bundle();
		if(tabList.get(index) == null || tabList.get(index).getId() == 1){
			bundle.putInt("tabId", 1);
			bundle.putString("blogsList", blogsList);
		}
		else{
			bundle.putInt("tabId", tabList.get(index).getId());
		}
		blogsCommonFragment.setArguments(bundle);
		return blogsCommonFragment;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return tabList.size();
	}

}
