package com.sportzweb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.helper.JSONFileReader;
import com.bdlions.load.image.ImageLoader;
import com.sonuto.Config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ActivityCommentsDisplayer extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_comment_displayer);
		TextView textViewLikeCounter = (TextView) findViewById(R.id.textViewLikeCounter);
		ListView listViewComments = (ListView) findViewById(R.id.listComments);
		
		String jsonContent = JSONFileReader.getContent(getApplicationContext(), R.raw.like_json);
		try {
			JSONObject jsonObject = new JSONObject(jsonContent);
			JSONArray newsfeeds = jsonObject.getJSONArray("newsfeeds");
			JSONObject newsFeed = (JSONObject)newsfeeds.get(0);
			JSONArray feedbacks = newsFeed.getJSONArray("feedbacks");

			JSONArray likedUsers = newsFeed.getJSONArray("liked_user_list");
			textViewLikeCounter.setText(likedUsers.length() + " people like this status.");
			textViewLikeCounter.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent activityLikedSharedUsers = new Intent(ActivityCommentsDisplayer.this, ActivityLikedSharedUsers.class);
					activityLikedSharedUsers.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(activityLikedSharedUsers);
				}
			});

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
