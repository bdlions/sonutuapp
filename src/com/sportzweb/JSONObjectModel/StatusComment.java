package com.sportzweb.JSONObjectModel;

import org.json.JSONObject;


public class StatusComment {
	private String id;
	private JSONObject user_info;
	private String created_on;
	private String description;
	public JSONObject getUser_info() {
		return user_info;
	}
	public void setUser_info(JSONObject user_info) {
		this.user_info = user_info;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCreated_on() {
		return created_on;
	}
	
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

	
}
