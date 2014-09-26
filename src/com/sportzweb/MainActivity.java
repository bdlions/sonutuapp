package com.sportzweb;

import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sampanit.sonutoapp.utils.UserSessionManager;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.users.UserInfo;

public class MainActivity extends Activity {
	
	// Alert Dialog Manager
		AlertDialogManager alert = new AlertDialogManager();
		
		// Session Manager Class
		ISessionManager session;
		
		// Button Logout
		Button btnLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Session class instance
        session = new SessionManager(getApplicationContext());
        
        TextView lblName = (TextView) findViewById(R.id.lblName);
        TextView lblEmail = (TextView) findViewById(R.id.lblEmail);
        
        // Button logout
        btnLogout = (Button) findViewById(R.id.btnLogout);
        
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        
        
        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        if(session.isLoggedIn()){
        
	        // get user data from session
	        
	        lblEmail.setText(Html.fromHtml("Email: <b>" + session.getEmailAddress() + "</b>"));
	        lblName.setText(Html.fromHtml("Name: <b>" + session.getDisplayName() + "</b>"));
        }
        
        
        /**
         * Logout button click event
         * */
        btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Clear the session data
				// This will clear all session data and 
				// redirect user to LoginActivity
				if(session.logoutUser()){
					// After logout redirect user to Loing Activity
					Intent i = new Intent(getApplicationContext(), LoginActivity.class);
					// Closing all the Activities
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					
					// Add new Flag to start new Activity
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					// Staring Login Activity
					startActivity(i);
				}
				else{
					/**
					 * Logging out is impossible now
					 * */
				}
			}
		});
		

		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
