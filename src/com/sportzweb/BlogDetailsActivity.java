package com.sportzweb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.JsonObject;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.session.SessionManager;
import com.sonuto.users.AppID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.TimedText;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BlogDetailsActivity extends Activity {

	TextView blogCategory, blogTitle, blogDetail, imageDescription,
			blogDateTime;
	LinearLayout ll_comment_blog,ll_share_blog;
	ImageView blogImageView;
	public ImageLoader imageLoader;
	Context context;
	String blog_category_title;
	// process dialer
	ProgressDialog pDialog;
	ImageButton btnComment,btnShare;
	Integer blog_id;
	JSONArray blogCommentsArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_details);
		context = this;
		initUI();
		process();
	}
	
	
	public void initUI(){
		
		imageLoader = new ImageLoader(context);
		ll_comment_blog = (LinearLayout) findViewById(R.id.ll_comment_blog);
		ll_share_blog = (LinearLayout) findViewById(R.id.ll_share_blog);
		
		blogCategory = (TextView) findViewById(R.id.blogCategoryText);
		blogTitle = (TextView) findViewById(R.id.blogTitleText);
		blogDetail = (TextView) findViewById(R.id.blogDetailsText);
		imageDescription = (TextView) findViewById(R.id.blogImageDescText);
		blogDateTime = (TextView) findViewById(R.id.blogPostedTimeDatetext);
		blogImageView = (ImageView) findViewById(R.id.BlogImageView);
		

		ll_comment_blog.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent i =  new Intent(context, BlogCommentsActivity.class);
				i.putExtra("blog_id", blog_id);
				i.putExtra("comments", blogCommentsArray.toString());
				startActivity(i);
				
			}
			
		});
		
		ll_share_blog.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				JSONObject jsonRecipeObj = new JSONObject();
				
				try {
					int userId = SessionManager.getInstance().getUserId();
					jsonRecipeObj.put("user_id", userId);
					jsonRecipeObj.put("application_id", AppID.BLOG.getValue());
					jsonRecipeObj.put("item_id", blog_id);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			
				BlogsApp blogApp = new BlogsApp();
				blogApp.blogShare(new ICallBack() {
				
				@Override
				public void callBackResultHandler(Object object) {
					
					
				}
				
				@Override
				public void callBackErrorHandler(Object object) {
					
					
				}
			}, jsonRecipeObj);
				
			}
			
		});
		
		
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
					JSONObject blogDetails = blogDetaisJSONObject.getJSONObject("blog_info");
					//JSONObject blogCategoryIdList = blogDetaisJSONObject.getJSONObject("category_id_list");
					//JSONObject blogCategoryIdList = blogDetails.getJSONObject("blog_category_list");
					blogCommentsArray = blogDetaisJSONObject.getJSONArray("comment_list");
					
					blogCategory.setText(blog_category_title);
					blogCategory.setTextColor(Color.parseColor("#00ACEA"));
					if(blogDetails.getString("title") != null) {
						blogTitle.setText(Html.fromHtml(blogDetails.getString("title")));
					} else {
						blogTitle.setText("");
					}
					
					if(blogDetails.getString("description") != null) {
						blogDetail.setText(Html.fromHtml(blogDetails.getString("description")));
					} else {
						blogDetail.setText("");
					}
					
					imageDescription.setText(blogDetails.getString("picture_description"));
					String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/blog_app/";

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

}
