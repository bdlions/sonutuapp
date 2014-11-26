package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.android.gms.internal.bt;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

public class AccountingSettingsInfoActivity extends Activity {
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	Button btnEditProfile;
	public ImageLoader imageLoader;
	private Context mContext;
	ProgressDialog pDialog;
	int userId;
	TextView userNametextView, UserEmailtextView, UserPasswordtextView,userLangtextView,
	editUserLanguage,editUserPass,editUserEmail,editUserName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_settings_info);
		this.mContext = this;
		// Session Manager
		this.userId = getIntent().getIntExtra("user_id",0);
		
		if(userId == 0){
			this.userId = SessionManager.getInstance().getUserId();
		}

		initUI();
		process();
	}
	
	private void initUI() {
		this.userNametextView = (TextView) findViewById(R.id.userNametextView);
		this.UserEmailtextView = (TextView) findViewById(R.id.UserEmailtextView);
		this.UserPasswordtextView = (TextView) findViewById(R.id.UserPasswordtextView);
		this.userLangtextView = (TextView) findViewById(R.id.userLangtextView);
		
		this.editUserName = (TextView) findViewById(R.id.editUserName);
		this.editUserPass = (TextView) findViewById(R.id.editUserPass);
		this.editUserEmail = (TextView) findViewById(R.id.editUserEmail);
		this.editUserLanguage = (TextView) findViewById(R.id.editUserLanguage);
	}
	
	private void process() {
		final User user = new User();

		user.userProfile(new ICallBack() {

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub

			}

			@Override
			public void callBackResultHandler(Object object) {
				final JSONObject jsonUserObj = (JSONObject) object;
				try {
					final JSONObject UserObj = jsonUserObj.getJSONObject("user_info");

					if ((UserObj.getString("last_name") != null) | (UserObj.getString("last_name").length() != 0)) {
						AccountingSettingsInfoActivity.this.userNametextView.setText(UserObj.getString("first_name") + " " + UserObj.getString("last_name"));
					}
					else {
						AccountingSettingsInfoActivity.this.userNametextView.setText(UserObj.getString("first_name"));
					}
					
					if ((UserObj.getString("email") != null) ) {
						AccountingSettingsInfoActivity.this.UserEmailtextView.setText(UserObj.getString("email"));
					}
					else {
						AccountingSettingsInfoActivity.this.UserEmailtextView.setText("");
					}
					
					

				}
				catch (final JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, this.userId);
	}
    
}
