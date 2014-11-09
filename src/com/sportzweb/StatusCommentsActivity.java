package com.sportzweb;
import com.bdlions.load.image.ImageLoader;

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


public class StatusCommentsActivity extends Activity {

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
		setContentView(R.layout.activity_status_comments);
		context = this;
		initUi();
	}
	
	private void initUi() {
	

	}

	

}
