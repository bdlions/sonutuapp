package com.sportzweb;

import com.sonuto.users.Gender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GenderSelectionActivity extends Activity {
	private Context mContext;
	ImageButton imgButtonMale, imgButtonFemale;
	private LinearLayout maleBorder, femaleBorder;
	Gender genderValue;
	private int initialBackColor = 0xffffffff;
	private int selectedColor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gender_selection);
		
		selectedColor = 0xFFB5B5B5;
		
		mContext = this;
		imgButtonMale = (ImageButton) findViewById(R.id.image_male_button);
		imgButtonFemale = (ImageButton) findViewById(R.id.image_female_button);
		
		maleBorder = (LinearLayout) findViewById(R.id.maleBorder);
		femaleBorder = (LinearLayout) findViewById(R.id.femaleBorder);
		
		imgButtonMale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				genderValue = Gender.MALE;
				GenderSelectionActivity.this.maleBorder.setBackgroundColor(selectedColor);
				GenderSelectionActivity.this.femaleBorder.setBackgroundColor(initialBackColor);
			}
		});
		
		imgButtonFemale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				genderValue = Gender.FEMALE;
				GenderSelectionActivity.this.maleBorder.setBackgroundColor(initialBackColor);
				GenderSelectionActivity.this.femaleBorder.setBackgroundColor(selectedColor);
			}
		});
	}

	/*
	 * Save and continue click action for profileInformation activity
	 */
	public void saveANDcontinue(View view) {
		if(genderValue==Gender.FEMALE || genderValue==Gender.MALE){
			//Toast.makeText(mContext, genderValue.toString(), Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(mContext, ProfileInformationActivity.class);
			startActivity(intent);
			finish();
		} else{
			Toast.makeText(mContext, "Please select a gender", Toast.LENGTH_SHORT).show();
		}
		
	}

}
