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
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.gson.Gson;
import com.rpc.applications.xstreambanter.CreateRoom;
import com.rpc.applications.xstreambanter.JoinRoom;
import com.sonuto.rpc.AppXstreamBanter;
import com.sonuto.rpc.ICallBack;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;
import com.sportzweb.JSONObjectModel.Message;
import com.sportzweb.JSONObjectModel.Sport;
import com.sportzweb.JSONObjectModel.Team;
import com.sportzweb.JSONObjectModel.Tournament;

public class MapTeamChatRoom extends Activity {
	ArrayList<Team> spinnerTeamList = new ArrayList<Team>();
	Gson gS;
	TextView generatedCode;
	Button enterChatRoom;
	Match match;
	String matchAsJsonString;
	String tournamentAsJsonString;
	Spinner teamSelect;
	ArrayList<String> teams;
	ProgressDialog pDialog;
	String groupAccessCode;
	int roomId;
	int userId;
	Team selectedTeam;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xstream_banter_map_team_chat_room);
		try {
			userId = SessionManager.getInstance().getUserId();
		} catch (NullPointerException nullEx) {
			userId = 4;
		}
		gS = new Gson();

		matchAsJsonString = getIntent().getStringExtra("selectedMatch");
		match = gS.fromJson(matchAsJsonString, Match.class);

		tournamentAsJsonString = getIntent().getStringExtra(
				"selectedTournament");

		generatedCode = (TextView) findViewById(R.id.genCode);
		teamSelect = (Spinner) findViewById(R.id.spinner_teams);
		enterChatRoom = (Button) findViewById(R.id.btnenterChatRoom);

		generatedCode.setText("Code");

		teams = new ArrayList<String>();
		teams.add(match.getTeam1_title());
		teams.add(match.getTeam2_title());

		/*pDialog = new ProgressDialog(this);
		pDialog.setMessage("Creating a chat room..");
		pDialog.setCancelable(false);
		pDialog.show();  
		new AppXstreamBanter().createChatRoom(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {
				pDialog.dismiss();
				JSONObject jsonObject = (JSONObject) object;
				try {
					JSONArray teamList = jsonObject.getJSONArray("team_list");
					Gson gson = new Gson();
					int total_teams = teamList.length();
					for (int i = 0; i < total_teams; i++) {
						Team team = gson.fromJson(teamList.get(i).toString(), Team.class);
						spinnerTeamList.add(team);
					}
					ArrayAdapter<Team> teamAdapter = new ArrayAdapter<Team>(
							CreateChatRoom.this, android.R.layout.simple_list_item_1, spinnerTeamList);
					// bind adapter and view
					teamSelect.setAdapter(teamAdapter);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				Iterator iter = jsonObject.keys();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					String value = "";
					try {
						value = jsonObject.getString(key);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (key.equals("group_access_code"))
					{
						groupAccessCode = value;
						generatedCode.setText("Group Access Code: "+ value);						
					}
					else if(key.equals("xb_chat_room_id"))
					{
						roomId = value;
					}
						
				}
			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				pDialog.dismiss();
				System.out.println(object);
			}
		}, match.getMatch_id(), userId);*/
		roomId = getIntent().getIntExtra("roomId", 0);
		JSONArray teamList;
		try {
			teamList = new JSONArray(getIntent().getStringExtra(
					"teamList"));
			Gson gson = new Gson();
			int totalTeams = teamList.length();
			for (int i = 0; i < totalTeams; i++) {
				Team team = gson.fromJson(teamList.get(i).toString(),
						Team.class);
				spinnerTeamList.add(team);
			}
			ArrayAdapter<Team> teamAdapter = new ArrayAdapter<Team>(
					MapTeamChatRoom.this, android.R.layout.simple_list_item_1, spinnerTeamList);
			// bind adapter and view
			teamSelect.setAdapter(teamAdapter);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		teamSelect
		.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {

				if (spinnerTeamList.size() > 0) {
					selectedTeam = spinnerTeamList.get(arg2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		enterChatRoom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getChatRoomMessages();
			}
		});
	}
	
	private void getChatRoomMessages() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Entering into the chat room...");
		pDialog.setCancelable(false);
		pDialog.show(); 
		new AppXstreamBanter().storeChatRoomMap(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {
				pDialog.dismiss();
				JSONObject jsonObject = (JSONObject) object;
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

			@Override
			public void callBackErrorHandler(Object object) {
				pDialog.dismiss();
				System.out.println(object);
			}
		}, userId, roomId, selectedTeam.getId());		 
	}
}
