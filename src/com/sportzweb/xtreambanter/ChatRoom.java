package com.sportzweb.xtreambanter;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.Gson;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;

public class ChatRoom extends Activity{


	Gson gS;
	TextView tournamentName;
	TextView today;
	TextView matchName;
	EditText message;
	Button sendText;
	Match match;
	String matchAsJsonString;
	String tournamentAsJsonString;
	
	ArrayList<String> teams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_chat_history);	
		
		gS = new Gson();
		
		matchAsJsonString = getIntent().getStringExtra("selectedMatch");
		match = gS.fromJson(matchAsJsonString, Match.class);
		
		tournamentAsJsonString = getIntent().getStringExtra("selectedTournament");
		
		tournamentName = (TextView)findViewById(R.id.tournamentName);
		today = (TextView)findViewById(R.id.today);
		
		matchName = (TextView)findViewById(R.id.match);
		matchName.setText(match.toString());
		
		Date d = new Date();
		today.setText(d.toString());
		
		sendText = (Button)findViewById(R.id.btnSendChatRoom);
		
		
		
		teams = new ArrayList<String>();
		teams.add(match.getTeam1_title());
		teams.add(match.getTeam2_title());
		
		ArrayAdapter<String> teamAdapter = new ArrayAdapter<String>(ChatRoom.this,android.R.layout.simple_list_item_1, teams);
		// bind adapter and view
		//getAndSetLiistInSpinner();
		sendText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
					
	    		final Intent i = new Intent(getApplicationContext(), MatchActivity.class);
	    		i.putExtra("selectedMatch",matchAsJsonString);		    		
	    		startActivity(i);
			}
		});
	}			
}

