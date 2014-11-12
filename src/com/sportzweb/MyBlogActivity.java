package com.sportzweb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.JSONObjectModel.MyBlog;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyBlogActivity extends Activity {
	ListView listView;
	ProgressDialog pDiler;
	Context mComtext;
	TextView t1v, t2v, t3v, t4v;
	TableRow tbrow;
	TableLayout stk;
	ISessionManager session;
	int userId;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_blogs);
		mComtext = this;
		// Session Manager
		session = new SessionManager(getApplicationContext());
		
		initUi();
	}

	public void initUi() {
		
		userId = session.getUserId();
		
		stk = (TableLayout) findViewById(R.id.table_main);
		pDiler = new ProgressDialog(mComtext);
		pDiler.setMessage("Loading data...");
		pDiler.setCancelable(false);
		pDiler.show();
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		
		BlogsApp blogApp = new BlogsApp();
		blogApp.getMyBlogList(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				JSONObject myBlogListObj = (JSONObject) object;
				try {
					pDiler.dismiss();
					JSONArray blogListJsonArray = myBlogListObj
							.getJSONArray("my_blog_list");
					Gson gson = new Gson();
					int total_blog = blogListJsonArray.length();
					for (int i = 0; i < total_blog; i++) {
						MyBlog myBlog = gson.fromJson(blogListJsonArray.get(i)
								.toString(), MyBlog.class);

						tbrow = new TableRow(mComtext);
						tbrow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
						t1v = new TextView(mComtext);
						t2v = new TextView(mComtext);
						t3v = new TextView(mComtext);
						t4v = new TextView(mComtext);

						// add first column
						String title = myBlog.getTitle();
						if (title.length() > 10) {
							title = title.substring(0, 10);
						}
						t1v.setText(title);
						t1v.setTextColor(Color.parseColor("#000000"));
						t1v.setGravity(Gravity.CENTER);
						tbrow.addView(t1v);

						// add second column
						t2v.setText(myBlog.getStatus_title());
						t2v.setTextColor(Color.parseColor("#000000"));
						t2v.setGravity(Gravity.CENTER);
						tbrow.addView(t2v);

						// add third column
						final int blogId = myBlog.getBlog_id();
						t3v.setText("Edit");
						t3v.setClickable(true);
						t3v.setTextColor(Color.parseColor("#00acea"));
						t3v.setGravity(Gravity.CENTER);
						tbrow.addView(t3v);
						t3v.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Intent i = new Intent(MyBlogActivity.this,
										EditBlogActivity.class);
								i.putExtra("blog_id", blogId);
								startActivity(i);
							}
						});

						// add fourth column
						t4v.setText("Delete");
						t4v.setTextColor(Color.parseColor("#00acea"));
						t4v.setGravity(Gravity.CENTER);

						t4v.setOnClickListener(new OnClickListener() {
							@SuppressWarnings("deprecation")
							@Override
							public void onClick(View v) {
								alertDialog.setTitle("Delete Blog");
								alertDialog.setMessage("Are you sure about deleting this blog?");
								alertDialog.setButton2("YES",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,int which) {
												//Toast.makeText(getApplicationContext(),"well come", 1).show();
												deleteBlog(blogId);
											}
										});
								alertDialog.setButton("NO",new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,int which) {
												
											}
										});
								// Set the Icon for the Dialog
								alertDialog.setIcon(R.drawable.fail);
								alertDialog.show();
							}
						});

						// table row dynamically
						tbrow.addView(t4v);
						stk.addView(tbrow);

					}
				} catch (JSONException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void callBackErrorHandler(Object object) {
				System.out.print(object);

			}
			// here the user id will be dynamic according to logged-in user
		}, userId);

	}
	
	/**
	 * this function for sending delete request
	 * @param blogId
	 */
	
	public void deleteBlog(int blogId) {

		pDiler = new ProgressDialog(mComtext);
		pDiler.setMessage("Requesting to delete blog...");
		pDiler.setCancelable(false);
		pDiler.show();
		
		BlogsApp blogAppForDelete = new BlogsApp();
		blogAppForDelete.requestForBlogDelete(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object object) {
				pDiler.dismiss();
				JSONObject jsonObject = (JSONObject)object;
				try {
					String msg = jsonObject.get("message").toString();
					if(jsonObject.get("status").toString().equalsIgnoreCase("true")) {
						alert.showAlertDialog(MyBlogActivity.this, "Success..",msg, true);
						Intent i = new Intent(mComtext, MyBlogActivity.class);
						startActivity(i);
						finish();
					} else {
						alert.showAlertDialog(MyBlogActivity.this, " failed..",msg, false);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void callBackErrorHandler(Object object) {
				
				
			}
		}, blogId);
	}

}
