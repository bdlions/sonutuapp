package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sampanit.sonutoapp.utils.WebUtil;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	TextView textview_app_name;
	private Context mContext;
	private EditText mEmail, mPassword;
	TextView modelTextview;
	// login button
	Button btnLogin;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	// Session Manager Class
	ISessionManager session;
	String email, password;
	
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;

		// Session Manager
		session = new SessionManager(getApplicationContext());
		if (session.isLoggedIn()) {
			// Staring MainActivity
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
			finish();
		}

		// Email, Password input text
		mEmail = (EditText) findViewById(R.id.txtInpEmail);
		mPassword = (EditText) findViewById(R.id.txtInpPassword);
		modelTextview = (TextView) findViewById(R.id.link_to_register);

		// Login button
		btnLogin = (Button) findViewById(R.id.btnLogin);

		// Login button click event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				doLogin();
			}
		});

	}

	/*
	 * Request Server for login
	 */
	private void doLogin() {
		// Get email, password from EditText
		email = mEmail.getText().toString();
		password = mPassword.getText().toString();
		if (isVerified()) {
			
			User user = new User();
			user.loginUser(new ICallBack() {
				
				@Override
				public void callBackResultHandler(final Object object) {
					JSONObject jsonObject = (JSONObject)object;
					try {
						
						if(jsonObject.get("msg").toString().equalsIgnoreCase("SIGNIN_SUCCESSFULLY")){
							//Toast.makeText(getApplicationContext(), jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
							alert.showAlertDialog(LoginActivity.this, "Logedin ..",
									"SIGNIN_SUCCESSFULLY", true);
							if(session.logInUser(jsonObject)){
								Intent i = new Intent(getApplicationContext(),MainActivity.class);
								startActivity(i);
								finish();
							} else {
								// Login unsuccessful
								alert.showAlertDialog(LoginActivity.this, "Login failed..",
										"Username/Password is incorrect", false);
							}

						}else{
							 // Login unsuccessful
							alert.showAlertDialog(LoginActivity.this, "Login failed..",
									"Username/Password is incorrect", false);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					
				}
				
				@Override
				public void callBackErrorHandler(Object object) {
					//System.out.println(object);
					// email / password doesn't match
					alert.showAlertDialog(LoginActivity.this, "Login failed..",
							"Username/Password is incorrect", false);
					
				}
			}, email.toString(),password.toString());
		}
	}

	/*
	 * Verification Login Screen Data
	 */
	private boolean isVerified() {
		if (mEmail.getText().toString().trim().length() == 0) {
			Toast.makeText(mContext, getString(R.string.emailRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (!WebUtil.isValidEmailAddress(mEmail.getText().toString()
				.trim())) {
			Toast.makeText(mContext, getString(R.string.validEmail),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (mPassword.getText().toString().trim().length() == 0) {
			Toast.makeText(mContext, getString(R.string.passwordRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}

	}

	/*
	 * Create new account click action
	 */
	public void newAccount(View view) {
		LoginActivity.this.finish();

		Intent intent = new Intent(mContext, RegistrationActivity.class);
		startActivity(intent);
	}
}
