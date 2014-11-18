package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity {
	TextView firstNameText,lastNameText,genderText,dobText,countryText,
	homeTownText,telephoneText,emailText,skypeNameText,facebookText,twterNameText,
	linkdinText,employerText,occupationText,scuText,aboutMeText;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	Button btnEditProfile;
	public ImageLoader imageLoader;
	private Context mContext;
	ISessionManager session;
	int userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		this.mContext = this;
		// Session Manager
		this.session = new SessionManager(getApplicationContext());
		this.userId = this.session.getUserId();
		initUI();
		
		
		
		final User user = new User();
		user.getUserInfo(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object object) {
				
				final JSONObject jsonUserObj = (JSONObject) object;
				try {
					final JSONObject UserObj = jsonUserObj.getJSONObject("profile_info");
					
					//UserObj.get("");
					firstNameText.setText(UserObj.get("first_name").toString());
					lastNameText.setText(UserObj.get("last_name").toString());
					if(Integer.parseInt(UserObj.get("gender_id").toString()) == 1) {
						genderText.setText("Male");
					} else {
						genderText.setText("Female");
					}
					
					dobText.setText(UserObj.get("dob").toString());
					countryText.setText((UserObj.get("country_id").toString()));
					firstNameText.setText(UserObj.get("home_town").toString());
					
					telephoneText.setText(UserObj.get("telephone").toString());
					skypeNameText.setText(UserObj.get("skype_name").toString());
					twterNameText.setText(UserObj.get("twitter_name").toString());
					emailText.setText(UserObj.get("email").toString());
					facebookText.setText(UserObj.get("facebook_name").toString());
					linkdinText.setText(UserObj.get("linkedin_name").toString());
					
					
					employerText.setText(UserObj.get("employer").toString());
					occupationText.setText(UserObj.get("occupation").toString());
					aboutMeText.setText(UserObj.get("about_me").toString());
					scuText.setText(UserObj.get("clg_or_uni").toString());
					
					System.out.print(UserObj);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void callBackErrorHandler(Object object) {
				
				
			}
		}, userId);
		
	}

	private void initUI() {
		
		firstNameText = (TextView) findViewById(R.id.firstNameText);
		lastNameText = (TextView) findViewById(R.id.lastNameText);
		genderText = (TextView) findViewById(R.id.genderText);
		dobText = (TextView) findViewById(R.id.dobText);
		countryText = (TextView) findViewById(R.id.countryText);
		homeTownText = (TextView) findViewById(R.id.homeTownText);
		
		telephoneText = (TextView) findViewById(R.id.telephoneText);
		emailText = (TextView) findViewById(R.id.emailText);
		skypeNameText = (TextView) findViewById(R.id.skypeNameText);
		facebookText = (TextView) findViewById(R.id.facebookText);
		twterNameText = (TextView) findViewById(R.id.twterNameText);
		linkdinText = (TextView) findViewById(R.id.linkdinText);
		
		employerText = (TextView) findViewById(R.id.employerText);
		occupationText = (TextView) findViewById(R.id.occupationText);
		aboutMeText = (TextView) findViewById(R.id.aboutMeText);
		scuText = (TextView) findViewById(R.id.scuText);
	}
	
	
}
