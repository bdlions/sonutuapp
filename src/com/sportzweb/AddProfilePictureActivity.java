package com.sportzweb;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddProfilePictureActivity extends Activity{
	private Context mContext;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_picture);

        mContext = this;       
    }
	
	
	/*
	 * Save and continue click action
	 */
	public void saveANDcontinue(View v) {
		Intent intent = new Intent(mContext, AddProfilePictureActivity.class);
		startActivity(intent);
		finish();
	}

}
