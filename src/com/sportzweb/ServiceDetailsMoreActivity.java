package com.sportzweb;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceDetailsMoreActivity extends Activity {

	TextView sdMoreHeading, sdMoreStoreAddress,sdMoreAddress,sdMoreOpeningHour, sdMoreTelephone, sdMoreWWevsite,sdMoreProfile;
	Context context;
	Integer service_id = 2;
	String title,phone,distance,address;
	Button serviceMoreDetailsBackBtn;
	LinearLayout ll_service_recomments, ll_service_comments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_details_more);
		context = this;
		initUI();
	}
	
	
	public void initUI(){
		
		ll_service_comments = (LinearLayout) findViewById(R.id.ll_service_comments);
		ll_service_comments.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Clicked in comment", Toast.LENGTH_LONG).show();
				Intent i = new Intent(context, ServiceCommentsActivity.class);
				i.putExtra("service_id", service_id);
				i.putExtra("comments", "gg");
				startActivity(i);
				
			}
		});
		
		
		ll_service_recomments = (LinearLayout) findViewById(R.id.ll_service_recomments);
		
		ll_service_recomments.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Clicked in recomment", Toast.LENGTH_LONG).show();
				
			}
		});
		
		sdMoreHeading = (TextView) findViewById(R.id.sdMoreHeading);
		sdMoreStoreAddress = (TextView) findViewById(R.id.sdMoreStoreAddress);
		sdMoreOpeningHour = (TextView) findViewById(R.id.sdMoreOpeningHour);
		sdMoreTelephone = (TextView) findViewById(R.id.sdMoreTelephone);
		sdMoreWWevsite = (TextView) findViewById(R.id.sdMoreWWevsite);
		sdMoreAddress = (TextView) findViewById(R.id.sdMoreAddress);
		sdMoreProfile = (TextView) findViewById(R.id.sdMoreProfile);
		serviceMoreDetailsBackBtn = (Button) findViewById(R.id.serviceMoreDetailsBackBtn);
		serviceMoreDetailsBackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		Intent intent = getIntent();
		service_id = intent.getIntExtra("service_id", 0);
		String title = intent.getStringExtra("title");
		String phone = intent.getStringExtra("phone");
		String distance = intent.getStringExtra("distance");
		String address = intent.getStringExtra("address");
		
		sdMoreHeading.setText(title);
		sdMoreStoreAddress.setText(address);
		sdMoreOpeningHour.setText("10 AM");
		sdMoreTelephone.setText(phone);
		sdMoreWWevsite.setText("WWW.google.com");
		sdMoreAddress.setText(address);
		sdMoreProfile.setText("facebook.com");
		
		
	}
		

}
