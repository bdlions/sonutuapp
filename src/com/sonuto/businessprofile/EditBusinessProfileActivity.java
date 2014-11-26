package com.sonuto.businessprofile;



import com.bdlions.load.image.ImageLoader;
import com.sampanit.sonutoapp.utils.AlertDialogManager;

import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class EditBusinessProfileActivity extends Activity {
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	Button btnEditProfile;
	public ImageLoader imageLoader;
	private Context mContext;
	int userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_business_profile);
		this.mContext = this;
		// Session Manager
		this.userId = SessionManager.getInstance().getUserId();
		
	}
	
	
}
