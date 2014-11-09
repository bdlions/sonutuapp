package com.sportzweb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
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
import android.widget.Toast;

public class MyBlogActivity extends Activity {
	ListView listView;
	ProgressDialog pDiler;
	Context mComtext;
	TextView t1v, t2v, t3v, t4v;
	TableRow tbrow;
	TableLayout stk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_blogs);
		mComtext = this;
		initUi();
	}

	public void initUi() {

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
						tbrow.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
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
											public void onClick(
													DialogInterface dialog,
													int which) {
												Toast.makeText(
														getApplicationContext(),
														"well come", 1).show();
											}
										});
								alertDialog.setButton("NO",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												Toast.makeText(
														getApplicationContext(),
														"yoy have pressed cancel",
														1).show();
											}
										});
								// Set the Icon for the Dialog
								alertDialog.setIcon(R.drawable.fail);
								alertDialog.show();
								// see http://androidsnippets.com/simple-alert-dialog-popup-with-title-message-icon-and-button
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
		}, 69);

		/*
		 * for (int i = 0; i < 25; i++) {
		 * 
		 * TableRow tbrow = new TableRow(this);
		 * 
		 * TextView t1v = new TextView(this); t1v.setText("I am not alone");
		 * t1v.setTextColor(Color.parseColor("#000000"));
		 * t1v.setGravity(Gravity.CENTER); tbrow.addView(t1v);
		 * 
		 * TextView t2v = new TextView(this); t2v.setText("Approved");
		 * t2v.setTextColor(Color.parseColor("#000000"));
		 * t2v.setGravity(Gravity.CENTER); tbrow.addView(t2v);
		 * 
		 * TextView t3v = new TextView(this); t3v.setText("Edit");
		 * t3v.setTextColor(Color.parseColor("#00acea"));
		 * t3v.setGravity(Gravity.CENTER); tbrow.addView(t3v);
		 * 
		 * TextView t4v = new TextView(this); t4v.setText("Delete");
		 * t4v.setTextColor(Color.parseColor("#00acea"));
		 * t4v.setGravity(Gravity.CENTER); tbrow.addView(t4v);
		 * stk.addView(tbrow);
		 * 
		 * }
		 */
	}

}
