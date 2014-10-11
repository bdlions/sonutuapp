package com.sonuto.users;

import com.google.gson.JsonArray;

public class BusinessCategory {
	
	private int id;
	private String description;
	private JsonArray sub_type_list;
	
	public void setDescription(String description) {
		this.description = description;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public int getId() {
		return id;
	}
	
	public JsonArray getSub_type_list() {
		return sub_type_list;
	}
	public void setSub_type_list(JsonArray sub_type_list) {
		this.sub_type_list = sub_type_list;
	}
	
	public String toString()
    {
        return description;
    }

}
