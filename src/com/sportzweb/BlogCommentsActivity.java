package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.rpc.register.HealthyRecipeApp;
import com.sonuto.session.SessionManager;
import com.sonuto.users.AppID;
import com.sonuto.utils.custom.adapter.BlogCommentsCustomAdapter;
import com.sonuto.utils.custom.adapter.NewsCommentsCustomAdapter;
import com.sonuto.utils.custom.adapter.RCommentsCustomAdapter;
import com.sportzweb.JSONObjectModel.BlogComment;
import com.sportzweb.JSONObjectModel.NewsComment;
import com.sportzweb.JSONObjectModel.RecipeComment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class BlogCommentsActivity extends Activity {

	TextView blogCategory, blogTitle, blogDetail, imageDescription,
			blogDateTime;
	ImageView blogImageView;
	Button blogCommentPostBtn;
	EditText blogCommentText;
	ListView commentListViewForBlog;
	public ImageLoader imageLoader;
	Context context;
	String blog_category_title;
	// process dialer
	ProgressDialog pDialog;
	int blog_id, rate_id = 0;
	String comments, userComments;
	JSONArray commentsJSONArr;
	SessionManager session;

	private RadioGroup radioGroup;
	private RadioButton positive, negitive, neutral;
	private ArrayList<BlogComment> blogCommentObjList = new ArrayList<BlogComment>();
	private BlogCommentsCustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new BlogCommentsCustomAdapter(this, blogCommentObjList);
		setContentView(R.layout.activity_blog_comments);
		session = new SessionManager(getApplicationContext());
		context = this;
		initUI();
		Process();
	}

	private void initUI() {

		radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
		positive = (RadioButton) findViewById(R.id.positive);
		negitive = (RadioButton) findViewById(R.id.negitive);
		neutral = (RadioButton) findViewById(R.id.neutral);
		blogCommentText = (EditText) findViewById(R.id.blogCommentText);
		blogCommentPostBtn = (Button) findViewById(R.id.blogCommentPostBtn);
		commentListViewForBlog = (ListView) findViewById(R.id.commentListViewForBlog);

	}

	private void Process() {

		Intent intent = getIntent();
		blog_id = intent.getIntExtra("blog_id", 0);
		comments = intent.getStringExtra("comments");
		try {
			commentsJSONArr = new JSONArray(comments);
			Gson gson = new Gson();
			int total_comments = commentsJSONArr.length();
			for (int i = 0; i < total_comments; i++) {
				BlogComment comment = gson.fromJson(commentsJSONArr.get(i)
						.toString(), BlogComment.class);
				blogCommentObjList.add(comment);
			}

			adapter = new BlogCommentsCustomAdapter(this, blogCommentObjList);
			commentListViewForBlog.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if (checkedId == R.id.positive) {
					Toast.makeText(getApplicationContext(), "choice: positive",
							Toast.LENGTH_SHORT).show();
					rate_id = 1;
				} else if (checkedId == R.id.negitive) {
					Toast.makeText(getApplicationContext(), "choice: negitive",
							Toast.LENGTH_SHORT).show();
					rate_id = 2;
				} else {
					Toast.makeText(getApplicationContext(), "choice: neutral",
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		blogCommentPostBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				JSONObject jsonBlogCommentObj = new JSONObject();
				userComments = blogCommentText.getText().toString();
				if (isVerifiedCommentTextStep()) {
					try {
						int userId = session.getUserId();
						jsonBlogCommentObj.put("user_id", userId);
						jsonBlogCommentObj.put("application_id",
								AppID.BLOG.getValue());
						jsonBlogCommentObj.put("item_id", blog_id);
						jsonBlogCommentObj.put("comment", userComments);
						jsonBlogCommentObj.put("rate_id", rate_id);
					} catch (JSONException e) {
						e.printStackTrace();
					}

					pDialog = new ProgressDialog(context);
					pDialog.setMessage("Submitting comments and Fetching data..");
					pDialog.setCancelable(false);
					pDialog.show();

					BlogsApp blogApp = new BlogsApp();
					blogApp.postBlogComments(new ICallBack() {

						@Override
						public void callBackResultHandler(Object object) {
							pDialog.dismiss();
							JSONObject blogCommentJSONObject = (JSONObject) object;

							Gson gson = new Gson();
							try {
								JSONObject blogInfoObj = blogCommentJSONObject
										.getJSONObject("comment_info");
								BlogComment blogComment = gson.fromJson(
										blogInfoObj.toString(),
										BlogComment.class);
								blogCommentObjList.add(blogComment);
								adapter.notifyDataSetChanged();
								blogCommentText.setText("");
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void callBackErrorHandler(Object object) {

						}
					}, jsonBlogCommentObj.toString());
				}

			}
		});

	}

	/**
	 * isVerifiedCommentTextStep validation method
	 * 
	 * @return boolean value
	 */
	public boolean isVerifiedCommentTextStep() {
		if (userComments.length() == 0) {
			Toast.makeText(context, getString(R.string.commentRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

}
