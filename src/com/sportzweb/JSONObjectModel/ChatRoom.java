package com.sportzweb.JSONObjectModel;

import java.util.Date;

public class ChatRoom {
	
	int id;
	String modified_on;
	String group_access_code;
	String created_on;
	int match_id;
	int user_id;
	int xb_chat_room_id;
	int team_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModified_on() {
		return modified_on;
	}
	public void setModified_on(String modified_on) {
		this.modified_on = modified_on;
	}
	public String getGroup_access_code() {
		return group_access_code;
	}
	public void setGroup_access_code(String group_access_code) {
		this.group_access_code = group_access_code;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public int getMatch_id() {
		return match_id;
	}
	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getXb_chat_room_id() {
		return xb_chat_room_id;
	}
	public void setXb_chat_room_id(int xb_chat_room_id) {
		this.xb_chat_room_id = xb_chat_room_id;
	}
	public int getTeam_id() {
		return team_id;
	}
	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	@Override
	public String toString() {
		return group_access_code + "";
	}
	
	
	
	
	/*{"id":"12","modified_on":null,"group_access_code":"ZYSQNAVxU4","created_on":"1414610318","match_id":"1","user_id":"4","xb_chat_room_id":"12","team_id":"2"}
	{"id":"14","modified_on":null,"group_access_code":"xrkrYFWM1W","created_on":"1415004259","match_id":"1","user_id":"2","xb_chat_room_id":"14","team_id":"2"}*/
	
	

}
