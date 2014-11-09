package com.sonuto.tabsswipe;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.StatusFeed;
import com.sportzweb.JSONObjectModel.StatusInfo;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
;

public class ScrollableLoadingList implements OnScrollListener {

	private Context rootContext;
	private ListView listViewStatusItems;
	private ArrayList<StatusInfo> statusInfoList;
	private int visibleThreshold = 5;
	private int currentPage = 0;
	private int previousTotal = 0;
	private boolean loading = true;

	public ScrollableLoadingList(Context rootContext, ListView listViewStatusItems, ArrayList<StatusInfo> statusInfoList) {
		this.rootContext = rootContext;
		this.listViewStatusItems = listViewStatusItems;
		this.statusInfoList = statusInfoList;
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (loading) {
			if (totalItemCount > previousTotal) {
				loading = false;
				previousTotal = totalItemCount;
				currentPage++;
			}
		}
		if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			final int scrollPosition = firstVisibleItem + visibleItemCount;
			// I load the next page of gigs using a background task,
			// but you can call any function here.
			new StatusFeed().get_statuses(new ICallBack() {

				@Override
				public void callBackResultHandler(Object result) {
					// TODO Auto-generated method stub
					JSONObject statusFeed = (JSONObject) result;
					if (statusFeed == null) {
						return;
					}
					try {
						JSONArray newsFeeds = statusFeed.getJSONArray("newsfeeds");
						int statusCount = newsFeeds.length();
						for (int i = 0; i < statusCount; i++) {
							JSONObject status = (JSONObject) newsFeeds.get(i);
							Gson gson = new Gson();
							statusInfoList.add(gson.fromJson(status.toString(), StatusInfo.class));
						}

						StatusItemAdapter adapter = new StatusItemAdapter(rootContext, statusInfoList);
						listViewStatusItems.setAdapter(adapter);
						listViewStatusItems.setSelection(scrollPosition);

					}
					catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void callBackErrorHandler(Object object) {
					// TODO Auto-generated method stub

				}
			});
			loading = true;
		}
	}

}