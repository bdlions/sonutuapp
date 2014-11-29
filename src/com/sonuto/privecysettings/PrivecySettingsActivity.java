package com.sonuto.privecysettings;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import android.widget.AdapterView.OnItemClickListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class PrivecySettingsActivity extends Activity {
	public ImageLoader imageLoader;
	private Context mContext;
	ISessionManager session;
	ProgressDialog pDialog;
	int userId;
	String[] values = new String[]{"Who can see my posts?", "Who can comment on my posts?", "Who can post on my profile?",
			"Who can contact me?","Who can tag me in posts?","Who can search for me?"};
	ListView lvPrivecySettings;
	JSONObject userObj,userGeneralInfoObj,userAccountInfoObj;

	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_privecy_settings);
		this.mContext = this;
		//this.session = new SessionManager(getApplicationContext());
		this.userId = getIntent().getIntExtra("user_id",0);
		
		if(userId == 0){
			this.userId = SessionManager.getInstance().getUserId();
		}

		initUI();
	}
	
	private void initUI() {
		pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Fetching data..");
		pDialog.setCancelable(false);
		pDialog.show();
		final User user = new User();
		user.getUserInfo(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object object) {
				
				final JSONObject jsonUserObj = (JSONObject) object;
				pDialog.dismiss();
				try {
					 userObj = jsonUserObj.getJSONObject("profile_info");
					 
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void callBackErrorHandler(Object object) {
				
			}
		}, userId);
		
		this.lvPrivecySettings = (ListView) findViewById(R.id.lvPrivecySettings);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, values);
		lvPrivecySettings.setAdapter(adapter);
		
		
		lvPrivecySettings.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position == 0){
					Intent i = new Intent(getApplicationContext(), SeePostPrivecyActivity.class);
					i.putExtra("user_id", userId);
					startActivity(i);
				} else if(position == 1){
//					Intent i = new Intent(getApplicationContext(), AccountInfoSettingsActivity.class);
//					i.putExtra("user_id", userId);
//					i.putExtra("profile_info", userObj.toString());
//					startActivity(i);
				} else {
					
				}
			}
		});
		
		
	}
    
}
