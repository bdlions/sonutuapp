package com.sonuto.share;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.Share;
import com.sonuto.session.SessionManager;
import com.sonutu.constants.STATUS_CATEGORY;
import com.sonutu.constants.STATUS_TYPE;
import com.sportzweb.PostStatusActivity;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.StatusInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StatusShareActivity extends Activity{
	EditText etStatusShareText;
	Button btnStatusShare;
	ProgressDialog pDialog;
	String statusShareText;
	@Override
	public void onCreate(Bundle instanceState) 
	{
		super.onCreate(instanceState);
		setContentView(R.layout.activity_status_share);
		initUI();
	}
	
	private void initUI()
    {
		etStatusShareText = (EditText) findViewById(R.id.etStatusShareText);
		btnStatusShare = (Button) findViewById(R.id.btnStatusShare);
		btnStatusShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pDialog = new ProgressDialog(StatusShareActivity.this);
				pDialog.setMessage("Sharing status..");
				pDialog.setCancelable(false);
				pDialog.show();
				
				int status_type_id = getIntent().getIntExtra("status_type_id", STATUS_TYPE.GENERAL.getValue());
				int status_category_id = getIntent().getIntExtra("status_category_id", STATUS_CATEGORY.NEWSFEED.getValue());
				int mapping_id = getIntent().getIntExtra("mapping_id", 0);
				int reference_id = getIntent().getIntExtra("reference_id", 0);
				int shared_type_id = getIntent().getIntExtra("shared_type_id", 1);
				
				JSONObject params = new JSONObject();
				try
				{
					statusShareText = etStatusShareText.getText().toString();
					int userId = SessionManager.getInstance().getUserId();
					params.put("user_id", userId);
					params.put("mapping_id", mapping_id);
					params.put("status_type_id", status_type_id);
					params.put("status_category_id", status_category_id);
					params.put("description", statusShareText);
					params.put("reference_id", reference_id);
					params.put("shared_type_id", shared_type_id);
				}
				catch(JSONException e)
			    {
					
			    }
				
				new Share().shareStatus(new ICallBack() {
					
					@Override
					public void callBackResultHandler(Object object) {
						// TODO Auto-generated method stub
						JSONObject jsonObject = (JSONObject) object;
						Gson gson = new Gson();
						StatusInfo statusInfo = new StatusInfo();
						try
					    {
							if(jsonObject.get("status").toString().equalsIgnoreCase("1"))
							{
								statusInfo = gson.fromJson(jsonObject.get("status_info").toString(), StatusInfo.class);
							}
					    }
						catch(JSONException jsonException)
					    {
							System.out.println(jsonException.toString());
					    }
						getIntent().putExtra("statusInfo", gson.toJson(statusInfo));
						setResult(RESULT_OK, getIntent());        
						finish();
						pDialog.dismiss();
					}
					
					@Override
					public void callBackErrorHandler(Object object) {
						// TODO Auto-generated method stub
						pDialog.dismiss();
					}
				}, params.toString());
			}
		});
    }
}
