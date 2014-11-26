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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PrivecySettingsActivity extends Activity {
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	Button btnEditProfile;
	public ImageLoader imageLoader;
	private Context mContext;
	ProgressDialog pDialog;
	int userId;
	TextView userGender, userEditDOB, editUserCountry,editUserTown,
	editUserSCU,editUserEmp,editUserOccupation,editUserAboutMe;
	Button btnok;
	String hometown,clgUni,employee,occupation,aboutMe; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_settings);
		this.mContext = this;
		// Session Manager
		this.userId = getIntent().getIntExtra("user_id",0);
		
		if(userId == 0){
			this.userId = SessionManager.getInstance().getUserId();
		}

		initUI();
	}
	
	private void initUI() {
		//this.imageLoader = new ImageLoader(this.mContext);
		this.userGender = (TextView) findViewById(R.id.userGender);
		this.userEditDOB = (TextView) findViewById(R.id.userEditDOB);
		//this.editUserCountry = (TextView) findViewById(R.id.editUserCountry);
		this.editUserTown = (TextView) findViewById(R.id.editUserTown);
		this.editUserSCU = (TextView) findViewById(R.id.editUserSCU);
		this.editUserEmp = (TextView) findViewById(R.id.editUserEmp);
		this.editUserOccupation = (TextView) findViewById(R.id.editUserOccupation);
		this.editUserAboutMe = (TextView) findViewById(R.id.editUserAboutMe);
		this.btnok = (Button) findViewById(R.id.btnok);
		
		btnok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userValue();
				if(isVerified()){
					User user = new User();
					JSONObject jsonUser = new JSONObject();
					try {
						jsonUser.put("user_id", userId);
						jsonUser.put("home_town", hometown);
						jsonUser.put("clg_or_uni", clgUni);
						jsonUser.put("employer", employee);
						jsonUser.put("occupation", occupation);
						jsonUser.put("about_me", aboutMe);
						
						pDialog = new ProgressDialog(mContext);
						pDialog.setMessage("Fetching data..");
						pDialog.setCancelable(false);
						pDialog.show();
						
						user.updateGeneralUserInfo(new ICallBack() {
							
							@Override
							public void callBackResultHandler(Object object) {
								JSONObject jsonObject = (JSONObject) object;
								pDialog.dismiss();
							}
							
							@Override
							public void callBackErrorHandler(Object object) {
								
							}
						}, jsonUser.toString());
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		final User user = new User();
		user.getUserInfo(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object object) {
				
				final JSONObject jsonUserObj = (JSONObject) object;
				try {
					final JSONObject UserObj = jsonUserObj.getJSONObject("profile_info");
					
					if(Integer.parseInt(UserObj.get("gender_id").toString()) == 1) {
						userGender.setText("Male");
					} else {
						userGender.setText("Female");
					}
					
					userEditDOB.setText(UserObj.get("dob").toString());
					//editUserCountry.setText((UserObj.get("country_id").toString()));
					editUserTown.setText(UserObj.get("home_town").toString());
					
					
					
					editUserEmp.setText(UserObj.get("employer").toString());
					editUserOccupation.setText(UserObj.get("occupation").toString());
					editUserAboutMe.setText(UserObj.get("about_me").toString());
					editUserSCU.setText(UserObj.get("clg_or_uni").toString());
					
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
	
	public void userValue() {
		hometown = editUserTown.getText().toString().trim();
		clgUni = editUserSCU.getText().toString().trim();
		employee = editUserEmp.getText().toString().trim();
		occupation = editUserOccupation.getText().toString().trim();
		aboutMe = editUserAboutMe.getText().toString().trim();
	}
	
	public boolean isVerified() {
		if(hometown.length() == 0) {
			Toast.makeText(mContext, getString(R.string.hometwonRequired), Toast.LENGTH_SHORT).show();
			return false;
		}else if(clgUni.length() == 0){
			Toast.makeText(mContext, getString(R.string.institutionRequired), Toast.LENGTH_SHORT).show();
			return false;
		}else if(employee.length() == 0){
			Toast.makeText(mContext, getString(R.string.employeRequired), Toast.LENGTH_SHORT).show();
			return false;
		}else if(occupation.length() == 0){
			Toast.makeText(mContext, getString(R.string.occupationRequired), Toast.LENGTH_SHORT).show();
			return false;
		}else if(aboutMe.length() == 0){
			Toast.makeText(mContext, getString(R.string.aboutMeRequired), Toast.LENGTH_SHORT).show();
			return false;
		}else {
			return true;
		}
		
	}

}
