package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.sonuto.Config;
import com.sonuto.loadimage.ImageLoader;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.rpc.register.HealthyRecipeApp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.TimedText;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeDetailsActivity extends Activity {

	TextView healthyRecipeCategory, recipeTitle, recipeDetail, ingrediant,preparationMethod,duration;
	ImageView recipeImageView;
	public ImageLoader imageLoader;
	Context context;
	// String blog_category_title;
	// process dialer
	ProgressDialog pDialog;
	ImageButton btnlike,btnComment,btnShare;
	Integer recipe_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthy_recipes_details);
		context = this;
		initUI();
		process();
	}
	
public void initUI(){
		
		//ImageButton intitialize
		btnlike = (ImageButton) findViewById(R.id.btnLikeReipe);
		btnComment = (ImageButton) findViewById(R.id.btnCommentReipe);
		btnComment.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent i =  new Intent(context, RecipeCommentsActivity.class);
				i.putExtra("recipe_id", recipe_id);
				startActivity(i);
				
			}
			
		});
		
		
		btnShare = (ImageButton) findViewById(R.id.btnShareReipe);
		imageLoader = new ImageLoader(context);
		healthyRecipeCategory = (TextView) findViewById(R.id.recipeCategoryText);
		recipeTitle = (TextView) findViewById(R.id.recipeTitletext);
		recipeDetail = (TextView) findViewById(R.id.recipeDetailsText);
		ingrediant = (TextView) findViewById(R.id.ingredientOfRecipetext);
		preparationMethod = (TextView) findViewById(R.id.preparationMethodOfRecipetext);
		duration = (TextView) findViewById(R.id.durationOfRecipetext);
		recipeImageView = (ImageView) findViewById(R.id.recipeImageView);

	}
	
	public void process() {
		Intent intent = getIntent();
		recipe_id = intent.getIntExtra("recipe_id", 0);
		final String recipe_category_title = intent.getStringExtra("recipe_category_title");


		 pDialog = new ProgressDialog(context);
		 pDialog.setMessage("Fetching data..");
		 pDialog.setCancelable(false);
		 pDialog.show();

		HealthyRecipeApp healthyRecipeapp = new HealthyRecipeApp();
		healthyRecipeapp.getRecipeDetails(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				JSONObject recipeDetaisJSONObject = (JSONObject) object;
				pDialog.dismiss();
				try {
					JSONObject recipeDetails = recipeDetaisJSONObject.getJSONObject("recipe_info");
					
					healthyRecipeCategory.setText(recipe_category_title);
					healthyRecipeCategory.setTextColor(Color.parseColor("#00ACEA"));
					
					recipeTitle.setText(recipeDetails.getString("title"));
					recipeDetail.setText(recipeDetails.getString("description"));
					ingrediant.setText(recipeDetails.getString("ingredients"));
					preparationMethod.setText(recipeDetails.getString("preparation_method"));
					
					duration.setText(recipeDetails.getString("duration"));
					
					String imagePath = Config.SERVER_ROOT_URL
							+ "resources/images/applications/healthy_recipes/";

					recipeImageView.setImageResource(R.drawable.upload_img_icon);
					imageLoader.DisplayImage(
							imagePath + recipeDetails.get("picture"),
							recipeImageView);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub

			}
		}, recipe_id);
	}

}
