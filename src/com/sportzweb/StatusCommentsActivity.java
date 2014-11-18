package com.sportzweb;
import org.json.JSONArray;
import org.json.JSONException;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sonuto.utils.custom.adapter.BlogCommentsCustomAdapter;
import com.sportzweb.JSONObjectModel.BlogComment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_comments);
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
		
	}

}
