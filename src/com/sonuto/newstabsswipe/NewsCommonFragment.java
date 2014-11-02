package com.sonuto.newstabsswipe;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import com.bdlions.components.HListView;
import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.NewsApp;
import com.sonuto.tabsswipe.adapter.NewsSubTabsPagerAdapter;
import com.sonuto.utils.component.CustomAdapter;
import com.sonuto.utils.component.NewsAdapter;
import com.sonuto.utils.component.RecipeBlogCustomAdapter;
import com.sportzweb.BlogAppActivity;
import com.sportzweb.NewsDetailsActivity;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Blogs;
import com.sportzweb.JSONObjectModel.News;
import com.sportzweb.JSONObjectModel.NewsTab;
import com.sportzweb.JSONObjectModel.SubNews;
import com.sportzweb.JSONObjectModel.SubNewsTab;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class NewsCommonFragment extends Fragment  {
	
	ArrayList<String> item;
	ListView listView;
	JSONArray newsJsonList;
	ImageView firstNewsImage;
	TextView firstNewsHeading;
	JSONArray jsonTabs,jsonNewsSubTabs;
	JSONObject jsonCategoryNews;
	public ImageLoader imageLoader;
	String subNewsHeading;
	
	private ViewPager viewPager;
	private NewsSubTabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	JSONObject news;
	private ArrayList<SubNewsTab> categoryNewsList = new ArrayList<SubNewsTab>();
	private ArrayList<SubNewsTab> subCategoryNewsList = new ArrayList<SubNewsTab>();
	LinearLayout parentLayout;
	TextView tv;
	private JSONObject firstNews;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//call server to get information/data for this tabid
		//and set news in the fragment
		imageLoader=new ImageLoader(getActivity().getApplicationContext());
		int tabId = getArguments().getInt("tabId");
		String newsList = getArguments().getString("newsList");
		
		
		

		final View rootView;
		
		
		if(tabId == 0){
			rootView = inflater.inflate(R.layout.activity_all_news, container, false);
			
			listView = (ListView) rootView.findViewById(R.id.listViewNews);
			firstNewsHeading = (TextView) rootView.findViewById(R.id.firstNewsHeadline);
			firstNewsImage = (ImageView) rootView.findViewById(R.id.firstNewsImage);
			try {
				newsJsonList = new JSONArray(newsList);
				processNews(newsJsonList);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		else {
			rootView = inflater.inflate(R.layout.news_sub_category, container, false);
			
			NewsApp newsApp = new NewsApp();
			newsApp.getNewsList(new ICallBack() {
				
				@Override
				public void callBackResultHandler(Object object) {
					JSONObject jsonObject = (JSONObject) object;
					//System.out.print(jsonObject);
					try {
						
						jsonCategoryNews = jsonObject.getJSONObject("category_info_news_list");
						Gson gson = new Gson();
						
						SubNewsTab categoryNews = gson.fromJson(jsonCategoryNews.toString(), SubNewsTab.class);
						String newsCategory = categoryNews.getTitle();
						
						if(categoryNews !=null) {
							parentLayout = (LinearLayout) rootView.findViewById(R.id.parentLayoutForNews);
							
							tv = new TextView(getActivity().getApplicationContext());
							tv.setText(newsCategory);
							tv.setTextColor(Color.parseColor("#00ACEA"));
							tv.setTypeface(null, Typeface.BOLD);
							parentLayout.addView(tv);
							
							Resources res = getResources();
							XmlPullParser parser = res.getXml(R.xml.horizontal_list_model);
							AttributeSet attributes = Xml.asAttributeSet(parser);
							
							//JSONArray blogsJsonList = jsonObject.getJSONArray("blog_list");
							JsonArray newsJsonList = categoryNews.getNews_list();
							int total_news = newsJsonList.size();
							
							// this is for news category horizontal list view
							HListView hListView = new HListView(getActivity().getApplicationContext(), attributes);
							hListView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 200));
							
							SubNews news = new SubNews();
							
							String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/news_app/news/";
							
							ArrayList<SubNews> items = new ArrayList<SubNews>();
							for(int j = 0; j < total_news; j ++){

								news = gson.fromJson(newsJsonList.get(j).toString(),SubNews.class);
								news.setPicture(imagePath + news.getPicture());
								items.add(news);
							}
							NewsAdapter adapter = new NewsAdapter(getActivity().getApplicationContext(), items);
							hListView.setAdapter(adapter);
							
							parentLayout.addView(hListView);
						}
						
						
						
						jsonNewsSubTabs = jsonObject.getJSONArray("subcategory_info_news_list");
						News tabList = new News();
						
						int tabCount = jsonNewsSubTabs.length();
						for (int i = 0; i < tabCount; i++) {
							SubNewsTab subNewsCategory = gson.fromJson(jsonNewsSubTabs.get(i).toString(),SubNewsTab.class);
							subCategoryNewsList.add(subNewsCategory);
							
							tv = new TextView(getActivity().getApplicationContext());
							
							subNewsHeading = subNewsCategory.getTitle();
							
							tv.setText(subNewsHeading);
							tv.setTextColor(Color.parseColor("#00ACEA"));
							tv.setTypeface(null, Typeface.BOLD);
							parentLayout.addView(tv);
							
							Resources res = getResources();
							XmlPullParser parser = res.getXml(R.xml.horizontal_list_model);
							AttributeSet attributes = Xml.asAttributeSet(parser);
							
							//JSONArray blogsJsonList = jsonObject.getJSONArray("blog_list");
							JsonArray blogsJsonList = subNewsCategory.getNews_list();
							int total_blog = blogsJsonList.size();
							
							
							// this is for Sub news category horizontal list view
							HListView hListView = new HListView(getActivity().getApplicationContext(), attributes);
							hListView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 200));
							
							
							SubNews blogs = new SubNews();
							
							String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/news_app/news/";
							
							ArrayList<SubNews> items = new ArrayList<SubNews>();
							for(int j = 0; j < total_blog; j ++){
								//Blogs blogs = new Blogs();
								blogs = gson.fromJson(blogsJsonList.get(j).toString(),SubNews.class);
								blogs.setPicture(imagePath+ blogs.getPicture());
								
								items.add(blogs);
							}
							NewsAdapter adapter = new NewsAdapter(getActivity().getApplicationContext(), items);
							hListView.setAdapter(adapter);
							
							
							parentLayout.addView(hListView);
						}
						
						
						
						
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
		
		//ArrayList<String> item = new ArrayList<String>();
		ArrayList<News> item = new ArrayList<News>();
		
		try {
			//JSONArray jsonArray = new JSONArray(newsList);
			firstNews = (JSONObject)newsList.get(0);
			
			firstNewsHeading.setText(firstNews.get("headline").toString());
			String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/news_app/news/";
			
			firstNewsImage.setImageResource(R.drawable.upload_img_icon);
	        imageLoader.DisplayImage(imagePath+firstNews.get("picture").toString(), firstNewsImage);
			
	      
	        firstNewsImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
						intent.putExtra("news_id", Integer.parseInt(firstNews.get("news_id").toString()));
						intent.putExtra("news_category_title","Home");
						
						getActivity().startActivity(intent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
	        
	        
			
			int newsCount = newsList.length();
			for(int i = 1; i < newsCount; i ++) {
				News newsObj = new News();
				news = (JSONObject)newsList.get(i);
				
				String picture = news.get("picture").toString();
				String description = news.get("headline").toString();
				Integer id = news.getInt("news_id");
				
				newsObj.setId(id);
				newsObj.setTitle(description);
				newsObj.setPicture(imagePath+picture);
				
				item.add(newsObj);
			}
			
			CustomAdapter adapter = new CustomAdapter(getActivity(), item);
			listView.setAdapter(adapter);
			
		} catch (JSONException ie) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
}
