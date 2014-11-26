package com.sonuto.nodejs.notification;
public enum NodeEvent {
	UPDATE_CHAT("updatechat"),
	UPDATE_MSG("updatemessages"),
	UPDATE_MSG_CHAT("updatemessagecchat");
	
	public final String name;
	NodeEvent(String eventName){
		this.name = eventName;
	}
	public String toString(){
		return name;
	}
}
