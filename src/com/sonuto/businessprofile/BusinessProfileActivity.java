package com.sonuto.businessprofile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.sonuto.Config;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import com.sportzweb.UserProfileActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class BusinessProfileActivity extends Activity {
	
	private Context mContext;
	Button btnTotalBConnection,btnBConnection,btnBInfo,btnBMore;
	ImageView BusinessProfileImageView,imageOfBusiness,bCrossView;
	TextView bNameTextView,textViewpostedBStatusTime,textViewBDescription,textViewBTotalcomments,textViewBTotalLike;
	ImageButton bLikeStatusBtn,bCommentStatusBtn,bShareStatusBtn;
	public ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_profile);
		mContext = this;
		initUi();
	}

	/*
	 * Initialize UI
	 */
	private void initUi() {
		this.imageLoader = new ImageLoader(this.mContext);
		btnTotalBConnection = (Button) findViewById(R.id.btnTotalBConnection);
		btnBConnection = (Button) findViewById(R.id.btnBConnection);
		btnBInfo = (Button) findViewById(R.id.btnBInfo);
		btnBMore = (Button) findViewById(R.id.btnBMore);
		
		bLikeStatusBtn = (ImageButton) findViewById(R.id.bLikeStatusBtn);
		bCommentStatusBtn = (ImageButton) findViewById(R.id.bCommentStatusBtn);
		bShareStatusBtn = (ImageButton) findViewById(R.id.bShareStatusBtn);
		
		bNameTextView = (TextView) findViewById(R.id.bNameTextView);
		textViewpostedBStatusTime = (TextView) findViewById(R.id.textViewpostedBStatusTime);
		textViewBDescription = (TextView) findViewById(R.id.textViewBDescription);
		textViewBTotalcomments = (TextView) findViewById(R.id.textViewBTotalcomments);
		textViewBTotalLike = (TextView) findViewById(R.id.textViewBTotalLike);
		
		BusinessProfileImageView = (ImageView ) findViewById(R.id.BusinessProfileImageView);
		imageOfBusiness = (ImageView ) findViewById(R.id.imageOfBusiness);
		bCrossView = (ImageView ) findViewById(R.id.bCrossView);
		
		Intent i = getIntent();
		String bProfile = i.getStringExtra("business_profile_info");
		try {
			JSONObject businessProfile  = new JSONObject(bProfile);
			//businessProfile.get("business_description")
			textViewBDescription.setText(businessProfile.get("business_description").toString());
			textViewBDescription.setText(businessProfile.get("business_description").toString());
			
			
			final String imagePath = Config.SERVER_ROOT_URL + "resources/uploads/business/";

			BusinessProfileActivity.this.BusinessProfileImageView.setImageResource(R.drawable.upload_img_icon);
			if ((businessProfile.getString("logo") != null) | (businessProfile.getString("logo").length() != 0)) {
				BusinessProfileActivity.this.imageLoader.DisplayImage(imagePath + businessProfile.getString("logo"), BusinessProfileActivity.this.BusinessProfileImageView);
			}
			
			BusinessProfileActivity.this.imageOfBusiness.setImageResource(R.drawable.followers);
			if ((businessProfile.getString("cover_photo") != null) | (businessProfile.getString("cover_photo").length() != 0)) {
				BusinessProfileActivity.this.imageLoader.DisplayImage(imagePath + businessProfile.getString("cover_photo"), BusinessProfileActivity.this.imageOfBusiness);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
