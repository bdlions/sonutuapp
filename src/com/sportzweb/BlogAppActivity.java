package com.sportzweb;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.jar.Attributes;

import org.apache.http.client.cache.Resource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Xml;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meetme.android.horizontallistview.HorizontalListView;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.tabsswipe.adapter.BlogsTabsPagerAdapter;
import com.sonuto.tabsswipe.adapter.NewsTabsPagerAdapter;
import com.sonuto.utils.component.RecipeBlogCustomAdapter;
import com.sportzweb.jsonObject.BlogsTab;
import com.sportzweb.jsonObject.NewsTab;

public class BlogAppActivity extends Activity{

	private ViewPager viewPager;
	private BlogsTabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	private ArrayList<BlogsTab> tabList = new ArrayList<BlogsTab>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthy_recipes);
		
		BlogsApp newsApp = new BlogsApp();

		newsApp.getHomePageData(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				// get json list of news tabs
				JSONObject jsonObject = (JSONObject) object;

				
					try {
						JSONArray jsonTabs = jsonObject.getJSONArray("blog_category_list");
						
						JSONArray customJSONTabs = jsonObject.getJSONArray("blog_custom_category_list");
						
						
						Gson gson = new Gson();
						tabList.add(gson.fromJson(customJSONTabs.get(0).toString(),BlogsTab.class));
						
						int tabCount = jsonTabs.length();
						for (int i = 0; i < tabCount; i++) {
							BlogsTab tab = gson.fromJson(jsonTabs.get(i).toString(),BlogsTab.class);
							tabList.add(tab);
						}
						
						tabCount = customJSONTabs.length();
						for (int i = 1; i < tabCount; i++) {
							BlogsTab tab = gson.fromJson(customJSONTabs.get(i).toString(),BlogsTab.class);
							tabList.add(tab);
						}
						
						
						LinearLayout parentLayout = (LinearLayout)findViewById(R.id.parentLayout);
						
						for(int i = 0; i < tabList.size(); i ++){
							//blog title
							
							TextView tv = new TextView(getApplicationContext());
							tv.setText(tabList.get(i).getTitle());
							parentLayout.addView(tv);
							
							Resources res = getResources();
							XmlPullParser parser = res.getXml(R.xml.horizontal_list_model);
							AttributeSet attributes = Xml.asAttributeSet(parser);
							
							//blogs item
							HorizontalListView hListView = new HorizontalListView(getApplicationContext(), attributes);
							hListView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 200));
							hListView.setDividerWidth(2);
							
							ArrayList<String> items = new ArrayList<String>();
							items.add("Item 1");
							items.add("Item 2");
							items.add("Item 3");
							items.add("Item 4");
							items.add("Item 1");
							items.add("Item 1");
							items.add("Item 2");
							items.add("Item 3");
							items.add("Item 4");
							items.add("Item 1");
							items.add("Item 1");
							items.add("Item 2");
							items.add("Item 3");
							items.add("Item 4");
							items.add("Item 1");
							items.add("Item 1");
							items.add("Item 2");
							items.add("Item 3");
							items.add("Item 4");
							items.add("Item 1");
							RecipeBlogCustomAdapter adapter = new RecipeBlogCustomAdapter(BlogAppActivity.this, items);
							hListView.setAdapter(adapter);
							
							
							parentLayout.addView(hListView);
							
						}
						
						
						
					}
					catch(Exception ex){
						
					}
					
				}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
