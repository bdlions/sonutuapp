package com.sportzweb;


import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sampanit.sonutoapp.utils.PersistentUser;
import com.sampanit.sonutoapp.utils.UserSessionManager;
import com.sampanit.sonutoapp.utils.WebUtil;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegistrationActivity extends Activity {

    private Context mContext;
    private EditText mEmail, mFirstname, mLastname, mPassword;
	TextView modelTextview;
	Button backLogin;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resigtration);
	
		mContext = this;
        //Initialize UI method 
        //initUi();

			
			mFirstname = (EditText) findViewById(R.id.regFnameInputEdtTxt);
			mLastname = (EditText) findViewById(R.id.regLnameInputEdtTxt);
			
		
	}
	

}
