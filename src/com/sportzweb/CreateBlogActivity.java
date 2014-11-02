package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.utils.component.BlogCategoryCustomAdapter;
import com.sportzweb.JSONObjectModel.BlogCategory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

public class CreateBlogActivity extends Activity {
	private Context mContext;

	FrameLayout bcategory_selection_step_layout, bcreate_title_step_layout,
			bcreate_main_step_layout, bcreate_add_pic_step_layout;
	// process dialer
	ProgressDialog pDialog;
	ListView blogCategoryListView;
	
	private ArrayList<BlogCategory> blogCategoryItem = new ArrayList<BlogCategory>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_blog);
		mContext = this;
		initUi();
	}

	public void initUi() {

		bcategory_selection_step_layout = (FrameLayout) findViewById(R.id.create_blog_box1);
		bcreate_title_step_layout = (FrameLayout) findViewById(R.id.create_blog_box2);
		bcreate_main_step_layout = (FrameLayout) findViewById(R.id.create_blog_box3);
		bcreate_add_pic_step_layout = (FrameLayout) findViewById(R.id.create_blog_box4);
		blogCategoryListView = (ListView) findViewById(R.id.blogCategoryListview);
		
		pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Fetching data..");
		pDialog.setCancelable(false);
		pDialog.show();
		
		BlogsApp blogApp = new BlogsApp();
		blogApp.getBlogCategoryList(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				pDialog.dismiss();
				JSONObject blogListJsonObj = (JSONObject) object;
				//System.out.print(blogListJsonObj);
				
				try {
					JSONArray blogCategoryListArray = blogListJsonObj.getJSONArray("blog_category_list");
					Gson gson = new Gson();
					
					int total_item = blogCategoryListArray.length();
					for(int i=0;i<total_item;i++) {
						BlogCategory item = gson.fromJson(blogCategoryListArray.get(i).toString(), BlogCategory.class);
						blogCategoryItem.add(item);
					}
					
					BlogCategoryCustomAdapter adapter = new BlogCategoryCustomAdapter(CreateBlogActivity.this, blogCategoryItem);
					blogCategoryListView.setAdapter(adapter);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	public void createBlogCategorySelectionStep(View view) {
		bcreate_title_step_layout.setVisibility(View.VISIBLE);
		bcategory_selection_step_layout.setVisibility(View.GONE);
		
	}

	public void createBlogTitleStep(View v) {
		bcreate_main_step_layout.setVisibility(View.VISIBLE);
		bcreate_title_step_layout.setVisibility(View.GONE);
	}

	public void createBlogMainTextStep(View v) {
		bcreate_add_pic_step_layout.setVisibility(View.VISIBLE);
		bcreate_main_step_layout.setVisibility(View.GONE);
	}

	public void browseFile(View v) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(
				Intent.createChooser(intent, "Complete action using"), 1);
	}

}
