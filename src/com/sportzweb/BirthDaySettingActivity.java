package com.sportzweb;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

public class BirthDaySettingActivity extends Activity{
	private Context mContext;
	private DatePicker dp;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_day_setting);
        dp = (DatePicker) findViewById(R.id.dpDOB);
        mContext = this;       
    }
	
	
	/*
	 * Save and continue click action
	 */
	public void saveANDcontinue(View v) {
			int day = dp.getDayOfMonth();
			int month = dp.getMonth();
			int year = dp.getYear();
			Toast.makeText(
					getApplicationContext(),
					"Your date of birth: (dd/mm/yyyy)" + day + "/" + (month + 1)
							+ "/" + year, Toast.LENGTH_LONG).show();

		Intent intent = new Intent(mContext, AddProfilePictureActivity.class);
		startActivity(intent);
		finish();
	}

}
