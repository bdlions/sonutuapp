package com.sonuto.tabsswipe;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.StatusFeed;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Blogs;
import com.sportzweb.JSONObjectModel.StatusInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class ActivityNewsFeed extends Fragment {
	private int userId;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
		JSONObject params = new JSONObject();
		try {
			userId = SessionManager.getInstance().getUserId();
			params.put("user_id", userId);
			params.put("status_list_id", 1);
			params.put("mapping_id", 0);
			params.put("limit", 5);
			params.put("offset", 0);
			params.put("hashtag", "");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (NullPointerException nullEx) {
			nullEx.printStackTrace();
		}
		
		new StatusFeed().get_statuses(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object result) {
				// TODO Auto-generated method stub
				JSONObject statusFeed = (JSONObject) result;
				if(statusFeed == null){
					return;
				}
				final ArrayList<StatusInfo> statusInfoList = new ArrayList<StatusInfo>();
				try {
					JSONArray newsFeeds = statusFeed.getJSONArray("newsfeeds");
					
					int statusCount = newsFeeds.length();
					for (int i = 0; i < statusCount; i++) {
						JSONObject status = (JSONObject) newsFeeds.get(i);
						Gson gson = new Gson();
						statusInfoList.add(gson.fromJson(status.toString(), StatusInfo.class));
					}
					
					final ListView listViewStatusItems = (ListView) rootView.findViewById(R.id.listViewStatusItems);
					listViewStatusItems.setOnScrollListener(new ScrollableLoadingList(getActivity().getApplicationContext(), listViewStatusItems, statusInfoList));
				    
				    StatusItemAdapter adapter = new StatusItemAdapter(getActivity().getApplicationContext(), statusInfoList);
				    listViewStatusItems.setAdapter(adapter);
				    
				}
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				System.out.println();
			}
		},params.toString());
		
		

		return rootView;
	}
}
