package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.users.Gender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GenderSelectionActivity extends Activity {
	private Context mContext;
	ImageButton imgButtonMale, imgButtonFemale;
	private LinearLayout maleBorder, femaleBorder;
	Gender genderValue;
	private int initialBackColor = 0xffffffff;
	private int selectedColor;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	ISessionManager session;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gender_selection);
		// Session Manager
		session = new SessionManager(getApplicationContext());
		
		selectedColor = 0xFFB5B5B5;
		
		mContext = this;
		imgButtonMale = (ImageButton) findViewById(R.id.image_male_button);
		imgButtonFemale = (ImageButton) findViewById(R.id.image_female_button);
		
		maleBorder = (LinearLayout) findViewById(R.id.maleBorder);
		femaleBorder = (LinearLayout) findViewById(R.id.femaleBorder);
		
		imgButtonMale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				genderValue = Gender.MALE;
				GenderSelectionActivity.this.maleBorder.setBackgroundColor(selectedColor);
				GenderSelectionActivity.this.femaleBorder.setBackgroundColor(initialBackColor);
			}
		});
		
		imgButtonFemale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				genderValue = Gender.FEMALE;
				GenderSelectionActivity.this.maleBorder.setBackgroundColor(initialBackColor);
				GenderSelectionActivity.this.femaleBorder.setBackgroundColor(selectedColor);
				
			}
		});
		
		
	}
	

	/*
	 * Save and continue click action for profileInformation activity
	 */
	public void saveANDcontinue(View view) {
		if(genderValue==Gender.FEMALE || genderValue==Gender.MALE){
			//Toast.makeText(mContext, genderValue.toString(), Toast.LENGTH_SHORT).show();
			User user = new User();
			
			try {
				JSONObject jsonUser = new JSONObject();
				
				
				jsonUser.put("user_id", session.getUserInfo().getUserId());
				jsonUser.put("gender_id", genderValue.getValue());
				
				
				user.updateGenderUser(new ICallBack() {
					@Override
					public void callBackResultHandler(final Object object) {
						JSONObject jsonObject = (JSONObject)object;
						try {
							
							if(jsonObject.get("status") != null && jsonObject.get("status").toString().equalsIgnoreCase("1")){
								 Intent intent = new Intent(mContext, ProfileInformationActivity.class);
									startActivity(intent);
									finish();
								Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
								
							} else {
								// Login unsuccessful
								alert.showAlertDialog(GenderSelectionActivity.this, "User update failed..",
										"Update unsuccessfull", false);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}   
						
					}

					@Override
					public void callBackErrorHandler(Object object) {
						// TODO Auto-generated method stub
						System.out.println(object);
					}
				}, jsonUser.toString());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else{
			Toast.makeText(mContext, "Please select a gender", Toast.LENGTH_SHORT).show();
		}
		
	}

}
