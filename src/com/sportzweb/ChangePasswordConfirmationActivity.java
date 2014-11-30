package com.sportzweb;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class ChangePasswordConfirmationActivity extends Activity {
	private Context mContext;
	Button btnforgetPass,btnBackLogin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pass);
		mContext = this;
		initUI();
	}

	private void initUI() {
		
		
	}

}
