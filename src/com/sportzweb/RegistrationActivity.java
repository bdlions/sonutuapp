package com.sportzweb;

import java.lang.reflect.Field;
import java.util.Properties;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams.Versions;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sampanit.sonutoapp.utils.AssetsPropertyReader;
import com.sampanit.sonutoapp.utils.UserSessionManager;
import com.sampanit.sonutoapp.utils.WebUtil;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;






import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	private Context mContext;
	private EditText mEmail, mFirstname, mLastname, mPassword;
	TextView modelTextview;
	Button backLogin, regContinue1, regContinue2, regContinue3;
	FrameLayout reg_step_1_layout, reg_step_2_layout, reg_step_3_layout;
	String fname,lname,password,email;
	
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resigtration);

		mContext = this;
		
		// Initialize UI method
		initUi();
	}
	
	/*
	 *  Initialize UI
	 */
	private void initUi() {
		
		mFirstname = (EditText) findViewById(R.id.regFnameInputEdtTxt);
		mLastname = (EditText) findViewById(R.id.regLnameInputEdtTxt);
		mEmail = (EditText) findViewById(R.id.regEmailInputEdtTxt);
		mPassword = (EditText) findViewById(R.id.regPassInputEdtTxt);
		
		reg_step_1_layout = (FrameLayout) findViewById(R.id.member_reg_box1);
		reg_step_2_layout = (FrameLayout) findViewById(R.id.member_reg_box2);
		reg_step_3_layout = (FrameLayout) findViewById(R.id.member_reg_box3);

		regContinue1 = (Button) findViewById(R.id.btnRegContinue1);
		regContinue2 = (Button) findViewById(R.id.btnRegContinue2);
		regContinue3 = (Button) findViewById(R.id.btnRegContinue3);
		
	}
	
	/*
	 * continue button click action for step 1
	 */
	public void regStep1(View view) {
		userValue();
		if(isVerifiedStep1()) {
			reg_step_2_layout.setVisibility(View.VISIBLE);
			reg_step_1_layout.setVisibility(View.GONE);
		}
		
	}
	
	/*
	 * continue button click action for step 2
	 */
	public void regStep2(View view) {
		userValue();
		if(isVerifiedStep2()) {
			reg_step_3_layout.setVisibility(View.VISIBLE);
			reg_step_2_layout.setVisibility(View.GONE);
		}
		
	}
	
	/*
	 * continue button click action for step 3
	 */
	public void regStep3(View view) {
		userValue();
		if (isVerifiedStep3()) {
			User user = new User();
			try {
					JSONObject jsonUser = new JSONObject();
					jsonUser.put("first_name", fname);
					jsonUser.put("last_name", lname);
					jsonUser.put("email", email);
					jsonUser.put("password", password);
	
					user.regiserUser(new ICallBack() {
						@Override
						public void callBackResultHandler(final Object object) {
							// TODO Auto-generated method stub
							//System.out.println(object);
							 
							   
							runOnUiThread(new Runnable()
			        		{
			        			@Override
			        			public void run()
			        			{
			        				
			        				
									   try {
										Toast.makeText(getBaseContext(), object.getClass().getDeclaredField("msg").get(object).toString(), Toast.LENGTH_SHORT).show();
									} catch (IllegalArgumentException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (NoSuchFieldException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
										 
									   
			        				
			        			}
			        		});
							
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

			// reg_step_1_layout.setVisibility(View.VISIBLE);
			// reg_step_3_layout.setVisibility(View.GONE);

			// Here we are reading server url from properties file
			// AssetsPropertyReader asserproperties = new
			// AssetsPropertyReader(RegistrationActivity.this);
			// Properties project_properties =
			// asserproperties.getProperties("project_config.properties");
			//
			// JSONHandler task = new JSONHandler();
			// String ss = project_properties.getProperty("server_url") +
			// "user_registration_login/";
			// task.execute(new String[] {ss});

			// RegistrationActivity.this.finish();
			// Intent intent = new Intent(mContext,
			// MemberSettingActivity.class);
			// startActivity(intent);

		}

	}
	
//	private class JSONHandler extends AsyncTask<String, Void, String> {
//
//		
//	    @Override
//	    protected String doInBackground(String... urls) {
//	        for (String url : urls) {
//	            JSONRPCClient client = JSONRPCClient.create(url, Versions.VERSION_2);
//	            client.setConnectionTimeout(2000);
//	            client.setSoTimeout(2000);
//	            
//	            JSONObject user = new JSONObject();
//	            try {
//	            	//String all_urses = client.callString("getAllUsers");
//
//	        		try {
//	        			user.put("first_name", fname);
//	        			user.put("last_name", lname);
//	        			user.put("email", email);
//	        			user.put("password", password);
//
//	        		} catch (JSONException e) {
//	        		    // TODO Auto-generated catch block
//	        		    e.printStackTrace();
//	        		}
//	            	
//	            	String flag = client.callString("userRegistration", user.toString());
//	            	System.out.println(flag);
//	        		
//	            } catch (JSONRPCException e) {
//	                e.printStackTrace(); //Invalid JSON Response caught here
//	            }
//	        }
//	        return null;
//	    }
//	}
	
	/**
	 * 
	 * @param fname
	 * @param lname
	 * @param email
	 * @param password
	 * @return josn object
	 */
	/*public JSONObject registerUser(String fname,String lname, String email, String password){
		
		JSONObject user = new JSONObject();
		try {
			user.put("first_name", fname);
			user.put("last_name", lname);
			user.put("email", email);
			user.put("password", password);

		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		
		// return user
		return user;
	}*/
	
	public void userValue() {
		 fname = mFirstname.getText().toString().trim();
		 lname = mLastname.getText().toString().trim();
		 email = mEmail.getText().toString().trim();
		 password = mPassword.getText().toString().trim();
	}
	
	/*
	 * 	Verification registration step1 Screen Data
	 */
	public boolean isVerifiedStep1() {
		
		//String fname = mFirstname.getText().toString().trim();
		//String lname = mLastname.getText().toString().trim();
		
		if(fname.length() == 0) {
			Toast.makeText(mContext, getString(R.string.firstnameRequired), Toast.LENGTH_SHORT).show();
			return false;
		} else if(lname.length() == 0) {
			Toast.makeText(mContext, getString(R.string.lastnameRequired), Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
		
	}
	
	/*
	 * 	Verification registration step2 Screen Data
	 */
	public boolean isVerifiedStep2() {
		
		//String password = mPassword.getText().toString().trim();
		
		if(password.length() == 0) {
			Toast.makeText(mContext, getString(R.string.passwordRequired), Toast.LENGTH_SHORT).show();
			return false;
		}else if(password.length() < 4){
			Toast.makeText(mContext, getString(R.string.passwordLengthRequired), Toast.LENGTH_SHORT).show();
			return false;
		}else {
			return true;
		}
		
	}
	
	/*
	 * 	Verification registration step2 Screen Data
	 */
	public boolean isVerifiedStep3() {

		//String email = mEmail.getText().toString().trim();
		
		if(email.length() == 0) {
			Toast.makeText(mContext, getString(R.string.emailRequired), Toast.LENGTH_SHORT).show();
			return false;
		}else {
			return true;
		}
		
	}

	/*
	 * Back to login click action
	 */
	public void backLogin(View view) {
		alert.showAlertDialog(RegistrationActivity.this, "registraion will be failed..", "You will loss data", false);
		RegistrationActivity.this.finish();
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
	}

}