package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.NewsApp;
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

public class NewsAppActivity extends FragmentActivity{

	private ViewPager viewPager;
	private NewsTabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	JSONArray jsonTabs;

	private ArrayList<NewsTab> tabList = new ArrayList<NewsTab>();
	private ArrayList<NewsTab> newsList = new ArrayList<NewsTab>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_tab);

		NewsApp newsApp = new NewsApp();

		newsApp.getHomePageData(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				// get json list of news tabs
				JSONObject jsonObject = (JSONObject) object;

				try {
					JSONArray jsonTabs = jsonObject
							.getJSONArray("news_category_list");
					
					Gson gson = new Gson();
					int tabCount = jsonTabs.length();
					for (int i = 0; i < tabCount; i++) {
						NewsTab tab = gson.fromJson(jsonTabs.get(i).toString(),
								NewsTab.class);
						tabList.add(tab);
					}
					
					
					JSONArray newsJsonList = jsonObject
							.getJSONArray("news_list");

					// Initilization
					viewPager = (ViewPager) findViewById(R.id.newspager);
					actionBar = getActionBar();
					mAdapter = new NewsTabsPagerAdapter(
							getSupportFragmentManager(), tabList, newsJsonList.toString());

					viewPager.setAdapter(mAdapter);
					actionBar.setHomeButtonEnabled(false);
					actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

					// Adding Tabs
					for (NewsTab tab : tabList) {
						actionBar.addTab(actionBar.newTab()
								.setText(tab.getTitle())
								.setTabListener(new ActionBar.TabListener() {
									
									@Override
									public void onTabUnselected(Tab tab, FragmentTransaction ft) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void onTabSelected(Tab tab, FragmentTransaction ft) {
										viewPager.setCurrentItem(tab.getPosition());
									}
									
									@Override
									public void onTabReselected(Tab tab, FragmentTransaction ft) {
										// TODO Auto-generated method stub
										
									}
								}));
					}

					/**
					 * on swiping the viewpager make respective tab selected
					 * */
					viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

								@Override
								public void onPageSelected(int position) {
									// on changing the page
									// make respected tab selected
									actionBar
											.setSelectedNavigationItem(position);
								}

								@Override
								public void onPageScrolled(int arg0,
										float arg1, int arg2) {
								}

								@Override
								public void onPageScrollStateChanged(int arg0) {
								}
							});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub

			}
		});
	}
}
