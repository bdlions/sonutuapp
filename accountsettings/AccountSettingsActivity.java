package com.sonuto.accountsettings;

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

public class AccountSettingsActivity extends Activity {
	
	AlertDialogManager alert = new AlertDialogManager();
	Button btnEditProfile;
	public ImageLoader imageLoader;
	private Context mContext;
	ISessionManager session;
	ProgressDialog pDialog;
	int userId;
	String[] values = new String[]{"General Information", "Account information", "Interests"};
	ListView lvAccountSettings;
	JSONObject userObj,userGeneralInfoObj,userAccountInfoObj;
	//ArrayList<String> listdata = new ArrayList<String>();
	//List<String> listdata = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_settings);
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
					 
					 /*if(Integer.parseInt(userObj.get("gender_id").toString()) == 1) {
						 listdata.add("Male");
						} else {
							listdata.add("Female");
						}
					 	listdata.add(userObj.get("dob").toString());
					 	listdata.add(userObj.get("home_town").toString());
					 	listdata.add(userObj.get("employer").toString());
					 	listdata.add(userObj.get("occupation").toString());
					 	listdata.add(userObj.get("about_me").toString());
					 	listdata.add(userObj.get("clg_or_uni").toString());
					 	
					 	listdata.add(userObj.get("country_id").toString());*/
					 
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void callBackErrorHandler(Object object) {
				
			}
		}, userId);
		
		this.lvAccountSettings = (ListView) findViewById(R.id.lvAccountSettings);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, values);
		lvAccountSettings.setAdapter(adapter);
		
		
		lvAccountSettings.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position == 0){
					Intent i = new Intent(getApplicationContext(), GeneralSettingsActivity.class);
					i.putExtra("user_id", userId);
					i.putExtra("profile_info", userObj.toString());
					//i.putExtra("general_info", listdata.toString());
					startActivity(i);
				} else if(position == 1){
					Intent i = new Intent(getApplicationContext(), AccountInfoSettingsActivity.class);
					i.putExtra("user_id", userId);
					i.putExtra("profile_info", userObj.toString());
					startActivity(i);
				} else {
					
				}
			}
		});
		
		
	}
    
}
