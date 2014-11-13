package com.sonuto.applications.xstreambanter;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sonuto.rpc.AppXstreamBanter;
import com.sonuto.rpc.ICallBack;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.ChatRoom;
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
	//Spinner teamSelect;
	ISessionManager session;
	ArrayList<ChatRoom> roomList = new ArrayList<ChatRoom>();
	
	ArrayList<String> teams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_join_chat_room);
		int userId;
		try{
			session = new SessionManager(getApplicationContext());
			userId = session.getUserId();
		}
		catch(NullPointerException nullEx){
			userId = 4;
		}
		gS = new Gson();
		
		matchAsJsonString = getIntent().getStringExtra("selectedMatch");
		match = gS.fromJson(matchAsJsonString, Match.class);
		
		tournamentAsJsonString = getIntent().getStringExtra("selectedTournament");
		
		generatedCode = (TextView)findViewById(R.id.genCode);
		//teamSelect = (Spinner)findViewById(R.id.spinner_teams);
		enterChatRoom = (Button)findViewById(R.id.btnenterChatRoom);
		oldCode = (TextView)findViewById(R.id.oldCode);
		previousCodes = (ListView)findViewById(R.id.previousCodelist);
		
		generatedCode.setText("Code");
		oldCode.setText("Previous Code");
		
		teams = new ArrayList<String>();
		teams.add(match.getTeam1_title());
		teams.add(match.getTeam2_title());
		
		new AppXstreamBanter().joinChatRoom(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {
				JSONObject jsonObject = (JSONObject) object;
				JSONArray jsonRoomList;
				try {
					jsonRoomList = jsonObject.getJSONArray("previous_chat_rooms");
					
					Gson gson = new Gson();
					int total_rooms = jsonRoomList.length();
					for (int i = 0; i < total_rooms; i++) {
						ChatRoom room = gson.fromJson(jsonRoomList.get(i).toString(), ChatRoom.class);
						roomList.add(room);
					}					
					ArrayAdapter<ChatRoom> chatRoomAdapter = new ArrayAdapter<ChatRoom>(JoinChatRoom.this,android.R.layout.simple_list_item_1, roomList);
					previousCodes.setAdapter(chatRoomAdapter);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			@Override
			public void callBackErrorHandler(Object object) {
				System.out.println(object);
			}
		},match.getMatch_id(), userId);
		
		enterChatRoom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent i = new Intent(getApplicationContext(), ChatRooms.class);
	    		i.putExtra("selectedMatch",matchAsJsonString);		    		
	    		i.putExtra("selectedTournament",tournamentAsJsonString);	    		
	    		startActivity(i);
			}
		});
	}			
}

