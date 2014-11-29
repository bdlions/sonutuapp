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
import com.sonuto.users.Gender;
import com.sportzweb.GenderSelectionActivity;
import com.sportzweb.ProfileInformationActivity;
import com.sportzweb.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class GenderSettingsActivity extends Activity {
	
	AlertDialogManager alert = new AlertDialogManager();
	Button btnChangeGender;
	public ImageLoader imageLoader;
	private Context mContext;
	ISessionManager session;
	int userId,genderId;
	Gender genderValue;
	private int initialBackColor = 0xffffffff;
	private int selectedColor;
	ImageButton imgButtonMale, imgButtonFemale;
	private LinearLayout maleBorder, femaleBorder;
	String general;
	JSONObject userInfoJSONObj;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gender_setteing);
		this.mContext = this;
		
		Intent intent = getIntent();
		Bundle b = getIntent().getExtras();
		this.userId = getIntent().getIntExtra("user_id",0);
		this.genderId = intent.getIntExtra("gender_id", 0);
		
		if(userId == 0){
			this.userId = SessionManager.getInstance().getUserId();
		}
		selectedColor = 0xFFB5B5B5;

		initUI();
	}
	
	private void initUI() {
		Intent intent = getIntent();
		general = intent.getStringExtra("profile_info");
        try {
        	userInfoJSONObj = new JSONObject(general);
			System.out.print(userInfoJSONObj);
			if(Integer.parseInt(userInfoJSONObj.get("gender_id").toString()) == 1) {
				genderId = 1;
			} else {
				genderId = 2;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		btnChangeGender = (Button) findViewById(R.id.btnChangeGender);
		imgButtonMale = (ImageButton) findViewById(R.id.imageview_male_button);
		imgButtonFemale = (ImageButton) findViewById(R.id.imageview_female_button);
		
		maleBorder = (LinearLayout) findViewById(R.id.maleBorder);
		femaleBorder = (LinearLayout) findViewById(R.id.femaleBorder);
		
		if(genderId == genderValue.MALE.getValue()) {
			GenderSettingsActivity.this.maleBorder.setBackgroundColor(selectedColor);
		} else {
			GenderSettingsActivity.this.femaleBorder.setBackgroundColor(selectedColor);
		}
		
		imgButtonMale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				genderValue = Gender.MALE;
				GenderSettingsActivity.this.maleBorder.setBackgroundColor(selectedColor);
				GenderSettingsActivity.this.femaleBorder.setBackgroundColor(initialBackColor);
			}
		});
		
		imgButtonFemale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				genderValue = Gender.FEMALE;
				GenderSettingsActivity.this.maleBorder.setBackgroundColor(initialBackColor);
				GenderSettingsActivity.this.femaleBorder.setBackgroundColor(selectedColor);
				
			}
		});
		
		btnChangeGender.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(genderValue==Gender.FEMALE || genderValue==Gender.MALE){
					// rpc call
					try {
						JSONObject jsonUser = new JSONObject();
						
						jsonUser.put("user_id", userId);
						jsonUser.put("gender_id", genderValue.getValue());
						
						
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				} else{
					Toast.makeText(mContext, "Please select a gender", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}
    
}
