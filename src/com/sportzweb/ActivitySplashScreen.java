package com.sportzweb;

import java.util.Timer;
import java.util.TimerTask;

import com.sonuto.session.SessionManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivitySplashScreen extends Activity {
	int images[] = {  R.drawable.frame_001, R.drawable.frame_002,
			R.drawable.frame_003, R.drawable.frame_004, R.drawable.frame_005,
			R.drawable.frame_006, R.drawable.frame_007, R.drawable.frame_008,
			R.drawable.frame_013, R.drawable.frame_020, R.drawable.frame_027, 
			R.drawable.frame_034, R.drawable.frame_041, R.drawable.frame_048, 
			R.drawable.frame_055, R.drawable.frame_062, R.drawable.frame_063, 
			R.drawable.frame_070, R.drawable.frame_077, R.drawable.frame_084, 
			R.drawable.frame_091, R.drawable.frame_098, R.drawable.frame_105, 
			R.drawable.frame_112, R.drawable.frame_119, R.drawable.frame_120,
			R.drawable.frame_122, R.drawable.frame_123, R.drawable.frame_124,
			R.drawable.frame_125, R.drawable.frame_126, R.drawable.frame_127,
			R.drawable.frame_128, R.drawable.frame_129, R.drawable.frame_130,
			R.drawable.frame_131, R.drawable.frame_132, R.drawable.frame_133,
			R.drawable.frame_134, R.drawable.frame_135, R.drawable.frame_136,
			R.drawable.frame_137, R.drawable.frame_138, R.drawable.frame_139,
			R.drawable.frame_140, R.drawable.frame_141, R.drawable.frame_142,
			R.drawable.frame_143, R.drawable.frame_144 };
	private Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*
		 * if this user has an active session go to the newsfeed activity
		 * otherwise show the splash screen and then go to the login activity
		 * */ 
		
		if (SessionManager.getInstance().isLoggedIn()) {
			/*
			 * has an active session go to the newsfeed activity
			 * */
			Intent i = new Intent(getApplicationContext(), ApplicationPane.class);
			startActivity(i);
			finish();
		}
		else{
			setContentView(R.layout.activity_splash_screen);
	
			ImageView imageviewLogo = (ImageView) findViewById(R.id.imageviewLogo);
			AnimationDrawable logoAnimation = new AnimationDrawable();
	
			for (int i = 0; i < images.length; i++) {
				logoAnimation.addFrame(getResources().getDrawable(images[i]), 12);
			}
	
			imageviewLogo.setImageDrawable(logoAnimation);
			logoAnimation.setOneShot(true);
			logoAnimation.start();
	
			timer = new Timer();
			LoginActivityLanucher loginActivityLanucher = new LoginActivityLanucher();
			timer.schedule(loginActivityLanucher, 2000);
		}

	}

	class LoginActivityLanucher extends TimerTask {

		@Override
		public void run() {

			timer.cancel();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Intent i = new Intent(getApplicationContext(),LoginActivity.class);
					startActivity(i);
					finish();
				}
			});
		}

	}
}
