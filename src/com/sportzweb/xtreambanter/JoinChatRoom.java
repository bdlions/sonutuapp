package com.sportzweb.xtreambanter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;
import com.sportzweb.JSONObjectModel.Sport;

public class JoinChatRoom extends Activity{


	Gson gS;
	TextView generatedCode;
	TextView oldCode;
	Button enterChatRoom;
	Match match;
	String matchAsJsonString;
	ListView previousCodes;
	Spinner teamSelect;
	
	ArrayList<String> teams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_join_chat_room);	
		
		gS = new Gson();
		
		matchAsJsonString = getIntent().getStringExtra("selectedMatch");
		match = gS.fromJson(matchAsJsonString, Match.class);
		
		generatedCode = (TextView)findViewById(R.id.genCode);
		teamSelect = (Spinner)findViewById(R.id.spinner_teams);
		enterChatRoom = (Button)findViewById(R.id.btnenterChatRoom);
		oldCode = (TextView)findViewById(R.id.oldCode);
		previousCodes = (ListView)findViewById(R.id.previousCodelist);
		
		generatedCode.setText("Code");
		oldCode.setText("Previous Code");
		
		teams = new ArrayList<String>();
		teams.add(match.getTeam1_title());
		teams.add(match.getTeam2_title());
		
		ArrayAdapter<String> teamAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, teams);
		// bind adapter and view
		teamSelect.setAdapter(teamAdapter);
		//getAndSetLiistInSpinner();
		enterChatRoom.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
					
	    		final Intent i = new Intent(getApplicationContext(), MatchActivity.class);
	    		i.putExtra("selectedMatch",matchAsJsonString);		    		
	    		startActivity(i);
			}
		});
	}			
}

