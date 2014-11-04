package com.sportzweb;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.session.SessionManager;
import com.sonuto.utils.component.BlogCategoryCustomAdapter;
import com.sportzweb.JSONObjectModel.BlogCategory;

import android.R.color;
import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Colors;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditBlogActivity extends Activity {
	private Context mContext;

	FrameLayout bcategory_selection_step_layout, bedit_title_step_layout,bedit_main_step_layout, bedite_add_pic_step_layout;
	// process dialer
	ProgressDialog pDialog,dialog;
	ListView blogCategoryListView;
	EditText blogTitle,blogMainText;
	private String imagepath = null;
	ImageView blogImageView;
	public ImageLoader imageLoader;
	
	private ArrayList<BlogCategory> blogCategoryItem = new ArrayList<BlogCategory>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_blog);
		mContext = this;
		imageLoader = new ImageLoader(mContext);
		
		
		Intent i = getIntent();
		int blog_id = i.getIntExtra("blog_id", 0);
		
		//Toast.makeText(mContext, "blog id is: " + blog_id, Toast.LENGTH_SHORT).show();
		
		initUi(blog_id);
	}
	
	public void initUi(int blogId) {

		bcategory_selection_step_layout = (FrameLayout) findViewById(R.id.edit_blog_box1);
		bedit_title_step_layout = (FrameLayout) findViewById(R.id.edit_blog_box2);
		bedit_main_step_layout = (FrameLayout) findViewById(R.id.edit_blog_box3);
		bedite_add_pic_step_layout = (FrameLayout) findViewById(R.id.edit_blog_box4);
		blogCategoryListView = (ListView) findViewById(R.id.blogCategoryListviewEdit);
		blogImageView = (ImageView) findViewById(R.id.blogImageViewForEdit);
		
		blogTitle = (EditText) findViewById(R.id.blogTitleEdtTxt);
		blogMainText = (EditText) findViewById(R.id.blogMainTextEdtTxt);
		
		pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Fetching data..");
		pDialog.setCancelable(false);
		pDialog.show();
		
		BlogsApp blogApp = new BlogsApp();
		blogApp.getBlogDetails(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				pDialog.dismiss();
				JSONObject blogDetaisJSONObject = (JSONObject) object;
				//System.out.print(blogListJsonObj);
				
				try {
					//JSONArray blogCategoryListArray = blogDetaisJSONObject.getJSONArray("blog_category_list");
					Gson gson = new Gson();
					
					/*int total_item = blogCategoryListArray.length();
					for(int i=0;i<total_item;i++) {
						BlogCategory item = gson.fromJson(blogCategoryListArray.get(i).toString(), BlogCategory.class);
						blogCategoryItem.add(item);
					}
					
					BlogCategoryCustomAdapter adapter = new BlogCategoryCustomAdapter(EditBlogActivity.this, blogCategoryItem);
					blogCategoryListView.setAdapter(adapter);*/
					
					JSONObject blogDetails = blogDetaisJSONObject.getJSONObject("blog_info");
					//JSONObject blogCategoryIdList = blogDetails.getJSONArray("category_id_list");
					
					//blogCategory.setText(blog_category_title);
					//blogCategory.setTextColor(Color.parseColor("#00ACEA"));
					
					blogTitle.setText(blogDetails.getString("title").toString());
					blogMainText.setText(blogDetails.getString("description").toString());
					
					String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/blog_app/";

					blogImageView.setImageResource(R.drawable.upload_img_icon);
					imageLoader.DisplayImage(
							imagePath + blogDetails.get("picture").toString(),
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
		}, blogId);

	}
	
	/**
	 * onclick event function which is given in XML
	 * @param view
	 */
	public void editBlogCategorySelectionStep(View view) {
		bedit_title_step_layout.setVisibility(View.VISIBLE);
		bcategory_selection_step_layout.setVisibility(View.GONE);
		
	}
	
	
	
	/**
	 * onclick event function which is given in XML
	 * @param view
	 */
	
	public void editBlogTitleStep(View v) {
		if (isVerifiedTitleStep()) {
			bedit_main_step_layout.setVisibility(View.VISIBLE);
			bedit_title_step_layout.setVisibility(View.GONE);
		}
	}
	
	/**
	 * createBlogTitleStep validation method
	 * @return boolean value
	 */
	public boolean isVerifiedTitleStep() {
		if (blogTitle.length() == 0) {
			Toast.makeText(mContext, getString(R.string.blogTitleRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * onclick event function which is given in XML
	 * @param view
	 */
	public void editBlogMainTextStep(View v) {
		if(isVerifiedMainTextStep()) {
			bedite_add_pic_step_layout.setVisibility(View.VISIBLE);
			bedit_main_step_layout.setVisibility(View.GONE);
		}
	}
	
	/**
	 * createBlogMainTextStep validation method
	 * @return boolean value
	 */
	public boolean isVerifiedMainTextStep() {
		if (blogMainText.length() == 0) {
			Toast.makeText(mContext, getString(R.string.blogDescriptionRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}
	
	
	/*
	 * Save and continue click action
	 */
	public void editBlog(View v) {
		dialog = ProgressDialog.show(EditBlogActivity.this, "", "Updating blog and saving data...", true);
		 //messageText.setText("uploading started.....");
		 new Thread(new Runnable() {
           public void run() {             
                //uploadFile(imagepath);
          	 try {
          		 //uploadFile(imagepath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                                        
           }
         }).start(); 
		
	}
	
}
