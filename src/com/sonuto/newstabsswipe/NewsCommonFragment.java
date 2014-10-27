package com.sonuto.newstabsswipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.NewsApp;
import com.sonuto.tabsswipe.adapter.NewsSubTabsPagerAdapter;
import com.sonuto.tabsswipe.adapter.NewsTabsPagerAdapter;
import com.sonuto.utils.component.CustomAdapter;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.NewsTab;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewsCommonFragment extends Fragment  {
	
	ArrayList<String> item;
	ListView listView;
	JSONArray newsJsonList;
	ImageView firstNewsImage;
	TextView firstNewsHeading;
	JSONArray jsonTabs;
	
	private ViewPager viewPager;
	private NewsSubTabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	private ArrayList<NewsTab> subTabList = new ArrayList<NewsTab>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//call server to get information/data for this tabid
		//and set news in the fragment
		final View rootView = inflater.inflate(R.layout.activity_all_news, container, false);
		listView = (ListView) rootView.findViewById(R.id.listViewNews);
		firstNewsHeading = (TextView) rootView.findViewById(R.id.firstNewsHeadline);
		firstNewsImage = (ImageView) rootView.findViewById(R.id.firstNewsImage);
		
		
		int tabId = getArguments().getInt("tabId");
		String newsList = getArguments().getString("newsList");
		if(tabId == 0){
			try {
				newsJsonList = new JSONArray(newsList);
				processNews(newsJsonList);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		else{
			NewsApp newsApp = new NewsApp();
			newsApp.getNewsList(new ICallBack() {
				
				@Override
				public void callBackResultHandler(Object object) {
					JSONObject jsonObject = (JSONObject) object;
					//System.out.print(jsonObject);
					JSONArray jsonSubTabs;
					try {
						jsonSubTabs = jsonObject.getJSONArray("news_subcategory_list");
						Gson gson = new Gson();
						int tabCount = jsonSubTabs.length();
						for (int i = 0; i < tabCount; i++) {
							NewsTab tab = gson.fromJson(jsonSubTabs.get(i).toString(),
									NewsTab.class);
							subTabList.add(tab);
						}
						
						JSONArray newsJsonList = jsonObject
								.getJSONArray("news_list");
						
						// Initilization
						viewPager = (ViewPager) getActivity().findViewById(R.id.subNewsPager);
						actionBar = getActivity().getActionBar();
						
						String newsList = null;
						if(newsJsonList != null){
							newsList = newsJsonList.toString();
						}
						mAdapter = new NewsSubTabsPagerAdapter(getActivity().getSupportFragmentManager(), subTabList, newsList);

						viewPager.setAdapter(mAdapter);
						actionBar.setHomeButtonEnabled(false);
						actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
						
						// Adding Tabs
						for (NewsTab tab : subTabList) {
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
			}, tabId);
		}
		

		
		
		//TextView tv = (TextView) rootView.findViewById(R.id.textViewSample);
		//tv.setText("Tab Id: " + tabId);
		
		return rootView;
	}
	
	private void processNews(JSONArray newsList){
		
		ArrayList<String> item = new ArrayList<String>();
		
		try {
			//JSONArray jsonArray = new JSONArray(newsList);
			JSONObject news = (JSONObject)newsList.get(0);
			
			firstNewsHeading.setText(news.get("headline").toString());
			String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/news_app/news/"+ news.get("picture").toString();
	
			firstNewsImage.setImageURI(Uri.parse(imagePath));
			
			
			int newsCount = newsList.length();
			for(int i = 1; i < newsCount; i ++){
				news = (JSONObject)newsList.get(i);
				
				String picture = news.get("picture").toString();
				String description = news.get("headline").toString();
				item.add(description);
			}
			CustomAdapter adapter = new CustomAdapter(getActivity(), item);
			listView.setAdapter(adapter);
			
		} catch (JSONException ie) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
}
