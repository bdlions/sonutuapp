package com.sonuto.applications.xstreambanter;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bdlions.io.socket.IOAcknowledge;
import com.bdlions.io.socket.IOCallback;
import com.bdlions.io.socket.SocketIO;
import com.bdlions.io.socket.SocketIOException;
import com.google.gson.Gson;
import com.sonuto.Config;
import com.sonuto.nodejs.MessageInfoXB;
import com.sonuto.nodejs.UserInfoXB;
import com.sonuto.rpc.AppXstreamBanter;
import com.sonuto.rpc.ICallBack;
import com.sonuto.session.SessionManager;
import com.sonuto.users.UserInfo;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;
import com.sportzweb.JSONObjectModel.Message;
import com.sportzweb.JSONObjectModel.Tournament;

public class ChatRooms extends Activity{


	Gson gS;
	TextView tournamentName;
	TextView today;
	TextView matchName;
	EditText message;
	TextView messageHistory;
	Button sendText;
	Match match;
	Tournament tournament;
	String matchAsJsonString;
	String tournamentAsJsonString;
	String allMessages="";
	Date d;
	SocketIO socket;
	
	ArrayList<String> teams;
	ArrayList<Message> messageList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_chat_history);	
		
		
		try {
			socket = new SocketIO(Config.SOCKET_SERVER_URL);

			socket.connect(new IOCallback() {

				@Override
				public void onMessage(JSONObject arg0, IOAcknowledge arg1) {
					// TODO Auto-generated method stub
					System.out.println(arg0);
				}

				@Override
				public void onMessage(String arg0, IOAcknowledge arg1) {
					// TODO Auto-generated method stub
					System.out.println(arg0);
				}

				@Override
				public void onError(SocketIOException arg0) {
					// TODO Auto-generated method stub
					System.out.println(arg0);
				}

				@Override
				public void onDisconnect() {
					// TODO Auto-generated method stub
					System.out.println();
				}

				@Override
				public void onConnect() {
					// TODO Auto-generated method stub
					UserInfoXB userInfo = new UserInfoXB();
					userInfo.setRoomId(12);
					Gson gson = new Gson();
					socket.emit("adduser", gson.toJson(userInfo));
				}

				@Override
				public void on(String event, IOAcknowledge ack, final Object... params) {
					// TODO Auto-generated method stub
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Gson gson = new Gson();
							MessageInfoXB messageInfo = gson.fromJson(params[0].toString(), MessageInfoXB.class);
							messageHistory.append("("+messageInfo.getTime()+")\n"+messageInfo.getMessage()+"\n\n");	
						}
					});
				}
			});

			// This line is cached until the connection is establisched.
			//socket.send("Hello Server!");
			//socket.emit("sendChat", "alamgir");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gS = new Gson();
		
		matchAsJsonString = getIntent().getStringExtra("selectedMatch");
		match = gS.fromJson(matchAsJsonString, Match.class);
		
		tournamentAsJsonString = getIntent().getStringExtra("selectedTournament");
		tournament = gS.fromJson(tournamentAsJsonString, Tournament.class);
		tournamentName = (TextView)findViewById(R.id.tournamentName);
		tournamentName.setText(tournament.toString());
		today = (TextView)findViewById(R.id.today);
		
		matchName = (TextView)findViewById(R.id.match);
		matchName.setText(match.toString());
		
		String currentDateTimeString = DateFormat.getDateInstance().format(new Date());

		// textView is the TextView view that should display it
		today.setText(currentDateTimeString);
		
		messageHistory = (TextView)findViewById(R.id.chat_history);
		message = (EditText)findViewById(R.id.chat_edt_text);
		sendText = (Button)findViewById(R.id.btnSendChatRoom);
		
		
		
		teams = new ArrayList<String>();
		teams.add(match.getTeam1_title());
		teams.add(match.getTeam2_title());
		
		new AppXstreamBanter().storeChatRoomMap(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {				
				JSONObject jsonObject = (JSONObject) object;
				if(jsonObject != null){
					JSONArray messagesList;
					try {
						
						messagesList = jsonObject.getJSONArray("chat_room_message_list");
						if(messagesList != null){
							Gson gson = new Gson();
							int total_messages = messagesList.length();
							for (int i = 0; i < total_messages; i++) {
								Message message = gson.fromJson(messagesList.get(i).toString(), Message.class);
								//messageList.add(message);
								allMessages+=message.toString();
							}
							
							//ArrayAdapter<Message> matchesAdapter = new ArrayAdapter<Message>(ChatRooms.this,android.R.layout.simple_list_item_1, messageList);
							messageHistory.setText(allMessages);
							messageHistory.setMovementMethod(new ScrollingMovementMethod());
						}
					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Iterator iter = jsonObject.keys();
					while (iter.hasNext()) {
						String key = (String) iter.next();
						System.out.println(key);
						
					}
				}
			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				
				System.out.println(object);
			}
		});
		
		//ArrayAdapter<String> teamAdapter = new ArrayAdapter<String>(ChatRooms.this,android.R.layout.simple_list_item_1, teams);
		// bind adapter and view
		//getAndSetLiistInSpinner();
		sendText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());	
	    		//messageHistory.append("("+currentDateTimeString+")\n"+message.getText()+"\n\n");		    		
	    		//startActivity(i);
				//socket.emit("sendmessage", "");
				
				MessageInfoXB messageInfo = new MessageInfoXB();
				messageInfo.setUserId(4);
				messageInfo.setRoomId(12);
				messageInfo.setMessage(message.getText().toString());
				
				Gson gson = new Gson();
				socket.emit("sendmessage", gson.toJson(messageInfo));
			}
		});
	}			
}

