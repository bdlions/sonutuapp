package com.sportzweb;

import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
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

public class MainActivity extends Activity {
	
	// Alert Dialog Manager
		AlertDialogManager alert = new AlertDialogManager();
		
		// Session Manager Class
		UserSessionManager session;
		
		// Button Logout
		Button btnLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Session class instance
        session = new UserSessionManager(getApplicationContext());
        
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
        session.checkLogin();
        
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        
        // name
        String password = user.get(UserSessionManager.KEY_PASSWORD);
        
        // email
        String email = user.get(UserSessionManager.KEY_EMAIL);
        
        // displaying user data
        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));
        lblName.setText(Html.fromHtml("Name: <b>" + password + "</b>"));
        
        
        /**
         * Logout button click event
         * */
        btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Clear the session data
				// This will clear all session data and 
				// redirect user to LoginActivity
				session.logoutUser();
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
