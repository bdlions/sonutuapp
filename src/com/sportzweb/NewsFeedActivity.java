package com.sportzweb;

import com.sonuto.tabsswipe.adapter.TabsPagerAdapter;
import com.sonuto.utils.TAB_INFO;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.app.ActionBar.TabListener;
;

public class NewsFeedActivity extends FragmentActivity {
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (final TAB_INFO tab_info : TAB_INFO.class.getEnumConstants()) {
			
			Drawable drawableIcon = NewsFeedActivity.this.getResources().getDrawable(tab_info.ICON);

			Tab tab = actionBar.newTab();
			tab.setIcon(drawableIcon);
			tab.setTabListener(new TabListener() {

				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction fgt) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onTabSelected(Tab tab, FragmentTransaction fgt) {
					viewPager.setCurrentItem(tab.getPosition());
					setTitle(tab_info.TITLE);
				}

				@Override
				public void onTabReselected(Tab tab, FragmentTransaction fgt) {
					// TODO Auto-generated method stub

				}
			});
			actionBar.addTab(tab);
		}// endfor

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

}
