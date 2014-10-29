package com.sportzweb.JSONObjectModel;

public class Blogs {
	private int id;
	private String title;
	private String picture;
	private String blog_category;

	public String getBlog_category() {
		return blog_category;
	}
	public void setBlog_category(String blog_category) {
		this.blog_category = blog_category;
	}
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
}
