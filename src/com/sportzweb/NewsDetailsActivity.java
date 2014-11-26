package com.sportzweb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.JsonObject;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.HealthyRecipeApp;
import com.sonuto.rpc.register.NewsApp;
import com.sonuto.session.SessionManager;
import com.sonuto.users.AppID;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetailsActivity extends Activity {

	TextView newsCategory, newsTitle, newsDetail, imageDescription,
			newsDateTime;
	LinearLayout ll_news_comment, ll_news_share;
	ImageView newsImageView;
	public ImageLoader imageLoader;
	Context context;
	String news_category_title;
	// process dialer
	ProgressDialog pDialog;
	ImageButton btnlike, btnComment, btnShare;
	Integer news_id;
	JSONArray newsCommentsArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_details);
		context = this;
		initUI();
		process();
	}

	public void initUI() {
		ll_news_comment = (LinearLayout) findViewById(R.id.ll_news_comment);
		ll_news_share = (LinearLayout) findViewById(R.id.ll_news_share);
		imageLoader = new ImageLoader(context);
		//newsCategory = (TextView) findViewById(R.id.newsCategoryText);
		newsTitle = (TextView) findViewById(R.id.newsTitleText);
		newsDetail = (TextView) findViewById(R.id.newsDetailsText);
		imageDescription = (TextView) findViewById(R.id.newsPictureDescriptionText);
		newsDateTime = (TextView) findViewById(R.id.newsPostedTimeDatetext);
		newsImageView = (ImageView) findViewById(R.id.newsImageView);

		ll_news_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(context, NewsCommentsActivity.class);
				i.putExtra("news_id", news_id);
				i.putExtra("comments", newsCommentsArray.toString());
				startActivity(i);

			}

		});

		ll_news_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				JSONObject jsonNewsObj = new JSONObject();

				try {
					int userId = SessionManager.getInstance().getUserId();
					jsonNewsObj.put("user_id", userId);
					jsonNewsObj.put("application_id", AppID.NEWS.getValue());
					jsonNewsObj.put("item_id", news_id);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				NewsApp newsApp = new NewsApp();
				newsApp.newsShare(new ICallBack() {

					@Override
					public void callBackResultHandler(Object object) {

					}

					@Override
					public void callBackErrorHandler(Object object) {

					}
				}, jsonNewsObj);
			}

		});

	}

	public void process() {
		Intent intent = getIntent();
		news_id = intent.getIntExtra("news_id", 0);
		news_category_title = intent.getStringExtra("news_category_title");

		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Fetching data..");
		pDialog.setCancelable(false);
		pDialog.show();

		NewsApp newsApp = new NewsApp();
		newsApp.getNewsDetails(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				JSONObject newsDetaisJSONObject = (JSONObject) object;
				pDialog.dismiss();
				try {
					JSONObject newsDetails = newsDetaisJSONObject
							.getJSONObject("news_info");
					// newsCategory.setText(news_category_title);
					newsCommentsArray = newsDetaisJSONObject.getJSONArray("comment_list");
					
					//newsCategory.setTextColor(Color.parseColor("#00ACEA"));
					
					if(newsDetails.getString("headline") != null) {
						newsTitle.setText(newsDetails.getString("headline"));
					} else {
						newsTitle.setText("");
					}
					
					if(newsDetails.getString("description") != null) {
						newsDetail.setText(newsDetails.getString("description"));
					} else {
						newsDetail.setText("");
					}
					
					
					if(newsDetails.getString("picture_description") != null) {
						imageDescription.setText(newsDetails.getString("picture_description"));
					} else {
						imageDescription.setText("");
					}

					String imagePath = Config.SERVER_ROOT_URL+ "resources/images/applications/news_app/news/";

					newsImageView.setImageResource(R.drawable.upload_img_icon);
					imageLoader.DisplayImage(
							imagePath + newsDetails.get("picture"),
							newsImageView);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub

			}
		}, news_id);
	}

}
