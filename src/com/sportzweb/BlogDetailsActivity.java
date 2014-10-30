package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.JsonObject;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.TimedText;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BlogDetailsActivity extends Activity {

	TextView blogCategory, blogTitle, blogDetail, imageDescription,
			blogDateTime;
	ImageView blogImageView;
	public ImageLoader imageLoader;
	Context context;
	String blog_category_title;
	// process dialer
	ProgressDialog pDialog;
	ImageButton btnlike,btnComment,btnShare;
	Integer blog_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_details);
		context = this;
		initUI();
		process();
	}
	
	
	public void initUI(){
		
//		OnClickListener listener = new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            	
//            }
//      };
		
		imageLoader = new ImageLoader(context);
		blogCategory = (TextView) findViewById(R.id.blogCategoryText);
		blogTitle = (TextView) findViewById(R.id.blogTitleText);
		blogDetail = (TextView) findViewById(R.id.blogDetailsText);
		imageDescription = (TextView) findViewById(R.id.blogImageDescText);
		blogDateTime = (TextView) findViewById(R.id.blogPostedTimeDatetext);
		blogImageView = (ImageView) findViewById(R.id.BlogImageView);
		
		//ImageButton intitialize
		btnlike = (ImageButton) findViewById(R.id.btnLikeBlog);
		btnComment = (ImageButton) findViewById(R.id.btnCommentBlog);
		btnComment.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent i =  new Intent(context, BlogCommentsActivity.class);
				i.putExtra("blog_id", blog_id);
				startActivity(i);
				
			}
			
		});
		
		
		btnShare = (ImageButton) findViewById(R.id.btnShareBlog);
		
	}

	public void process() {
		Intent intent = getIntent();
		blog_id = intent.getIntExtra("blog_id", 0);
		blog_category_title = intent.getStringExtra("blog_category_title");

		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Fetching data..");
		pDialog.setCancelable(false);
		pDialog.show();

		BlogsApp blogapp = new BlogsApp();
		blogapp.getBlogDetails(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				JSONObject blogDetaisJSONObject = (JSONObject) object;
				pDialog.dismiss();
				try {
					JSONObject blogDetails = blogDetaisJSONObject
							.getJSONObject("blog_info");
					blogCategory.setText(blog_category_title);
					
					blogCategory.setTextColor(Color.parseColor("#00ACEA"));
					
					blogTitle.setText(blogDetails.getString("title"));
					blogDetail.setText(blogDetails.getString("description"));
					imageDescription.setText(blogDetails
							.getString("picture_description"));
					String imagePath = Config.SERVER_ROOT_URL
							+ "resources/images/applications/blog_app/";

					blogImageView.setImageResource(R.drawable.upload_img_icon);
					imageLoader.DisplayImage(
							imagePath + blogDetails.get("picture"),
							blogImageView);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub

			}
		}, blog_id);
	}
	
	public void comment(View view) {
		
		
	}

}
