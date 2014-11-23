package com.sportzweb;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ServiceDetailsActivity extends Activity {

	TextView sDetailsHeading, sDetailsAddressMain, sDetailsAddressPhoneNo, sDetailsAddressDistance;
	//Button sDetailsBackBtn;
	Context context;
	Integer service_id;
	String title,phone,distance,address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_details);
		context = this;
		initUI();
	}
	
	
	public void initUI(){
		sDetailsHeading = (TextView) findViewById(R.id.serviceDetailsHeading);
		sDetailsAddressMain = (TextView) findViewById(R.id.serviceDetailsAddressMain);
		sDetailsAddressPhoneNo = (TextView) findViewById(R.id.serviceDetailsAddressPhoneNo);
		sDetailsAddressDistance = (TextView) findViewById(R.id.serviceDetailsAddressDistance);
		//sDetailsAddressDetails = (TextView) findViewById(R.id.serviceDetailsAddressDetails);
		//sDetailsBackBtn = (Button)findViewById(R.id.serviceDetailsBackBtn);
		
		Intent intent = getIntent();
		service_id = intent.getIntExtra("service_id", 0);
		title = intent.getStringExtra("title");
		phone = intent.getStringExtra("phone");
		distance = intent.getStringExtra("distance");
		address = intent.getStringExtra("address");
		
		sDetailsHeading.setText(title);
		sDetailsAddressMain.setText(address);
		sDetailsAddressPhoneNo.setText("Phone No : " + phone);
		sDetailsAddressDistance.setText("Distance :" + distance);
		
		// Get a handle to the Map Fragment
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		//LatLng dhaka = new LatLng(23.709921000000000000, 90.407143000000020000);
		LatLng dhaka = new LatLng(51.494566, -0.275827);

		
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 13));

        map.addMarker(new MarkerOptions()
                .title("Dhaka")
                .snippet("The most populous city in Bangladesh.")
                .position(dhaka));
        
       
		
//		sDetailsBackBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View view) {
//				finish();
//			}
//		});
//		
//		sDetailsAddressDetails.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(context, ServiceDetailsMoreActivity.class);
//				i.putExtra("service_id", service_id);
//				i.putExtra("title", title);
//				i.putExtra("address", address);
//				i.putExtra("phone", phone);
//				i.putExtra("distance", distance);
//				if(context != null)
//					context.startActivity(i);
//			}
//		});
		
	}
		

}
