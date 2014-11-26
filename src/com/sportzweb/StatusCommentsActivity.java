package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.StatusFeed;
import com.sonuto.session.SessionManager;
import com.sonuto.utils.custom.adapter.StatusCommentsCustomAdapter;
import com.sportzweb.JSONObjectModel.StatusComment;

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
import android.widget.Toast;

public class StatusCommentsActivity extends Activity {
	EditText commentForStatusText;
	ImageView blogImageView;
	public ImageLoader imageLoader;
	Button btnForCommentInStatus;
	Context context;
	// process dialer
	ProgressDialog pDialog;
	ListView commentListViewForStatus;
	int status_id;
	String comments, userComments;
	JSONArray commentsJSONArr;
	private StatusCommentsCustomAdapter adapter;
	private ArrayList<StatusComment> statusCommentObjList = new ArrayList<StatusComment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_comments);
		adapter = new StatusCommentsCustomAdapter(this, statusCommentObjList);
		context = this;
		initUi();
		Process();
	}

	private void initUi() {
		commentForStatusText = (EditText) findViewById(R.id.commentForStatusText);
		btnForCommentInStatus = (Button) findViewById(R.id.btnForCommentInStatus);
		commentListViewForStatus = (ListView) findViewById(R.id.commentListViewForStatus);
	}

	private void Process() {
		Intent intent = getIntent();
		status_id = intent.getIntExtra("status_id", 0);
		comments = intent.getStringExtra("statusComments");
		try {
			commentsJSONArr = new JSONArray(comments);
			Gson gson = new Gson();
			int total_comments = commentsJSONArr.length();
			for (int i = 0; i < total_comments; i++) {
				String commentObj = commentsJSONArr.get(i).toString();
				StatusComment comment = gson.fromJson(commentObj, StatusComment.class);
				JSONObject userInfo = new JSONObject( commentObj).getJSONObject("user_info");
				comment.setUser_info(userInfo);
				
				statusCommentObjList.add(comment);
			}

			adapter = new StatusCommentsCustomAdapter(this, statusCommentObjList);
			commentListViewForStatus.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		btnForCommentInStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				JSONObject jsonStatusCommentObj = new JSONObject();
				userComments = commentForStatusText.getText().toString();
				if(isVerifiedCommentTextStep()) {
					try {
						int userId = SessionManager.getInstance().getUserId();
						jsonStatusCommentObj.put("user_id", userId);
						jsonStatusCommentObj.put("status_id", status_id);
						jsonStatusCommentObj.put("feedback", userComments);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					
					pDialog = new ProgressDialog(context);
					pDialog.setMessage("Submitting comment and Fetching data..");
					pDialog.setCancelable(false);
					pDialog.show();
					
					StatusFeed statusFeed = new StatusFeed();	
					statusFeed.postStatusComment(new ICallBack() {

						@Override
						public void callBackResultHandler(Object object) {
							pDialog.dismiss();
							JSONObject statusCommentJSONObject = (JSONObject) object;
								JSONObject statusCommentInfoArray;
								try {
									statusCommentInfoArray = statusCommentJSONObject.getJSONObject("comment_info");
									Gson gson = new Gson();
									
									StatusComment statusInfo = gson.fromJson(statusCommentInfoArray.toString(), StatusComment.class);
									statusCommentObjList.add(statusInfo);
									adapter.notifyDataSetChanged();
									commentForStatusText.setText("");
								} catch (JSONException e) {
									e.printStackTrace();
								}
						}

						@Override
						public void callBackErrorHandler(Object object) {

						}
					}, jsonStatusCommentObj.toString()); 
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
			Toast.makeText(context, getString(R.string.commentRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

}
