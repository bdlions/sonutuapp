package com.sonuto.newstabsswipe;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.components.HListView;
import com.google.gson.Gson;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.rpc.register.NewsApp;
import com.sonuto.utils.component.CustomAdapter;
import com.sonuto.utils.component.RecipeBlogCustomAdapter;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Blogs;
import com.sportzweb.JSONObjectModel.News;

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

public class BlogsCommonFragment extends Fragment {
	
	JSONArray blogsJsonList;
	ArrayList<String> item;
	HListView lvTest,lvTest1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_blog_app,container, false);
		 //lvTest = (HorizontalListView) rootView.findViewById(R.id.HorizontalListView);
		 //lvTest1 = (HorizontalListView) rootView.findViewById(R.id.HorizontalListView1);
		
		
		int tabId = getArguments().getInt("tabId");
		String blogsList = getArguments().getString("blogsList");
		

		if (tabId == 1) {
			try {
				blogsJsonList = new JSONArray(blogsList);
				processNews(blogsJsonList);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		} else {
			// call server to get information/data for this tabid
			// and set news in the fragment

			ArrayList<Blogs> items = new ArrayList<Blogs>();
			for(int j = 0; j < 20; j ++){
				Blogs news = new Blogs();
				news.setId(j + 1);
				news.setTitle("title: " + j + 1);
				news.setPicture("http://lh6.googleusercontent.com/-spR6L3z1hHQ/AAAAAAAAAAI/AAAAAAAAAAA/hVXPzP19P1Q/s32-c/photo.jpg");
				items.add(news);
			}
			//HorizontalListView lvTest = (HorizontalListView) rootView
			//		.findViewById(R.id.HorizontalListView);
			//HorizontalListView lvTest1 = (HorizontalListView) rootView
				//	.findViewById(R.id.HorizontalListView1);



			RecipeBlogCustomAdapter adapter = new RecipeBlogCustomAdapter(getActivity(), items);
			lvTest.setAdapter(adapter);
			lvTest1.setAdapter(adapter);

		}

		return rootView;
	}

	private void processNews(JSONArray blogList) {

		ArrayList<Blogs> item = new ArrayList<Blogs>();

		try {
			// JSONArray jsonArray = new JSONArray(newsList);
			JSONObject blogs = (JSONObject) blogList.get(0);


			int newsCount = blogList.length();
			for (int i = 0; i < newsCount; i++) {
				blogs = (JSONObject) blogList.get(i);
				Gson gson = new Gson();
				item.add(gson.fromJson(blogs.toString(), Blogs.class));
			}
			
			RecipeBlogCustomAdapter adapter = new RecipeBlogCustomAdapter(getActivity(), item);
			lvTest.setAdapter(adapter);
			lvTest1.setAdapter(adapter);
			

		} catch (JSONException ie) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

}
