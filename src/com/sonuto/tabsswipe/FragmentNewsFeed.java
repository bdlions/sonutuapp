package com.sonuto.tabsswipe;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.components.EndlessScroller;
import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.StatusFeed;
import com.sonuto.session.SessionManager;
import com.sportzweb.ActivityPostStatus;
import com.sportzweb.ActivitySearch;
import com.sportzweb.PostStatusActivity;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.StatusInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.view.MenuItem.OnMenuItemClickListener;;

public class FragmentNewsFeed extends Fragment {
	private int userId;
	private ListView listViewStatusItems;
	private StatusItemAdapter adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
		setHasOptionsMenu(true);
		
		ArrayList<StatusInfo> statusInfoList = new ArrayList<StatusInfo>();
		listViewStatusItems = (ListView) rootView.findViewById(R.id.listViewStatusItems);
		adapter = new StatusItemAdapter(getActivity().getApplicationContext(), statusInfoList);
	    listViewStatusItems.setAdapter(adapter);
	    
	    updateNewsFeedList(1);

		listViewStatusItems.setOnScrollListener(new EndlessScroller() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				updateNewsFeedList(page);
			}
		});
		
		
		
		return rootView;
	}
	
	private void updateNewsFeedList(int page){
		JSONObject params = new JSONObject();
		try {
			userId = SessionManager.getInstance().getUserId();
			params.put("user_id", userId);
			params.put("status_list_id", 1);
			params.put("mapping_id", 0);
			params.put("limit", 5);
			params.put("offset", (page - 1) * 5);
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
				ArrayList<StatusInfo> statusInfoList = new ArrayList<StatusInfo>();
				try {
					JSONArray newsFeeds = statusFeed.getJSONArray("newsfeeds");
					
					int statusCount = newsFeeds.length();
					for (int i = 0; i < statusCount; i++) {
						JSONObject status = (JSONObject) newsFeeds.get(i);
						Gson gson = new Gson();
						statusInfoList.add(gson.fromJson(status.toString(), StatusInfo.class));
					}
					adapter.addAll(statusInfoList);
					adapter.notifyDataSetChanged();
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
		
	}
	
	@Override 
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_main_actions, menu);
		
	    super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_search){
			Intent searchIntent = new Intent(getActivity(), ActivitySearch.class);
			startActivity(searchIntent);
		}
		else if(item.getItemId() == R.id.action_post_status){
			Intent postStatusIntent = new Intent(getActivity(), PostStatusActivity.class);
			
			Bundle params = new Bundle();
			params.putInt("status_type_id", 1);
			params.putInt("status_category_id", 1);
			postStatusIntent.putExtras(params);
			
			startActivityForResult(postStatusIntent, 1);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1 && getActivity().RESULT_OK == resultCode){
			Gson gson = new Gson();
			
			StatusInfo statusInfo = gson.fromJson(data.getStringExtra("statusInfo"), StatusInfo.class);
			
			adapter.addItemAt(0,statusInfo);
			adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
