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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class NameSettingsActivity extends Activity {
	
	AlertDialogManager alert = new AlertDialogManager();
	EditText fNameChangeEdtTxt, lNameChangeEdtTxt;
	Button btnNameChange;
	public ImageLoader imageLoader;
	private Context mContext;
	ISessionManager session;
	int userId;
	String general;
	JSONObject userInfoJSONObj;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_setteing);
		this.mContext = this;
		this.session = new SessionManager(getApplicationContext());
		Intent intent = getIntent();
		Bundle b = getIntent().getExtras();
		this.userId = getIntent().getIntExtra("user_id",0);
		general = intent.getStringExtra("profile_info");
        System.out.print(general);
        Log.d("My App", general);
        try {
        	userInfoJSONObj = new JSONObject(general);
			System.out.print(userInfoJSONObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(userId == 0){
			this.userId = this.session.getUserId();
		}
		initUI();
	}
	
	private void initUI() {
		fNameChangeEdtTxt = (EditText) findViewById(R.id.fNameChangeEdtTxt);
		lNameChangeEdtTxt = (EditText) findViewById(R.id.lNameChangeEdtTxt);
		btnNameChange = (Button) findViewById(R.id.btnNameChange);
		try {
			fNameChangeEdtTxt.setText(userInfoJSONObj.getString("first_name"));
			lNameChangeEdtTxt.setText(userInfoJSONObj.getString("last_name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		btnNameChange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
	}
    
}
