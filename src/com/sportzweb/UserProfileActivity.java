package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.users.Country;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends Activity {
	private Context mContext;
	ISessionManager session;
	public ImageLoader imageLoader;
	int userId;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	ImageView userProfileImage,myFollowingsImageView,myPhotosImageView,myInfoImageView;
	TextView userProfileName,userAddress,userProfession;
	Button btnEditProfile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		mContext = this;
		// Session Manager
		session = new SessionManager(getApplicationContext());
		userId = session.getUserId();
		
		initUI();
		process();
	}
	
	


	private void initUI() {
		imageLoader = new ImageLoader(mContext);
		
		userProfileImage = (ImageView) findViewById(R.id.userProfileImage);
		myInfoImageView = (ImageView) findViewById(R.id.myInfoImageView);
		myPhotosImageView = (ImageView) findViewById(R.id.myPhotosImageView);
		myFollowingsImageView = (ImageView) findViewById(R.id.myFollowingsImageView);
		btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
		
		userProfileName = (TextView) findViewById(R.id.userProfileName);
		userAddress = (TextView) findViewById(R.id.userAddress);
		userProfession = (TextView) findViewById(R.id.userProfession);
		

		
	}




	private void process() {
		User user = new User();
		
		user.userProfile(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object object) {
				JSONObject jsonUserObj = (JSONObject) object;
				try {
					//JSONArray user_info_array = jsonUserObj.getJSONArray("user_info");
					//Gson gson = new Gson();
					//System.out.print(user_info_array);
					JSONObject UserObj = jsonUserObj.getJSONObject("user_info");

					if(UserObj.getString("last_name") != null | UserObj.getString("last_name").length() != 0) {
						userProfileName.setText(UserObj.getString("first_name") +" "+ UserObj.getString("last_name"));
					} else {
						userProfileName.setText(UserObj.getString("first_name") );
					}
					
					if(UserObj.getString("address") != null | UserObj.getString("address").length() != 0) {
						userAddress.setText(UserObj.getString("address") );
					} else {
						userAddress.setText(" ");
					}
					
					if(UserObj.getString("occupation") != null | UserObj.getString("occupation").length() != 0) {
						userProfession.setText(UserObj.getString("occupation"));
					} else {
						userProfession.setText(" ");
					}
					
					String imagePath = Config.SERVER_ROOT_URL + "resources/uploads/profile_picture/";

					userProfileImage.setImageResource(R.drawable.upload_img_icon);
					if(UserObj.getString("photo") !=null | UserObj.getString("photo").length() != 0) {
						imageLoader.DisplayImage(imagePath + UserObj.getString("photo"),userProfileImage);
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
		}, userId);
	}

}
