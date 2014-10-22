package com.sonuto.tabsswipe.adapter;

import com.sonuto.newstabsswipe.BaseballFragment;
import com.sonuto.newstabsswipe.BoxingFragment;
import com.sonuto.newstabsswipe.CricketFragment;
import com.sonuto.newstabsswipe.FootballFragment;
import com.sonuto.newstabsswipe.FormulaFragment;
import com.sonuto.newstabsswipe.NewsHomeFragment;
import com.sonuto.newstabsswipe.RugbyFragment;
import com.sonuto.newstabsswipe.TennisFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsTabsPagerAdapter extends FragmentPagerAdapter {

	public NewsTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated feed fragment activity
			return new NewsHomeFragment();
		case 1:
			// Message fragment activity
			return new FootballFragment();
		case 2:
			// Friends fragment activity
			return new BaseballFragment();
		case 3:
			// Applications fragment activity
			return new FormulaFragment();
		case 4:
			// Applications fragment activity
			return new BaseballFragment();
		case 5:
			// Applications fragment activity
			return new BoxingFragment();
		case 6:
			// Applications fragment activity
			return new TennisFragment();
		case 7:
			// Applications fragment activity
			return new CricketFragment();
		case 8:
			// Applications fragment activity
			return new RugbyFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 9;
	}

}
