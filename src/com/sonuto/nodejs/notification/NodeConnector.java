package com.sonuto.nodejs.notification;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONObject;

import com.bdlions.io.socket.IOAcknowledge;
import com.bdlions.io.socket.IOCallback;
import com.bdlions.io.socket.SocketIO;
import com.bdlions.io.socket.SocketIOException;
import com.google.gson.Gson;
import com.sonuto.Config;
import com.sonuto.session.SessionManager;

public class NodeConnector implements MessageObservable{

	private static ArrayList<MessageObserver> observers;
	private static NodeConnector instance = null;
	private static NodeEvent nodeEvent;
	
	private static String message;
	private static String sender;
	private static SocketIO socket;
	
	protected NodeConnector(){
		observers = new ArrayList<MessageObserver>();
	}
	
	public static NodeConnector getInstance(){
		if(instance == null){
			instance = new NodeConnector();
			try {
				socket = new SocketIO(Config.SOCKET_SERVER_URL);
				socket.connect(new IOCallback() {
					
					@Override
					public void onMessage(JSONObject json, IOAcknowledge ack) {
						
					}
					
					@Override
					public void onMessage(String data, IOAcknowledge ack) {
						
					}
					
					@Override
					public void onError(SocketIOException socketIOException) {
						
					}
					
					@Override
					public void onDisconnect() {
						
					}
					
					@Override
					public void onConnect() {
						Gson gson = new Gson();
						socket.emit("adduser", gson.toJson(SessionManager.getInstance().getUserInfo()));
					}
					
					@Override
					public void on(String event, IOAcknowledge ack, Object... args) {
						message = null;
						sender = null;
						
						for(NodeEvent evt : NodeEvent.class.getEnumConstants()){
							if(evt.name.equalsIgnoreCase(event)){
								nodeEvent = evt;
							}
						}
						if(args.length >= 2){
							message = args[ 1 ].toString();
							sender = args[ 0 ].toString();
						}
						else if(args.length >= 1){
							message = args[ 1 ].toString();
						}
						getInstance().notifyObservers();
					}
				});
			} catch (MalformedURLException e) {

			}
		}
		return instance;
	}
	
	@Override
	public void registerObserver(MessageObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(MessageObserver observer) {
		int index = observers.indexOf(observer);
		if(index >= 0){
			observers.remove(index);
		}
	}

	@Override
	public void notifyObservers() {
		for(int i = 0; i < observers.size(); i ++){
			observers.get(i).onUpdate(nodeEvent, sender, message);
		}
	}

}
