package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.sonuto.Config;
import com.sonuto.loadimage.ImageLoader;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.TimedText;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class BlogDetailsActivity extends Activity{
	
	TextView blogCategory,blogTitle,blogDetail,imageDescription,blogDateTime;
	ImageView blogImageView;
	public ImageLoader imageLoader; 
	Context context;
	//String blog_category_title;
	// process dialer
	//ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_details);
		imageLoader=new ImageLoader(context);
		
		//blogCategory = (TextView) findViewById(R.id.blogCategoryText);
		blogTitle = (TextView) findViewById(R.id.blogTitleText);
		blogDetail = (TextView) findViewById(R.id.blogDetailsText);
		imageDescription = (TextView) findViewById(R.id.blogImageDescText);
		blogDateTime = (TextView) findViewById(R.id.blogPostedTimeDatetext);
		
		blogImageView = (ImageView) findViewById(R.id.BlogImageView);
		
		
		Intent intent = getIntent();
		Integer blog_id = intent.getIntExtra("blog_id", 0);
		//blog_category_title = intent.getStringExtra("blog_category_title");
		
		//Toast.makeText(getApplicationContext(), "blog is : " + blog_id + "blog category tile : " + blog_category_title, Toast.LENGTH_SHORT).show();
		
//		pDialog = new ProgressDialog(context);
//		pDialog.setMessage("Fetching data..");
//		pDialog.setCancelable(false);
//		pDialog.show();
		
		BlogsApp blogapp = new BlogsApp();
		blogapp.getBlogDetails(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object object) {
				JSONObject blogDetaisJSONObject = (JSONObject) object;
				//pDialog.dismiss();
				try {
					JSONObject blogDetails = blogDetaisJSONObject.getJSONObject("blog_info");
					//blogCategory.setText(blog_category_title);
					blogTitle.setText(blogDetails.getString("title"));
					blogDetail.setText(blogDetails.getString("description"));
					imageDescription.setText(blogDetails.getString("picture_description"));
					String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/blog_app/";
					
					blogImageView.setImageResource(R.drawable.upload_img_icon);
			        imageLoader.DisplayImage(imagePath+blogDetails.get("picture"), blogImageView);
					
					//System.out.print(blogDetails.get("blog_id"));
					//System.out.print(blogDetails.get("picture_description"));
					//System.out.print(blogDetails.get("picture"));
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

}
