package com.sportzweb.JSONObjectModel;

public class Match {
	
	int id; 
	String time; 
	String team1_title; 
	String date;  
	String team2_title; 
	int match_id;
	


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getTeam1_title() {
		return team1_title;
	}



	public void setTeam1_title(String team1_title) {
		this.team1_title = team1_title;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getTeam2_title() {
		return team2_title;
	}



	public void setTeam2_title(String team2_title) {
		this.team2_title = team2_title;
	}



	public int getMatch_id() {
		return match_id;
	}



	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}



	public String toString()
    {
        return getTeam1_title()+"    vs     "+getTeam2_title();
    }

}
