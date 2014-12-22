package com.sportzweb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.helper.JSONFileReader;
import com.bdlions.load.image.ImageLoader;
import com.sonuto.Config;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ActivityLikedSharedUsers extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);

		setContentView(R.layout.activity_liked_shared_users);
		ListView listViewLikedUser = (ListView) findViewById(R.id.listViewLikedUser);
		TextView textViewBack = (TextView) findViewById(R.id.textViewBack);
		textViewBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		String jsonContent = JSONFileReader.getContent(getApplicationContext(), R.raw.like_json);
		try {
			JSONObject jsonObject = new JSONObject(jsonContent);
			JSONArray newsfeeds = jsonObject.getJSONArray("newsfeeds");
			JSONObject newsFeed = (JSONObject)newsfeeds.get(0);
			JSONArray likedUsers = newsFeed.getJSONArray("liked_user_list");

			ArrayAdapterItem teamAdapter = new ArrayAdapterItem(this, R.layout.adapter_liked_shared_users, likedUsers);
			listViewLikedUser.setAdapter(teamAdapter);

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	class ArrayAdapterItem extends ArrayAdapter<JSONObject> {

	    Context mContext;
	    int layoutResourceId;
	    JSONArray data;

	    public ArrayAdapterItem(Context mContext, int layoutResourceId, JSONArray data) {

	        super(mContext, layoutResourceId);

	        this.layoutResourceId = layoutResourceId;
	        this.mContext = mContext;
	        this.data = data;
	    }

	    @Override
	    public int getCount() {
	    	// TODO Auto-generated method stub
	    	return data.length();
	    }
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {

	    	
	        if(convertView==null){
	            // inflate the layout
	            LayoutInflater inflater = LayoutInflater.from(mContext);
	            convertView = inflater.inflate(layoutResourceId, parent, false);
	        }
	        try {
				JSONObject follower = (JSONObject)data.get(position);
				if(follower != null){
					TextView textViewFriendName = (TextView)convertView.findViewById(R.id.textViewFriendName);
					textViewFriendName.setText(follower.getString("first_name") + " " + follower.getString("last_name"));
					
					ImageView imageViewProfilePic = (ImageView) convertView.findViewById(R.id.imageViewProfilePic);
					ImageLoader imageLoader = new ImageLoader(mContext);
					String imagePath = Config.PROFILE_PIC_DIR_LG;
					imageLoader.DisplayImage(imagePath + follower.getString("photo"), imageViewProfilePic);
					
				}
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return convertView;

	    }

	}

}
