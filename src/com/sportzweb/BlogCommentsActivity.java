package com.sportzweb;

import com.sonuto.loadimage.ImageLoader;




import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class BlogCommentsActivity extends Activity {

	TextView blogCategory, blogTitle, blogDetail, imageDescription,
			blogDateTime;
	ImageView blogImageView;
	public ImageLoader imageLoader;
	Context context;
	String blog_category_title;
	// process dialer
	ProgressDialog pDialog;
	
	private RadioGroup radioGroup;
	private RadioButton positive, negitive, neutral;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_comments);
		context = this;
		initUi();
	}
	
	private void initUi() {
		radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if (checkedId == R.id.positive) {
					Toast.makeText(getApplicationContext(), "choice: positive",
							Toast.LENGTH_SHORT).show();
				} else if (checkedId == R.id.negitive) {
					Toast.makeText(getApplicationContext(), "choice: negitive",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "choice: neutral",
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		positive = (RadioButton) findViewById(R.id.positive);
		negitive = (RadioButton) findViewById(R.id.negitive);
		neutral = (RadioButton) findViewById(R.id.neutral);

	}

	

}
