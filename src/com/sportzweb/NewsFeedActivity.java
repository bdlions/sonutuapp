package com.sportzweb;

import com.sonuto.tabsswipe.adapter.TabsPagerAdapter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.app.ActionBar.TabListener;;

public class NewsFeedActivity extends FragmentActivity{
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    
 
    private enum TAB_INFO {
	NEWS_FEED("News Feed", R.drawable.chat_bubble), 
	FRIEND("Friend",R.drawable.followers), 
	MESSAGE("Message", R.drawable.message), 
	APPLICATION("Application", R.drawable.application), 
	SETTINGS("Settings",R.drawable.gear);
	private final String TITLE;
	private final int ICON;

	TAB_INFO(String title, int icon) {
	    this.TITLE = title;
	    this.ICON = icon;
	}

	public String getValue() {
	    return TITLE;
	}
    }

    //tabs title on the main appliction
    private final TAB_INFO TABS[] = {TAB_INFO.NEWS_FEED, TAB_INFO.FRIEND, TAB_INFO.MESSAGE, TAB_INFO.APPLICATION, TAB_INFO.SETTINGS};
    
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
	for (int i = 0; i < TABS.length; i++) {
	    
	    Drawable drawableIcon = NewsFeedActivity.this.getResources().getDrawable(TABS[i].ICON);
	    
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
	            setTitle(TABS[tab.getPosition()].TITLE);
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
