package com.sportzweb;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;

public class TestAppActivity extends Activity {
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.service_list);
		//GoogleMap googleMap;
		//googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	}

}
