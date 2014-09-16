package com.sportzweb;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class LoginActivity extends Activity {

	//TextSwitcher textswitcher_app_name;
	
	// Animation
    Animation animFadein;
    Animation animMove;
    TextView textview_app_name;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
//		textswitcher_app_name = (TextSwitcher) findViewById(R.id.textswitcher_app_name);
//		
//		// Set the ViewFactory of the TextSwitcher that will create TextView object when asked
//		textswitcher_app_name.setFactory(new ViewSwitcher.ViewFactory() {
//			
//			@Override
//			public View makeView() {
//				 // TODO Auto-generated method stub
//                // create new textView and set the properties like clolr, size etc
//                TextView myText = new TextView(LoginActivity.this);
//                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
//                myText.setTextSize(36);
//                myText.setTextColor(Color.BLUE);
//                return myText;
//			}
//		});
//		textswitcher_app_name.setText("sdfsdfdsf");
//        
//		// Declare the in and out animations and initialize them 
//        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
//        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
//       
//        // set the animation type of textSwitcher
//        textswitcher_app_name.setInAnimation(in);
//        textswitcher_app_name.setOutAnimation(out);
		
		
		// load the animation
		animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move); 
        
		animMove.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				LinearLayout loginbox = (LinearLayout)findViewById(R.id.loginbox);
				loginbox.setVisibility(View.VISIBLE);
				
				// load the animation
				animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
		                R.anim.fade_in); 
		        
				animFadein.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
				});
		        
				loginbox.startAnimation(animFadein);
			}
		});
        
        textview_app_name = (TextView) findViewById(R.id.textview_app_name);
        textview_app_name.startAnimation(animMove);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	

}
