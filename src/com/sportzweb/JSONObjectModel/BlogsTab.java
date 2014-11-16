package com.sportzweb.JSONObjectModel;


import com.google.gson.JsonArray;

public class BlogsTab {

	private int id;
	private String title;
	private JsonArray blog_list;
	
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
	public JsonArray getBlog_list() {
		return blog_list;
	}
	public void setBlog_list(JsonArray blog_list) {
		this.blog_list = blog_list;
	}


}
