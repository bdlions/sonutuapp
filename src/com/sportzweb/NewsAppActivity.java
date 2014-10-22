package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sonuto.tabsswipe.adapter.NewsTabsPagerAdapter;
import com.sonuto.tabsswipe.adapter.TabsPagerAdapter;
import com.sonuto.users.Country;
import com.sportzweb.jsonObject.NewsTab;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class NewsAppActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private NewsTabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	private ArrayList<NewsTab> tabList = new ArrayList<NewsTab>();
	// Tab titles
	//private String[] tabs = { "Home", "Football", "Baseball", "Formula","Basketball","Boxing", "Tennis","Cricket","Rugby" };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_tab);

		try {
			//dummy string of json news tabs
			String tabString = "{\"news_tab_list\":[{\"id\": \"1\",\"title\": \"Home\"}, {\"id\": \"2\",\"title\": \"Football\"}, {\"id\": \"3\",\"title\": \"Baseball\"}, {\"id\": \"4\",\"title\": \"Formula\"}]}";
			//get json list of news tabs
			JSONObject jsonObject = new JSONObject(tabString);
			
			JSONArray jsonTabs = jsonObject.getJSONArray("news_tab_list");
			
			Gson gson = new Gson();
			int tabCount = jsonTabs.length();
			for (int i = 0; i < tabCount; i++) {
				NewsTab tab = gson.fromJson(jsonTabs.get(i).toString(), NewsTab.class);
				tabList.add(tab);
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.newspager);
		actionBar = getActionBar();
		mAdapter = new NewsTabsPagerAdapter(getSupportFragmentManager(), tabList);
		

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		// Adding Tabs
		for (NewsTab tab : tabList) {
			actionBar.addTab(actionBar.newTab().setText(tab.getTitle()).setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

}
