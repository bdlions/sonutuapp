package com.sportzweb.xtreambanter;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;

public class CreateChatRoom extends Activity{


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
					
		    		final Intent i = new Intent(getApplicationContext(), MatchActivity.class);
		    		i.putExtra("selectedMatch",matchAsJsonString);		    		
		    		startActivity(i);
	        	
				
			}
		});
		
	}
			
}
