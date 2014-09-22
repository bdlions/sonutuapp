package com.sportzweb;



import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sampanit.sonutoapp.utils.UserSessionManager;
import com.sampanit.sonutoapp.utils.WebUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	// Animation
    Animation animFadein;
    Animation animMove;
    TextView textview_app_name;
    private Context mContext;
	private EditText mEmail, mPassword;
	TextView modelTextview;
	// login button
	Button btnLogin;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	// Session Manager Class
	UserSessionManager session;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		// Session Manager
        session = new UserSessionManager(getApplicationContext());                
        
        // Email, Password input text
        mEmail = (EditText) findViewById(R.id.txtInpEmail);
        mPassword = (EditText) findViewById(R.id.txtInpPassword); 
        modelTextview = (TextView)findViewById(R.id.link_to_register);
        
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        
        
        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);
        
        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Get username, password from EditText
				String username = mEmail.getText().toString();
				String password = mPassword.getText().toString();
				
				// Check if username, password is filled				
				if(username.trim().length() > 0 && password.trim().length() > 0){
					// For testing puspose username, password is checked with sample data
					// username = test
					// password = test
					if(username.equals("test") && password.equals("test")){
						// Creating user login session
						// For testing i am stroing name, email as follow
						// Use user real data
						session.createLoginSession("test@gmail.com", "test");
						
						// Staring MainActivity
						Intent i = new Intent(getApplicationContext(), MainActivity.class);
						startActivity(i);
						finish();
						
					}else{
						// username / password doesn't match
						alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
					}				
				}else{
					// user didn't entered username or password
					// Show alert asking him to enter the details
					alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
				}
				
			}
		});
        

		
		
		//mContext = this;
        // Initialize UI method 
        //initUi();
	}
	
	
	/*
	 *  Initialize UI
	 */
	/*private void initUi() {
		
		mEmail = (EditText) findViewById(R.id.txtInpEmail);
		mPassword = (EditText) findViewById(R.id.txtInpPassword);
		
		if(PersistentUser.isLogged(mContext)) {
			LoginActivity.this.finish();
			
			//Intent intent = new Intent(mContext, MainActivity.class);
			//startActivity(intent);
		}
		
	}*/
	
	
	/*
	 * 	Submit button click action
	 */
	public void loginSubmit(View view) {
		if(isVerified()) {
			doLogin();
		}
	}
	
	
	/*
	 * 	Request Server for login
	 */
	private void doLogin() {
		
	}
	
	
	/*
	 * 	Verification Login Screen Data
	 */
	private boolean isVerified() {
		if(mEmail.getText().toString().trim().length() == 0) {
			Toast.makeText(mContext, getString(R.string.emailRequired), Toast.LENGTH_SHORT).show();
			return false;
		} else if(!WebUtil.isValidEmailAddress(mEmail.getText().toString().trim())) {
			Toast.makeText(mContext, getString(R.string.validEmail), Toast.LENGTH_SHORT).show();
			return false;
		}else if(mPassword.getText().toString().trim().length() == 0) {
			Toast.makeText(mContext, getString(R.string.passwordRequired), Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
		
	}
	
	
	/*
	 * 	Create new account click action
	 */
	public void newAccount(View view) {
		LoginActivity.this.finish();
		
		Intent intent = new Intent(mContext, RegistrationActivity.class);
		startActivity(intent);
	}
}
