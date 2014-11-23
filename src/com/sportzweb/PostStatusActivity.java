package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.StatusFeed;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.users.AppID;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PostStatusActivity extends Activity {
	EditText statusText;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	Button btnForStatusPost;
	public ImageLoader imageLoader;
	private Context mContext;
	ISessionManager session;
	int userId;
	String userStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_status);
		this.mContext = this;
		// Session Manager
		this.session = new SessionManager(getApplicationContext());
		this.userId = this.session.getUserId();
		initUI();
		
	}

	private void initUI() {
		statusText = (EditText) findViewById(R.id.statusText);
		btnForStatusPost = (Button) findViewById(R.id.btnForStatusPost);
		btnForStatusPost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				StatusFeed postStatus = new StatusFeed();
				JSONObject jsonStatusCommentObj = new JSONObject();
				userStatus = statusText.getText().toString();
				if(isVerifiedPostStatusTextStep()) {
					try {
						int userId = session.getUserId();
						jsonStatusCommentObj.put("user_id", userId);
						jsonStatusCommentObj.put("mapping_id", userId);
						jsonStatusCommentObj.put("status_type_id", 1);
						jsonStatusCommentObj.put("status_category_id", 1);
						jsonStatusCommentObj.put("description", userStatus);
						jsonStatusCommentObj.put("reference_id", 1);
						jsonStatusCommentObj.put("shared_type_id", 1);
						jsonStatusCommentObj.put("via_user_id", 1);
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				
				postStatus.postStatus(new ICallBack() {
					
					@Override
					public void callBackResultHandler(Object object) {
						
						final JSONObject jsonUserObj = (JSONObject) object;
						System.out.println(jsonUserObj);
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
	public boolean isVerifiedPostStatusTextStep() {
		if (userStatus.length() == 0) {
			Toast.makeText(mContext, getString(R.string.commentRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}
	
	
}
