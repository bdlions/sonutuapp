package com.sportzweb;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sportzweb.JSONObjectModel.StatusInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;

public class ActivityPostStatus extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);
		Button buttonPostStatus = (Button)findViewById(R.id.buttonPostStatus);
		buttonPostStatus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StatusInfo statusInfo = new StatusInfo();
				statusInfo.setDescription("Serialization is a marker interface, which implies the user cannot marshal the data according to their requirements");
				statusInfo.setFirst_name("First");
				statusInfo.setLast_name("kabir");
				statusInfo.setStatus_id(12);
				statusInfo.setLiked_user_list(new JsonArray());
				statusInfo.setPhoto("");
				statusInfo.setStatus_created_on("one seconds ago");
				statusInfo.setFeedbacks(new JsonArray());
				statusInfo.setAllow_to_delete(true);
				Gson gson = new Gson();
				
				
				getIntent().putExtra("statusInfo", gson.toJson(statusInfo));
				setResult(RESULT_OK, getIntent());        
				finish();
			}
		});
		
		
	}
}
