package com.sportzweb.JSONObjectModel;

public class Sport {
	
	String title;
	int id;
	
	public int getId() {
		return id;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}	
	
	public String toString()
    {
        return title;
    }

}
