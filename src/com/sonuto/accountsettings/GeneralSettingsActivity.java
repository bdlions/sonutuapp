package com.sonuto.accountsettings;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import com.sportzweb.UserProfileActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class GeneralSettingsActivity extends Activity {
	
	AlertDialogManager alert = new AlertDialogManager();
	Button btnEditProfile;
	public ImageLoader imageLoader;
	private Context mContext;
	ISessionManager session;
	ProgressDialog pDialog;
	int userId;
	ListView lvGeneralSettings;
	JSONObject UserObj;
	String[] values = new String[]{"Gender","Date Of Birth","Country", "Home Town", "Employer","Employer","Occupation","About Me"};
	ArrayAdapter<String> adapter;
	String general;
	JSONObject userInfoJSONObj;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_general_settings);
		this.mContext = this;
		this.userId = getIntent().getIntExtra("user_id",0);
		
		if(userId == 0){
			this.userId = SessionManager.getInstance().getUserId();
		}

		initUI();
	}
	
	private void initUI() {
		Intent intent = getIntent();
		general = intent.getStringExtra("profile_info");
        try {
        	userInfoJSONObj = new JSONObject(general);
			System.out.print(userInfoJSONObj);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//Bundle b = getIntent().getExtras();
		//System.out.print(general);
//        if(b!=null){
//            ArrayList<String> arr = (ArrayList<String>)b.getStringArrayList("general_info");
//            System.out.println(arr);
//            this.lvGeneralSettings = (ListView) findViewById(R.id.lvAccountSettings);
//    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arr);
//    		lvGeneralSettings.setAdapter(adapter);
//        }
//		lvGeneralSettings.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//				if(position == 0){
//					Intent i = new Intent(GeneralSettingsActivity.this, PrivecySettingsActivity.class);
//					i.putExtra("user_id", session.getUserId());
//					i.putExtra("profile_info", UserObj.toString());
//					startActivity(i);
//				} else if(position == 1){
//					
//				} else {
//					
//				}
//			}
//		});


		this.lvGeneralSettings = (ListView) findViewById(R.id.lvGeneralSettings);
		adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, values);
 		lvGeneralSettings.setAdapter(adapter);
 		System.out.print(general);
		lvGeneralSettings.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position == 0){
					Intent i = new Intent(getApplicationContext(), GenderSettingsActivity.class);
					i.putExtra("user_id", session.getUserId());
					i.putExtra("gender_id", 2);
					i.putExtra("profile_info", userInfoJSONObj.toString());
					startActivity(i);
				} else if(position == 1){
					
				} else {
					
				}
				
			}
		});
	}
    
}
