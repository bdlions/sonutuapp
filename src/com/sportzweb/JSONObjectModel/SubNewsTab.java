package com.sportzweb.JSONObjectModel;

import org.json.JSONArray;

import com.google.gson.JsonArray;

public class SubNewsTab {

	private int id;
	private String title;
	private JsonArray news_list;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public JsonArray getNews_list() {
		return news_list;
	}
	public void setNews_list(JsonArray news_list) {
		this.news_list = news_list;
	}


}
