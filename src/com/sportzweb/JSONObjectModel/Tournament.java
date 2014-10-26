package com.sportzweb.JSONObjectModel;

public class Tournament {
	
	int id;
	int sports_id;
	String title;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSports_id() {
		return sports_id;
	}
	public void setSports_id(int sports_id) {
		this.sports_id = sports_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String toString()
    {
        return title;
    }

}
