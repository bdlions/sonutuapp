package com.sportzweb.JSONObjectModel;


import com.google.gson.JsonArray;

public class HealthyRecipesTab {

	private int id;
	private String title;
	private JsonArray recipe_list;
	
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
	public JsonArray getRecipe_list() {
		return recipe_list;
	}
	public void setRecipe_list(JsonArray recipe_list) {
		this.recipe_list = recipe_list;
	}
	


}
