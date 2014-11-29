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

public class ForgetPasswordEmailActivity extends Activity {
	private Context mContext;
	Button btnforgetPass;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password_confirmation_code);
		mContext = this;
		initUI();
	}

	private void initUI() {
//		btnforgetPass = (Button) findViewById(R.id.btnforgetPass);
//		btnforgetPass.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(getApplicationContext(), SeePostPrivecyActivity.class);
//				startActivity(i);
//			}
//		});
	}

}
