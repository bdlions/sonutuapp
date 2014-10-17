package com.sportzweb;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;


public class TestActivity extends Activity {

	private Context mContext;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext = this;
            
    }

}
