package com.sportzweb.JSONObjectModel;

public class StatusInfo {

	private int status_id;
	private String description;
	private String first_name;
	private String last_name;
	private String status_created_on;
	private String photo;
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getStatus_created_on() {
		return status_created_on;
	}
	public void setStatus_created_on(String status_created_on) {
		this.status_created_on = status_created_on;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
