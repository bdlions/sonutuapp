package com.sunuto.tabsswipe.adapter;

import com.sonuto.tabsswipe.ApplicationsFragment;
import com.sonuto.tabsswipe.MessageFragment;
import com.sonuto.tabsswipe.FriendsFragment;
import com.sonuto.tabsswipe.SettingsFragment;
import com.sonuto.tabsswipe.TopRatedFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated feed fragment activity
			return new TopRatedFragment();
		case 1:
			// Message fragment activity
			return new MessageFragment();
		case 2:
			// Friends fragment activity
			return new FriendsFragment();
		case 3:
			// Applications fragment activity
			return new ApplicationsFragment();
	case 4:
		// Applications fragment activity
		return new SettingsFragment();
	}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}
