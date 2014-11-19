package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sonuto.utils.custom.adapter.BlogCommentsCustomAdapter;
import com.sonuto.utils.custom.adapter.NewsCommentsCustomAdapter;
import com.sonuto.utils.custom.adapter.StatusCommentsCustomAdapter;
import com.sportzweb.JSONObjectModel.BlogComment;
import com.sportzweb.JSONObjectModel.NewsComment;
import com.sportzweb.JSONObjectModel.StatusComment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

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
				StatusComment comment = gson.fromJson(commentsJSONArr.get(i).toString(), StatusComment.class);
				statusCommentObjList.add(comment);
			}

			adapter = new StatusCommentsCustomAdapter(this, statusCommentObjList);
			commentListViewForStatus.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
