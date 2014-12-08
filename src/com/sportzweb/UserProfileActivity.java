package com.sportzweb;

import java.util.ArrayList;
import java.util.zip.Inflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.components.EndlessScroller;
import com.bdlions.helper.ListViewHelper;
import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.MemberProfile;
import com.sonuto.rpc.StatusFeed;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.tabsswipe.StatusItemAdapter;
import com.sonuto.utils.IActivityResultFromAdapter;
import com.sonutu.constants.STATUS_CATEGORY;
import com.sonutu.constants.STATUS_TYPE;
import com.sportzweb.JSONObjectModel.StatusInfo;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserProfileActivity extends Activity implements IActivityResultFromAdapter{
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	Button btnEditProfile;
	public ImageLoader imageLoader;
	private Context mContext;
	int userId;
	ImageView userProfileImage, myFollowingsImageView, myPhotosImageView,
			myInfoImageView;
	TextView userProfileName, userAddress, userProfession;

	private ListView listViewStatusItems;
	private StatusItemAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_user_profile);
		this.mContext = this;
		// Session Manager
		userId = getIntent().getIntExtra("user_id",0);
		
		if(userId == 0){
			userId = SessionManager.getInstance().getUserId();
		}

		initUI();
		process();
		myPhotosImageView.setOnClickListener(new OnClickListener() {		    
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(), MyPhotos.class);
				startActivity(intent);
				
			}
		 });
		
		ArrayList<StatusInfo> statusInfoList = new ArrayList<StatusInfo>();
		listViewStatusItems = (ListView) this.findViewById(R.id.listViewMemberProfileStatusItems);
		adapter = new StatusItemAdapter(this, this, statusInfoList);
		//adapter.setResultCallback(this);
	    listViewStatusItems.setAdapter(adapter);
	    updateNewsFeedList(1);

		listViewStatusItems.setOnScrollListener(new EndlessScroller() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				updateNewsFeedList(page);
			}
		});
	}
	
	private void updateNewsFeedList(int page){
		JSONObject params = new JSONObject();
		try {
			userId = SessionManager.getInstance().getUserId();
			params.put("user_id", userId);
			params.put("status_list_id", STATUS_CATEGORY.USER_PROFILE.getValue());
			params.put("mapping_id", userId);
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
					if(statusCount > 0)
					{
						adapter.addAll(statusInfoList);
						adapter.notifyDataSetChanged();
					}					
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
	
	private void initUI() {
		this.imageLoader = new ImageLoader(this.mContext);

		this.userProfileImage = (ImageView) findViewById(R.id.userProfileImage);
		this.myInfoImageView = (ImageView) findViewById(R.id.myInfoImageView);
		this.myPhotosImageView = (ImageView) findViewById(R.id.myPhotosImageView);
		this.myFollowingsImageView = (ImageView) findViewById(R.id.myFollowingsImageView);
		this.btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
		this.userProfileName = (TextView) findViewById(R.id.userProfileName);
		this.userAddress = (TextView) findViewById(R.id.userAddress);
		this.userProfession = (TextView) findViewById(R.id.userProfession);
		
		btnEditProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
			}
		});
		
		myInfoImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, UserInfoActivity.class);
				i.putExtra("user_id", userId);
				startActivity(i);
			}
		});

	}

	private void process() {
		MemberProfile memberProfile = new MemberProfile();
		memberProfile.getMemberProfileInfo(new ICallBack() {
			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub

			}
			@Override
			public void callBackResultHandler(Object object) {
				JSONObject jsonObject = (JSONObject) object;
				try {
					JSONObject userInfo = jsonObject.getJSONObject("member_profile_info");
					userProfileName.setText(userInfo.getString("first_name") + " " + userInfo.getString("last_name"));
					userAddress.setText(userInfo.getString("country_name")+','+userInfo.getString("home_town"));
					userProfession.setText(userInfo.getString("occupation"));
					
					final String imagePath = Config.PROFILE_PIC_DIR_LG;
					userProfileImage.setImageResource(R.drawable.upload_img_icon);
					if ((userInfo.getString("photo") != null) | (userInfo.getString("photo").length() != 0)) {
						imageLoader.DisplayImage(imagePath + userInfo.getString("photo"), userProfileImage);
					}
				}
				catch (final JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, this.userId);
	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_search){
			Intent searchIntent = new Intent(this, ActivitySearch.class);
			startActivity(searchIntent);
		}
		else if(item.getItemId() == R.id.action_post_status){
			Intent postStatusIntent = new Intent(this, PostStatusActivity.class);
			
			Bundle params = new Bundle();

			params.putInt("status_type_id", STATUS_TYPE.GENERAL.getValue());
			params.putInt("status_category_id", STATUS_CATEGORY.USER_PROFILE.getValue());
			params.putInt("mapping_id", SessionManager.getInstance().getUserId());
			

			postStatusIntent.putExtras(params);
			
			startActivityForResult(postStatusIntent, 1);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if( (requestCode == 1 || requestCode == 2) && RESULT_OK == resultCode){
			Gson gson = new Gson();
			
			StatusInfo statusInfo = gson.fromJson(data.getStringExtra("statusInfo"), StatusInfo.class);
			
			adapter.addItemAt(0,statusInfo);
			adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void startActivityResultFromAdapter(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		startActivityForResult(intent, requestCode);
	}

}
