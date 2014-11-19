package com.sportzweb.JSONObjectModel;

public class UserInfoInComments {
	private int user_id;
	private int comment_id;
	private String comment;
	private String comment_created_on;
	private String first_name;
	private String last_name;
	private String photo;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment_created_on() {
		return comment_created_on;
	}

	public void setComment_created_on(String comment_created_on) {
		this.comment_created_on = comment_created_on;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
}
