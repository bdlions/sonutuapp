package com.sonuto.accountsettings;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
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

public class AccountInfoSettingsActivity extends Activity {
	
	AlertDialogManager alert = new AlertDialogManager();
	Button btnEditProfile;
	public ImageLoader imageLoader;
	private Context mContext;
	ISessionManager session;
	ProgressDialog pDialog;
	int userId;
	ListView lvAccountInfoSettings;
	JSONObject UserObj;
	String[] values = new String[]{"Name","Email","Password", "Language"};
	ArrayAdapter<String> adapter;
	String general;
	JSONObject userInfoJSONObj;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_info_settings);
		this.mContext = this;
		this.session = new SessionManager(getApplicationContext());
		this.userId = getIntent().getIntExtra("user_id",0);
		
		if(userId == 0){
			this.userId = this.session.getUserId();
		}

		initUI();
	}
	
	private void initUI() {
		Intent intent = getIntent();
		general = intent.getStringExtra("profile_info");
        System.out.print(general);
        Log.d("My App", general);
        try {
        	userInfoJSONObj = new JSONObject(general);
			System.out.print(userInfoJSONObj);
			if ((userInfoJSONObj.getString("last_name") != null) | (userInfoJSONObj.getString("last_name").length() != 0)) {
				values[0] = values[0] + "(" + userInfoJSONObj.getString("first_name") + " " + userInfoJSONObj.getString("last_name") + ")";
			}
			if ((userInfoJSONObj.getString("email") != null) | (userInfoJSONObj.getString("email").length() != 0)) {
				values[1] = values[1] + "(" + userInfoJSONObj.getString("email") + ")";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.lvAccountInfoSettings = (ListView) findViewById(R.id.lvAccountInfoSettings);
		adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, values);
		this.lvAccountInfoSettings.setAdapter(adapter);
 		System.out.print(general);
 		this.lvAccountInfoSettings.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position == 0){
					Intent i = new Intent(getApplicationContext(), NameSettingsActivity.class);
					i.putExtra("user_id", session.getUserId());
					i.putExtra("profile_info", userInfoJSONObj.toString());
					startActivity(i);
				} else if(position == 1){
					Intent i = new Intent(getApplicationContext(), EmailSettingsActivity.class);
					i.putExtra("user_id", session.getUserId());
					i.putExtra("profile_info", userInfoJSONObj.toString());
					startActivity(i);
				} else {
					
				}
				
			}
		});
	}
    
}
