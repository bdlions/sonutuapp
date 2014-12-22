package com.sportzweb;

import com.bdlions.components.ViewPagerWithHList;
import com.sonuto.nodejs.notification.MessageObserver;
import com.sonuto.nodejs.notification.NodeConnector;
import com.sonuto.nodejs.notification.NodeEvent;
import com.sonuto.tabsswipe.ApplicationsFragment;
import com.sonuto.tabsswipe.adapter.TabsPagerAdapter;
import com.sonuto.utils.TAB_INFO;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.ActionBar.TabListener;
;

public class ApplicationPane extends FragmentActivity implements MessageObserver{
	private ViewPagerWithHList viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
		setContentView(R.layout.activity_tab);
		NodeConnector.getInstance().registerObserver(this);
		
		// Initilization
		viewPager = (ViewPagerWithHList) findViewById(R.id.pager);
		viewPager.setPagingEnabled(false);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		

		// Adding Tabs
		for (final TAB_INFO tab_info : TAB_INFO.class.getEnumConstants()) {
			
			Drawable drawableIcon = ApplicationPane.this.getResources().getDrawable(tab_info.ICON);

			Tab tab = actionBar.newTab();
			tab.setIcon(drawableIcon);
			viewPager.setOffscreenPageLimit(TAB_INFO.class.getEnumConstants().length);
			
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
					if(tab.getPosition() == 3){
						((ApplicationsFragment)TAB_INFO.APPLICATION.INSTANCE).reload();
					}
				}
			});
			actionBar.addTab(tab);
		}// endfor

	}

	@Override
	public void onUpdate(NodeEvent event, String from, String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
	}

}
