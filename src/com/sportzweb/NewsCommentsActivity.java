package com.sportzweb;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.HealthyRecipeApp;
import com.sonuto.session.SessionManager;
import com.sonuto.users.AppID;
import com.sonuto.utils.custom.adapter.NewsCommentsCustomAdapter;
import com.sonuto.utils.custom.adapter.RCommentsCustomAdapter;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class NewsCommentsActivity extends Activity {
	ImageView blogImageView;
	ListView commentListViewForNews;
	public ImageLoader imageLoader;
	Context context;
	String blog_category_title;
	// process dialer
	ProgressDialog pDialog;
	EditText newsCommentText;
	Button newsCommentPostBtn;
	
	private RadioGroup radioGroup;
	private RadioButton positive, negitive, neutral;
	int news_id,rate_id = 0;
	String comments,userComments;
	JSONArray commentsJSONArr;
	SessionManager session;
	private NewsCommentsCustomAdapter adapter;
	private ArrayList<NewsComment> newsCommentObjList = new ArrayList<NewsComment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_comment);
		session = new SessionManager(getApplicationContext());
		context = this;
		initUi();
		process();
	}
	
	
	private void initUi() {
		radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
		positive = (RadioButton) findViewById(R.id.positive);
		negitive = (RadioButton) findViewById(R.id.negitive);
		neutral = (RadioButton) findViewById(R.id.neutral);
		
		newsCommentText = (EditText) findViewById(R.id.newsCommentText);
		newsCommentPostBtn = (Button) findViewById(R.id.newsCommentPostBtn);
		commentListViewForNews = (ListView) findViewById(R.id.commentListViewForNews);
	}
	
	
	private void process() {
		Intent intent = getIntent();
		news_id = intent.getIntExtra("news_id", 0);
		comments = intent.getStringExtra("comments");
		try {
			commentsJSONArr = new JSONArray(comments);
			Gson gson = new Gson();
			int total_comments = commentsJSONArr.length();
			for (int i = 0; i < total_comments; i++) {
				NewsComment comment = gson.fromJson(commentsJSONArr.get(i).toString(), NewsComment.class);
				newsCommentObjList.add(comment);
			}
			
			adapter = new NewsCommentsCustomAdapter(this, newsCommentObjList);
			commentListViewForNews.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if (checkedId == R.id.positive) {
					Toast.makeText(getApplicationContext(), "choice: positive",Toast.LENGTH_SHORT).show();
					rate_id = 1;
				} else if (checkedId == R.id.negitive) {
					Toast.makeText(getApplicationContext(), "choice: negitive",Toast.LENGTH_SHORT).show();
					rate_id = 2;
				} else {
					Toast.makeText(getApplicationContext(), "choice: neutral",Toast.LENGTH_SHORT).show();
				}
			}

		});

		newsCommentPostBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				JSONObject jsonRecipeCommentObj = new JSONObject();
				userComments = newsCommentText.getText().toString();
				if(isVerifiedCommentTextStep()) {
					try {
						int userId = session.getUserId();
						jsonRecipeCommentObj.put("user_id", userId);
						jsonRecipeCommentObj.put("application_id", AppID.NEWS.getValue());
						jsonRecipeCommentObj.put("item_id", news_id);
						jsonRecipeCommentObj.put("comment", userComments);
						jsonRecipeCommentObj.put("rate_id", rate_id);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					
					pDialog = new ProgressDialog(context);
					pDialog.setMessage("Submitting comment and Fetching data..");
					pDialog.setCancelable(false);
					pDialog.show();
					
					HealthyRecipeApp healthyRecipeapp = new HealthyRecipeApp();
					healthyRecipeapp.postrecipeComments(new ICallBack() {

						@Override
						public void callBackResultHandler(Object object) {
							pDialog.dismiss();
							JSONObject newsCommentJSONObject = (JSONObject) object;
								JSONObject newsCommentInfoArray;
								try {
									newsCommentInfoArray = newsCommentJSONObject.getJSONObject("comment_info");
									Gson gson = new Gson();
									
									NewsComment newsInfo = gson.fromJson(newsCommentInfoArray.toString(), NewsComment.class);
									newsCommentObjList.add(newsInfo);
									adapter.notifyDataSetChanged();
								} catch (JSONException e) {
									e.printStackTrace();
								}
						}

						@Override
						public void callBackErrorHandler(Object object) {

						}
					}, jsonRecipeCommentObj.toString());
				}
				
			}
		});
	}


	/**
	 * isVerifiedCommentTextStep validation method
	 * @return boolean value
	 */
	public boolean isVerifiedCommentTextStep() {
		if (userComments.length() == 0) {
			Toast.makeText(context, getString(R.string.recipeCommentRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

}
