package com.sonuto.tabsswipe.adapter;

import com.sonuto.utils.TAB_INFO;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		if(TAB_INFO.class.getEnumConstants().length < index)
			return TAB_INFO.class.getEnumConstants()[index].INSTANCE;
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return TAB_INFO.class.getEnumConstants().length;
	}

}
