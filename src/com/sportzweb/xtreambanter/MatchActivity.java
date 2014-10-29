package com.sportzweb.xtreambanter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.Matches;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;
import com.sportzweb.JSONObjectModel.Tournament;
import com.sportzweb.R.id;
import com.sportzweb.R.layout;

public class MatchActivity extends Activity{


	Gson gS;
	TextView today;
	TextView matchText;
	TextView matchTime;
	Button createChatRoom;
	Button joinChatRoom;
	Match match;
	String matchAsJsonString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_create_join_chat_room);	
		
		gS = new Gson();
		
		Date date = new Date();
		
		matchAsJsonString = getIntent().getStringExtra("selectedMatch");
		match = gS.fromJson(matchAsJsonString, Match.class);
		today = (TextView)findViewById(R.id.today);
		matchText = (TextView)findViewById(R.id.match);
		matchTime = (TextView)findViewById(R.id.match_time);
		createChatRoom = (Button)findViewById(R.id.btnCreateChatRoom);
		joinChatRoom = (Button)findViewById(R.id.btnJoinChatRoom);
		
		Date curDate = new Date(0);
		today.setText(DateFormat.getDateInstance().format(curDate).toString());
		matchText.setText(match.toString());
		matchTime.setText(match.getTime());
		//getAndSetLiistInSpinner();
		createChatRoom.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {					
	    		final Intent i = new Intent(getApplicationContext(), MatchActivity.class);
	    		i.putExtra("selectedMatch",matchAsJsonString);		    		
	    		startActivity(i);
			}
		});
		
		joinChatRoom.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {					
	    		final Intent i = new Intent(getApplicationContext(), JoinChatRoom.class);
	    		i.putExtra("selectedMatch",matchAsJsonString);		    		
	    		startActivity(i);
			}
		});
		
	}
			
}
