package com.sportzweb;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.net.ssl.ManagerFactoryParameters;

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

import com.google.gson.Gson;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.session.SessionManager;
import com.sonuto.utils.component.ArrayListFragment;
import com.sportzweb.JSONObjectModel.BlogCategory;










import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateBlogActivity extends Activity {
	private Context mContext;

	FrameLayout bcategory_selection_step_layout, bcreate_title_step_layout,
			bcreate_main_step_layout, bcreate_add_pic_step_layout;
	// process dialer
	ProgressDialog pDialog,dialog;
	LinearLayout categoryListFragmentLayout;
	EditText blogTitle,blogMainText;
	private String imagepath = null;
	CheckedTextView category_checkBox;
	ArrayListFragment list;
	ArrayList<Integer> selectedItem = new ArrayList<Integer>();
	SessionManager session;
	
	private ArrayList<BlogCategory> blogCategoryItem = new ArrayList<BlogCategory>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_blog);
		mContext = this;
		session = new SessionManager(mContext);
		initUi();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            imagepath = getPath(selectedImageUri);
            ImageView imageview = (ImageView) findViewById(R.id.blogImageView);
            imageview.setImageURI(selectedImageUri);
		}
	}
	
	public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

	public void initUi() {

		bcategory_selection_step_layout = (FrameLayout) findViewById(R.id.create_blog_box1);
		bcreate_title_step_layout = (FrameLayout) findViewById(R.id.create_blog_box2);
		bcreate_main_step_layout = (FrameLayout) findViewById(R.id.create_blog_box3);
		bcreate_add_pic_step_layout = (FrameLayout) findViewById(R.id.create_blog_box4);
		categoryListFragmentLayout = (LinearLayout) findViewById(R.id.categoryListFragmentLayout);
		
		blogTitle = (EditText) findViewById(R.id.blogTitleEdtTxt);
		blogMainText = (EditText) findViewById(R.id.blogMainTextEdtTxt);
		
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
				
				try {
					JSONArray blogCategoryListArray = blogListJsonObj.getJSONArray("blog_category_list");
					Gson gson = new Gson();
					
					int total_item = blogCategoryListArray.length();
					for(int i=0;i<total_item;i++) {
						BlogCategory item = gson.fromJson(blogCategoryListArray.get(i).toString(), BlogCategory.class);
						blogCategoryItem.add(item);
					}
					if (getFragmentManager().findFragmentById(R.id.categoryListFragmentLayout) == null) {
			            list = new ArrayListFragment();
			            list.setBlogCategoryItem(blogCategoryItem);
			            getFragmentManager().beginTransaction().add(R.id.categoryListFragmentLayout, list).commit();
			        }
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void callBackErrorHandler(Object object) {
				
			}
		});

	}
	
	/**
	 * onclick event function which is given in XML
	 * @param view
	 */
	public void createBlogCategorySelectionStep(View view) {
		SparseBooleanArray checked = list.getListView().getCheckedItemPositions();
		int len = checked.size();
		for (int i = 0; i < len; i++) {
			int pos = checked.keyAt(i);
			BlogCategory bc = (BlogCategory) list.getListView().getItemAtPosition(pos);
			int blogId = bc.getId();
			selectedItem.add(blogId);
		}
		
		if(selectedItem.isEmpty()) {
			Toast.makeText(mContext, "Please select atleast one category",Toast.LENGTH_SHORT).show();
		} else {
			
			Toast.makeText(mContext, selectedItem + " ",Toast.LENGTH_SHORT).show();
			bcreate_title_step_layout.setVisibility(View.VISIBLE);
			bcategory_selection_step_layout.setVisibility(View.GONE);
		}
		
	}
	
	
	
	/**
	 * onclick event function which is given in XML
	 * @param view
	 */
	
	public void createBlogTitleStep(View v) {
		if (isVerifiedTitleStep()) {
			bcreate_main_step_layout.setVisibility(View.VISIBLE);
			bcreate_title_step_layout.setVisibility(View.GONE);
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
	public void createBlogMainTextStep(View v) {
		if(isVerifiedMainTextStep()) {
			bcreate_add_pic_step_layout.setVisibility(View.VISIBLE);
			bcreate_main_step_layout.setVisibility(View.GONE);
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
	public void createBlog(View v) {
		dialog = ProgressDialog.show(CreateBlogActivity.this, "", "Creating blog and saving data...", true);
		 //messageText.setText("uploading started.....");
		 new Thread(new Runnable() {
           public void run() {
          	 try {
          		 uploadFile(imagepath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                                        
           }
         }).start(); 
		
	}
	

	public void browseFile(View v) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(
				Intent.createChooser(intent, "Complete action using"), 1);
	}
	
	private void uploadFile(String filePath) {

		InputStream inputStream;
		try {
			File file = new File(filePath);
			inputStream = new FileInputStream(file);
			byte[] data;
			try {
				data = IOUtils.toByteArray(inputStream);

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(Config.CREATE_BLOG_DIR);

				InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), file.getName());
				
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();        

				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addPart("userfile", inputStreamBody);
				
				int userId = session.getUserId();
				JSONArray collection = new JSONArray(selectedItem);
				
				builder.addPart("blog_category_list", new StringBody(collection.toString(), ContentType.TEXT_PLAIN));
				builder.addPart("user_id", new StringBody(Integer.toString(userId), ContentType.TEXT_PLAIN));
				builder.addPart("title", new StringBody(blogTitle.getText().toString(), ContentType.TEXT_PLAIN));
				builder.addPart("description", new StringBody(blogMainText.getText().toString(), ContentType.TEXT_PLAIN));
				
				HttpEntity entity = builder.build();
				
				httpPost.setEntity(entity);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				
				HttpEntity resEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(resEntity);

				JSONArray jsonarray = new JSONArray("[" + response + "]");
				JSONObject jsonobject = jsonarray.getJSONObject(0);
				
				
				final String msg = (jsonobject.getString("message"));
				
				runOnUiThread(new Runnable() {
					public void run() {
						//messageText.setText(msg);
						TextView textViewMessage = (TextView)findViewById(R.id.textViewMsg);
						textViewMessage.setText(msg);
						textViewMessage.setTextColor(Color.GREEN);
					}
				});
				
				dialog.dismiss();
				
				// Close the connection
				httpClient.getConnectionManager().shutdown();
				
				Intent intent = new Intent(mContext, MyBlogActivity.class);
				startActivity(intent);
				finish();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

}
