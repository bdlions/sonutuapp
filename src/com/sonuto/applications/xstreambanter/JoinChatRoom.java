package com.sonuto.applications.xstreambanter;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.sonuto.rpc.AppXstreamBanter;
import com.sonuto.rpc.ICallBack;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.ChatRoom;
import com.sportzweb.JSONObjectModel.Match;

public class JoinChatRoom extends Activity{

	String chatRoomCode = "";
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
	ProgressDialog pDialog;
	ArrayList<String> teams;
	int userId;
	String roomMapExists = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_join_chat_room);		
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
				chatRoomCode = generatedCode.getText().toString();				
				enterChatRoom();
			}
		});
		
		previousCodes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(roomList.size()>0){
					Intent i = new Intent(getApplicationContext(), MatchActivity.class);
					ChatRoom room = roomList.get(position);
					chatRoomCode = room.getGroup_access_code();
					enterChatRoom();
	        	}				
			}
		});
	}	
	
	private void enterChatRoom() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Entering into the chat room..");
		pDialog.setCancelable(false);
		pDialog.show();  
		new AppXstreamBanter().enterChatRoom(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {
				pDialog.dismiss();
				JSONObject jsonObject = (JSONObject) object;
				/*try {
					matchList = jsonObject.getJSONArray("match_list");
					
					Gson gson = new Gson();
					int total_matches = matchList.length();
					for (int i = 0; i < total_matches; i++) {
						Match match = gson.fromJson(matchList.get(i).toString(), Match.class);
						matchesList.add(match);
					}
					
					ArrayAdapter<Match> matchesAdapter = new ArrayAdapter<Match>(MatchesActivity.this,android.R.layout.simple_list_item_1, matchesList);
					listView.setAdapter(matchesAdapter);
				} 
				catch (JSONException e) {
					//give proper error message to the client
					e.printStackTrace();
				}
				catch(NullPointerException npe){
					//give proper error message to the client
					npe.printStackTrace();
				}*/
				int roomId = 0;
				Iterator iter = jsonObject.keys();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					String value = "";
					try {
						value = jsonObject.getString(key);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (key.equals("room_map_exists"))
					{
						roomMapExists = value;				
					}
					else if (key.equals("chat_room_id"))
					{
						roomId = Integer.parseInt(value);				
					}
				}
				if(roomMapExists.equals("1") && roomId > 0)
				{
					JSONArray chatRoomMessageList;
					try {
						chatRoomMessageList = jsonObject.getJSONArray("chat_room_message_list");
						final Intent i = new Intent(getApplicationContext(),
								ChatRooms.class);
						i.putExtra("selectedMatch", matchAsJsonString);
						i.putExtra("selectedTournament", tournamentAsJsonString);
						i.putExtra("chatRoomMessageList", chatRoomMessageList.toString());
						i.putExtra("roomId", roomId);
						startActivity(i);
						finish();
					} 
					catch (JSONException e) {
						//give proper error message to the client
						e.printStackTrace();
					}
					catch(NullPointerException npe){
						//give proper error message to the client
						npe.printStackTrace();
					}
				}
				else
				{
					JSONArray teamList;
					try {
						teamList = jsonObject.getJSONArray("team_list");
						final Intent i = new Intent(getApplicationContext(),
								 MapTeamChatRoom.class);
						i.putExtra("selectedMatch", matchAsJsonString);
						i.putExtra("selectedTournament", tournamentAsJsonString);
						i.putExtra("teamList", teamList.toString());
						i.putExtra("roomId", roomId);
						startActivity(i);
						finish();
					} 
					catch (JSONException e) {
						//give proper error message to the client
						e.printStackTrace();
					}
					catch(NullPointerException npe){
						//give proper error message to the client
						npe.printStackTrace();
					}
				}
			}
			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				pDialog.dismiss();
				System.out.println(object);
			}
		}, chatRoomCode, userId);		 
	}	
}

