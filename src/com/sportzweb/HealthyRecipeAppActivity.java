package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bdlions.components.HListView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.HealthyRecipeApp;
import com.sonuto.utils.component.HealthyRecipeCustomAdapter;
import com.sportzweb.JSONObjectModel.HealthyRecipes;
import com.sportzweb.JSONObjectModel.HealthyRecipesTab;


public class HealthyRecipeAppActivity extends Fragment{
	// process dialer
	ProgressDialog pDialog;
	String recipe_category;
	private ArrayList<HealthyRecipesTab> tabList = new ArrayList<HealthyRecipesTab>();

	View rootView;
	Activity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_healthy_recipe_app, null);
		activity = getActivity();
		process();
		return rootView;
	}

	
	
	public void process() {
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Fetching data ...");
		pDialog.setCancelable(false);
		pDialog.show();
		
		HealthyRecipeApp healthyRecipeApp = new HealthyRecipeApp();
		healthyRecipeApp.getHomePageData(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				// get json list of news tabs
				JSONObject jsonObject = (JSONObject) object;
				pDialog.dismiss();
					try {
						JSONArray jsonTabs = jsonObject.getJSONArray("recipe_category_recipe_list");
						
						//JSONArray customJSONTabs = jsonObject.getJSONArray("blog_custom_category_list");
						
						Gson gson = new Gson();
						//tabList.add(gson.fromJson(customJSONTabs.get(0).toString(),BlogsTab.class));
						
						int tabCount = jsonTabs.length();
						for (int i = 0; i < tabCount; i++) {
							HealthyRecipesTab tab = gson.fromJson(jsonTabs.get(i).toString(),HealthyRecipesTab.class);
							tabList.add(tab);
						}
						
						
						LinearLayout parentLayout = (LinearLayout)rootView.findViewById(R.id.parentLayoutForRecipeApp);
						
						for(int i = 0; i < tabList.size(); i ++){
							//blog title
							
							TextView tv = new TextView(getActivity().getApplicationContext());
							
							recipe_category = tabList.get(i).getTitle();
							
							tv.setText(tabList.get(i).getTitle());
							tv.setTextColor(Color.parseColor("#00ACEA"));
							tv.setTypeface(null, Typeface.BOLD);
							parentLayout.addView(tv);
							
							Resources res = getResources();
							XmlPullParser parser = res.getXml(R.xml.horizontal_list_model);
							AttributeSet attributes = Xml.asAttributeSet(parser);
							
							//JSONArray blogsJsonList = jsonObject.getJSONArray("blog_list");
							JsonArray recipesJsonList = tabList.get(i).getRecipe_list();
							int total_recipe = recipesJsonList.size();
							//recipes item
							HListView hListView = new HListView(getActivity().getApplicationContext(), attributes);
							hListView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 200));
							//hListView.setDividerWidth(2);
							
							HealthyRecipes recipes = new HealthyRecipes();
							
							String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/healthy_recipes/";
							
							ArrayList<HealthyRecipes> items = new ArrayList<HealthyRecipes>();
							for(int j = 0; j < total_recipe; j ++){
								recipes = gson.fromJson(recipesJsonList.get(j).toString(),HealthyRecipes.class);
								recipes.setPicture(imagePath+ recipes.getPicture());
								recipes.setRecipe_category(recipe_category);
								items.add(recipes);
							}
							HealthyRecipeCustomAdapter adapter = new HealthyRecipeCustomAdapter(activity, items);
							hListView.setAdapter(adapter);
							
							
							parentLayout.addView(hListView);

						}
						
						
						
					}
					catch(Exception ex){
						
					}
					
				}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	

}
