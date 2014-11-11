package com.sportzweb.xtreambanter;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.JoinRoom;
import com.sonuto.rpc.register.Matches;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;

public class JoinChatRoom extends Activity{


	Gson gS;
	TextView generatedCode;
	TextView oldCode;
	Button enterChatRoom;
	Match match;
	String matchAsJsonString;
	String tournamentAsJsonString;
	ListView previousCodes;
	Spinner teamSelect;
	ISessionManager session;
	
	
	ArrayList<String> teams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_join_chat_room);
		String userId;
		try{
			session = new SessionManager(getApplicationContext());
			userId = Integer.toString(session.getUserId());
		}
		catch(NullPointerException nullEx){
			userId = "4";
		}
		gS = new Gson();
		
		matchAsJsonString = getIntent().getStringExtra("selectedMatch");
		match = gS.fromJson(matchAsJsonString, Match.class);
		
		tournamentAsJsonString = getIntent().getStringExtra("selectedTournament");
		
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
		
		String[] inputs = new String[] {"match_id:"+Integer.toString(match.getMatch_id()), "user_id:"+userId};
		JSONArray jsonArray = new JSONArray(Arrays.asList(inputs));
		System.out.println(jsonArray.toString());
		JoinRoom joinRoom = new JoinRoom();
		 JSONObject jsonObject = new JSONObject();
		 joinRoom.getPreviousCode(new ICallBack() {
				@Override
				public void callBackResultHandler(final Object object) {
					JSONObject jsonObject = (JSONObject) object;
					System.out.println(jsonObject.toString());
				}

				@Override
				public void callBackErrorHandler(Object object) {
					// TODO Auto-generated method stub
					System.out.println(object);
				}
			}, jsonArray.toString());
		
		ArrayAdapter<String> teamAdapter = new ArrayAdapter<String>(JoinChatRoom.this,android.R.layout.simple_list_item_1, teams);
		// bind adapter and view
		teamSelect.setAdapter(teamAdapter);
		//getAndSetLiistInSpinner();
		enterChatRoom.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
					
	    		final Intent i = new Intent(getApplicationContext(), ChatRoom.class);
	    		i.putExtra("selectedMatch",matchAsJsonString);		    		
	    		i.putExtra("selectedTournament",tournamentAsJsonString);	    		
	    		startActivity(i);
			}
		});
	}			
}

