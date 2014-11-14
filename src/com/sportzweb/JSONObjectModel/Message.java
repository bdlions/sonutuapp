package com.sportzweb.JSONObjectModel;

public class Message {

	String message;
	String last_name;
	String first_name;
	String time;
	int created_on;
	String team_name;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getCreated_on() {
		return created_on;
	}
	public void setCreated_on(int created_on) {
		this.created_on = created_on;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	@Override
	public String toString() {
		return "(" + time + ")\n" + last_name
				+ "-" + team_name + ":" + message+"\n\n";
	}
	
	
	
}
