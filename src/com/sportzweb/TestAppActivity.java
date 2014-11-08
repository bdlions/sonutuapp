package com.sportzweb;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;

public class TestAppActivity extends ListActivity {
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;

		setContentView(R.layout.service_list);
	}

}
