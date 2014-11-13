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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rpc.applications.xstreambanter.CreateRoom;
import com.rpc.applications.xstreambanter.JoinRoom;
import com.sonuto.rpc.AppXstreamBanter;
import com.sonuto.rpc.ICallBack;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;
import com.sportzweb.JSONObjectModel.Tournament;

public class CreateChatRoom extends Activity {

	Gson gS;
	TextView generatedCode;
	Button enterChatRoom;
	Match match;
	String matchAsJsonString;
	String tournamentAsJsonString;
	Spinner teamSelect;
	ISessionManager session;
	ArrayList<String> teams;
	ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_create_chat_room);
		int userId;
		try {
			session = new SessionManager(getApplicationContext());
			userId = session.getUserId();
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

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Creating a chat room..");
		pDialog.setCancelable(false);
		pDialog.show();  
		new AppXstreamBanter().createChatRoom(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {
				pDialog.dismiss();
				JSONObject jsonObject = (JSONObject) object;
				Iterator iter = jsonObject.keys();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					if (key.equals("group_access_code"))
						try {
							generatedCode.setText("Group Access Code: "
									+ jsonObject.getString(key));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				pDialog.dismiss();
				System.out.println(object);
			}
		}, match.getMatch_id(), userId);

		ArrayAdapter<String> teamAdapter = new ArrayAdapter<String>(
				CreateChatRoom.this, android.R.layout.simple_list_item_1, teams);
		// bind adapter and view
		teamSelect.setAdapter(teamAdapter);
		// getAndSetLiistInSpinner();
		enterChatRoom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				getChatRoomMessages();
				/*final Intent i = new Intent(getApplicationContext(),
						ChatRooms.class);
				i.putExtra("selectedMatch", matchAsJsonString);
				i.putExtra("selectedTournament", tournamentAsJsonString);
				startActivity(i);*/
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
					startActivity(i);
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
		});		 
	}
}
