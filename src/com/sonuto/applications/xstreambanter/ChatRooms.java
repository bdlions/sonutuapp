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
import com.google.gson.JsonSyntaxException;
import com.sonuto.Config;
import com.sonuto.nodejs.MessageInfoXB;
import com.sonuto.nodejs.UserInfoXB;
import com.sonuto.rpc.AppXstreamBanter;
import com.sonuto.rpc.ICallBack;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.users.UserInfo;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;
import com.sportzweb.JSONObjectModel.Message;
import com.sportzweb.JSONObjectModel.Tournament;

public class ChatRooms extends Activity {

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
	String allMessages = "";
	Date d;
	SocketIO socket;

	ArrayList<String> teams;
	ArrayList<Message> messageList;
	int userId;
	int roomId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_chat_history);
		try {
			userId = SessionManager.getInstance().getUserId();
		} catch (NullPointerException nullEx) {
			userId = 4;
		}

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
					try {
						roomId = getIntent().getIntExtra("roomId", 0);
					} catch (Exception ex) {

					}
					userInfo.setRoomId(roomId);
					Gson gson = new Gson();
					socket.emit("adduser", gson.toJson(userInfo));
				}

				@Override
				public void on(String event, IOAcknowledge ack,
						final Object... params) {
					try {
						JSONObject jsonObject = new JSONObject(params[0]
								.toString());
						String messageText = jsonObject.get("messageInfo")
								.toString();

						Gson gson = new Gson();
						final MessageInfoXB messageInfo = gson.fromJson(
								messageText, MessageInfoXB.class);

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								//messageHistory.append("("
								//		+ messageInfo.getTime() + ")\n"
								//		+ messageInfo.getMessage() + "\n\n");
								messageHistory.setText(messageInfo.toString()+messageHistory.getText());
								//messageHistory.append(messageInfo.toString());
							}
						});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gS = new Gson();

		roomId = getIntent().getIntExtra("roomId", 0);

		matchAsJsonString = getIntent().getStringExtra("selectedMatch");
		match = gS.fromJson(matchAsJsonString, Match.class);

		tournamentAsJsonString = getIntent().getStringExtra(
				"selectedTournament");
		tournament = gS.fromJson(tournamentAsJsonString, Tournament.class);
		tournamentName = (TextView) findViewById(R.id.tournamentName);
		tournamentName.setText(tournament.toString());
		today = (TextView) findViewById(R.id.today);

		matchName = (TextView) findViewById(R.id.match);
		matchName.setText(match.toString());

		String currentDateTimeString = DateFormat.getDateInstance().format(
				new Date());

		// textView is the TextView view that should display it
		today.setText(currentDateTimeString);

		messageHistory = (TextView) findViewById(R.id.chat_history);
		message = (EditText) findViewById(R.id.chat_edt_text);
		sendText = (Button) findViewById(R.id.btnSendChatRoom);

		teams = new ArrayList<String>();
		teams.add(match.getTeam1_title());
		teams.add(match.getTeam2_title());

		JSONArray messagesList;
		try {
			messagesList = new JSONArray(getIntent().getStringExtra(
					"chatRoomMessageList"));
			Gson gson = new Gson();
			int total_messages = messagesList.length();
			for (int i = 0; i < total_messages; i++) {
				Message message = gson.fromJson(messagesList.get(i).toString(),
						Message.class);
				allMessages += message.toString();
			}
			messageHistory.setText(allMessages);
			messageHistory.setMovementMethod(new ScrollingMovementMethod());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		sendText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (userId > 0 && roomId > 0) {
					MessageInfoXB messageInfo = new MessageInfoXB();
					messageInfo.setUserId(userId);
					messageInfo.setRoomId(roomId);
					messageInfo.setMessage(message.getText().toString());
					Gson gson = new Gson();
					socket.emit("sendmessage", gson.toJson(messageInfo));
					message.setText("");
				}

			}
		});
	}
}
