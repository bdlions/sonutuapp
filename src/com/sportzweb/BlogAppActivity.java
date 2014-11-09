package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bdlions.components.HListView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.utils.component.RecipeBlogCustomAdapter;
import com.sportzweb.JSONObjectModel.Blogs;
import com.sportzweb.JSONObjectModel.BlogsTab;


public class BlogAppActivity extends Fragment{
	// process dialer
	ProgressDialog pDialog;
	private Context mContext;
	String blogCategory;
	private ArrayList<BlogsTab> tabList = new ArrayList<BlogsTab>();
	View rootView;
	Activity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		rootView = inflater.inflate(R.layout.activity_blog_app, null);
		activity = getActivity();
		// gets the activity's default ActionBar
	    ActionBar actionBar = getActivity().getActionBar();
	    actionBar.show();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		process();
		return rootView;
	}

	
	public void process() {
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Fetching data..");
		pDialog.setCancelable(false);
		pDialog.show();
		
		BlogsApp blogsApp = new BlogsApp();
		blogsApp.getHomePageData(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				// get json list of news tabs
				JSONObject jsonObject = (JSONObject) object;
				pDialog.dismiss();
					try {
						JSONArray jsonTabs = jsonObject.getJSONArray("blog_category_blog_list");
						
						//JSONArray customJSONTabs = jsonObject.getJSONArray("blog_custom_category_list");
						
						Gson gson = new Gson();
						//tabList.add(gson.fromJson(customJSONTabs.get(0).toString(),BlogsTab.class));
						
						int tabCount = jsonTabs.length();
						for (int i = 0; i < tabCount; i++) {
							BlogsTab tab = gson.fromJson(jsonTabs.get(i).toString(),BlogsTab.class);
							tabList.add(tab);
						}
						
						/*tabCount = customJSONTabs.length();
						for (int i = 1; i < tabCount; i++) {
							BlogsTab tab = gson.fromJson(customJSONTabs.get(i).toString(),BlogsTab.class);
							tabList.add(tab);
						}*/
						
						
						LinearLayout parentLayout = (LinearLayout)rootView.findViewById(R.id.parentLayout);
						
						for(int i = 0; i < tabList.size(); i ++){
							//blog title
							
							TextView tv = new TextView(getActivity());
							
							blogCategory = tabList.get(i).getTitle();
							
							tv.setText(blogCategory);
							tv.setTextColor(Color.parseColor("#00ACEA"));
							tv.setTypeface(null, Typeface.BOLD);
							parentLayout.addView(tv);
							
							Resources res = getResources();
							XmlPullParser parser = res.getXml(R.xml.horizontal_list_model);
							AttributeSet attributes = Xml.asAttributeSet(parser);
							
							//JSONArray blogsJsonList = jsonObject.getJSONArray("blog_list");
							JsonArray blogsJsonList = tabList.get(i).getBlog_list();
							int total_blog = blogsJsonList.size();
							//blogs item
							HListView hListView = new HListView(getActivity(), attributes);
							hListView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 200));
							//hListView.setDividerWidth(2);
							
							Blogs blogs = new Blogs();
							
							String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/blog_app/";
							
							ArrayList<Blogs> items = new ArrayList<Blogs>();
							for(int j = 0; j < total_blog; j ++){
								//Blogs blogs = new Blogs();
								blogs = gson.fromJson(blogsJsonList.get(j).toString(),Blogs.class);
								blogs.setPicture(imagePath+ blogs.getPicture());
								blogs.setBlog_category(blogCategory);
								items.add(blogs);
							}
							RecipeBlogCustomAdapter adapter = new RecipeBlogCustomAdapter(getActivity(), items);
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
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.blog, menu); //inflate our menu
	    return;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	    switch(item.getItemId()) {
	    case R.id.menu_create_blog:
	        //click on create blog
	    	createBlog();
	        break;
	    case R.id.menu_my_blog:
	        //click on my blogs
	    	myBlog();
	        break;
	    }
	    return true;
	}
	
	 /**
     * Launching new activity
     * */
    private void createBlog() {
        Intent i = new Intent(getActivity(), CreateBlogActivity.class);
        startActivity(i);
    }
    
    /**
     * Launching new activity
     * */
    private void myBlog() {
        Intent i = new Intent(getActivity(), MyBlogActivity.class);
        startActivity(i);
    }
	

}
