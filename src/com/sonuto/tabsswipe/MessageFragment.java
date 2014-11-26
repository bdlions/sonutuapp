package com.sonuto.tabsswipe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.bdlions.load.image.ImageLoader;
import com.sonuto.Config;
import com.sonuto.message.MessageChat;
import com.sonuto.rpc.Followers;
import com.sonuto.rpc.ICallBack;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import com.sportzweb.UserProfileActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;;

public class MessageFragment extends Fragment {

	private ListView listViewFriends;
	private View rootView;
	//private int selectedUserId = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_friends, container, false);
		
		listViewFriends = (ListView)rootView.findViewById(R.id.listViewFriends);
		new Followers().show(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object object) {
				JSONObject followersObject = (JSONObject) object;
				JSONArray followers;
				try {
					followers = followersObject.getJSONArray("followers");
					ArrayAdapterItem teamAdapter = new ArrayAdapterItem(getActivity(), R.layout.frag_friend_list_item, followers);
					listViewFriends.setAdapter(teamAdapter);
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
		}, SessionManager.getInstance().getUserId());

		
		
		
		return rootView;
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

	    	int selectedUserId =0;
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
					
					if(follower.getInt("online_status") == 1){
						ImageView imageViewOnlineStatus = (ImageView) convertView.findViewById(R.id.imageViewOnlineStatus);
						imageViewOnlineStatus.setImageResource(R.drawable.online);
					}
					
					ImageView imageViewProfilePic = (ImageView) convertView.findViewById(R.id.imageViewProfilePic);
					ImageLoader imageLoader = new ImageLoader(mContext);
					String imagePath = Config.SERVER_ROOT_URL + "resources/uploads/profile_picture/50x50/";
					imageLoader.DisplayImage(imagePath + follower.getString("photo"), imageViewProfilePic);
					
					selectedUserId = follower.getInt("user_id");
				}
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        final Intent intent = new Intent(getContext(), MessageChat.class);
			Bundle bundle = new Bundle();
			bundle.putInt("receiverId", selectedUserId);
			intent.putExtras(bundle);
	        convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Toast.makeText(mContext, "add the message activity like xtreambanter", Toast.LENGTH_SHORT).show();
			        startActivity(intent);
				}
			});
	        
	        
	        
	        return convertView;

	    }

	}


}
