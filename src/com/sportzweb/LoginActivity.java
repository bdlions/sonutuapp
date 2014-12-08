package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;


import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sampanit.sonutoapp.utils.AlertMessage;
import com.sampanit.sonutoapp.utils.WebUtil;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
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
	AlertDialogManager alert;
	// Session Manager Class
	String email, password;
	
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		alert = new AlertDialogManager();

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
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Processing login..");
			pDialog.setCancelable(false);
			pDialog.show();
			//Toast.makeText(mContext, "Yes", Toast.LENGTH_SHORT).show();
			User user = new User();
			user.loginUser(new ICallBack() {
				
				@Override
				public void callBackResultHandler(final Object object) {
					
					JSONObject jsonObject = (JSONObject)object;
					try {
						
						if(jsonObject.get("msg").toString().equalsIgnoreCase("SIGNIN_SUCCESSFULLY")){

							if(SessionManager.getInstance().logInUser(jsonObject.getJSONObject("user_info"))){
								if(jsonObject.get("is_bp_exists").toString().equalsIgnoreCase("1")){
									SessionManager.getInstance().isBusinessProfileExist(true);
									SessionManager.getInstance().logInUserBusinessProfile(jsonObject.getJSONObject("business_profile_info"));
								} else {
									SessionManager.getInstance().isBusinessProfileExist(false);
									//SessionManager.getInstance().logInUserBusinessProfile(jsonObject.getJSONObject("business_profile_info"));

								}
								
								Intent i = new Intent(getApplicationContext(),ApplicationPane.class);
								startActivity(i);								
								finish();
								
							} else {
								// Login unsuccessful
								//alert.showAlertDialog(mContext, "Login failed..","Username/Password is incorrect", false);
								AlertMessage.showMessage(mContext, "Login failed...","Username/Password is incorrect");
							}
							

						}else{
							 // Login unsuccessful
							//alert.showAlertDialog(mContext, "Login failed..","Username/Password is incorrect", false);
							AlertMessage.showMessage(mContext, "Login failed...","Username/Password is incorrect");
						}
						pDialog.dismiss();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				
					
				}
				
				@Override
				public void callBackErrorHandler(Object object) {
					pDialog.dismiss();
					// email / password doesn't match
					//alert.showAlertDialog(mContext, "Login failed..","Username/Password is incorrect", false);
					AlertMessage.showMessage(mContext, "Login failed...","Username/Password is incorrect");
					
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
		Intent intent = new Intent(mContext, RegistrationActivity.class);
		startActivity(intent);
		finish();
	}
	
	/*
	 * forget password click action
	 */
	public void forgetPassword(View view) {
		Intent intent = new Intent(mContext, ForgetPasswordActivity.class);
		startActivity(intent);
		finish();
	}
}
