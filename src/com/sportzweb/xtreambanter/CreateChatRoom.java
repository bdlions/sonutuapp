package com.sportzweb.xtreambanter;
import java.util.ArrayList;
import java.util.Iterator;

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
import com.sonuto.rpc.register.CreateRoom;
import com.sonuto.rpc.register.JoinRoom;
import com.sonuto.session.ISessionManager;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;

public class CreateChatRoom extends Activity{


	Gson gS;
	TextView generatedCode;
	Button enterChatRoom;
	Match match;
	String matchAsJsonString;
	String tournamentAsJsonString;
	Spinner teamSelect;
	
	ArrayList<String> teams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_create_chat_room);	
		
		gS = new Gson();
		
		matchAsJsonString = getIntent().getStringExtra("selectedMatch");
		match = gS.fromJson(matchAsJsonString, Match.class);
		
		tournamentAsJsonString = getIntent().getStringExtra("selectedTournament");
		
		generatedCode = (TextView)findViewById(R.id.genCode);
		teamSelect = (Spinner)findViewById(R.id.spinner_teams);
		enterChatRoom = (Button)findViewById(R.id.btnenterChatRoom);
		
		generatedCode.setText("Code");
		
		teams = new ArrayList<String>();
		teams.add(match.getTeam1_title());
		teams.add(match.getTeam2_title());
		
		CreateRoom createChatRoom = new CreateRoom();
		 JSONObject jsonObject = new JSONObject();
		 createChatRoom.getGeneratedCode(new ICallBack() {
				@Override
				public void callBackResultHandler(final Object object) {
					JSONObject jsonObject = (JSONObject) object;
					Iterator iter = jsonObject.keys();
					 while(iter.hasNext()){
					   String key = (String)iter.next();
					   if(key.equals("group_access_code"))
						try {
							generatedCode.setText("Group Access Code: "+jsonObject.getString(key));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				@Override
				public void callBackErrorHandler(Object object) {
					// TODO Auto-generated method stub
					System.out.println(object);
				}
			}, gS.toJson(match));
		
		ArrayAdapter<String> teamAdapter = new ArrayAdapter<String>(CreateChatRoom.this,android.R.layout.simple_list_item_1, teams);
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

