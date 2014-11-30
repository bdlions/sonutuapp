package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.privecysettings.SeePostPrivecyActivity;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.users.Gender;
import com.sonuto.users.UserInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class ForgetPasswordEmailVarifyActivity extends Activity {
	private Context mContext;
	Button btnforgetPassContinue,btnNotMyAcc,btnBackToLogin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pass_conf_code_reset);
		mContext = this;
		initUI();
	}

	private void initUI() {
		btnforgetPassContinue = (Button) findViewById(R.id.btnforgetPassContinue);
		btnforgetPassContinue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), ChangePasswordConfirmationActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		btnNotMyAcc = (Button) findViewById(R.id.btnNotMyAcc);
		btnNotMyAcc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), ForgetPasswordEmailActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		btnBackToLogin = (Button) findViewById(R.id.btnBackToLogin);
		btnBackToLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
				finish();
			}
		});
		
	}

}
