package com.sportzweb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.HealthyRecipeApp;
import com.sonuto.session.SessionManager;
import com.sonuto.users.AppID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecipeDetailsActivity extends Activity {

	TextView healthyRecipeCategory, recipeTitle, recipeDetail, ingrediant,
			preparationMethod, duration;
	LinearLayout llShareReipe,llCommentReipe;
	ImageView recipeImageView;
	public ImageLoader imageLoader;
	Context context;
	// process dialer
	ProgressDialog pDialog;
	ImageButton btnlike, btnComment, btnShare;
	Integer recipe_id;
	JSONArray recipeCommentsArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthy_recipes_details);
		context = this;
		initUI();
		process();
	}

	public void initUI() {

		// ImageButton intitialize
		// btnlike = (ImageButton) findViewById(R.id.btnLikeReipe);
		llShareReipe = (LinearLayout) findViewById(R.id.llShareReipe);
		llCommentReipe = (LinearLayout) findViewById(R.id.llCommentReipe);
		btnShare = (ImageButton) findViewById(R.id.btnShareReipe);
		btnComment = (ImageButton) findViewById(R.id.btnCommentReipe);
		llCommentReipe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(context, RecipeCommentsActivity.class);
				i.putExtra("recipe_id", recipe_id);
				i.putExtra("comments", recipeCommentsArray.toString());
				startActivity(i);

			}

		});
		
		
		llShareReipe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				JSONObject jsonRecipeObj = new JSONObject();
				
					try {
						int userId = SessionManager.getInstance().getUserId();
						jsonRecipeObj.put("user_id", userId);
						jsonRecipeObj.put("application_id", AppID.HEALTHY_RECIPE.getValue());
						jsonRecipeObj.put("item_id", recipe_id);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				
				HealthyRecipeApp healthyRecipeShare = new HealthyRecipeApp();
				healthyRecipeShare.recipeShare(new ICallBack() {
					
					@Override
					public void callBackResultHandler(Object object) {
						
						
					}
					
					@Override
					public void callBackErrorHandler(Object object) {
						
						
					}
				}, jsonRecipeObj);
			}
		});
		
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
		final String recipe_category_title = intent
				.getStringExtra("recipe_category_title");

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
					JSONObject recipeDetails = recipeDetaisJSONObject
							.getJSONObject("recipe_info");
					recipeCommentsArray = recipeDetaisJSONObject
							.getJSONArray("comment_list");

					healthyRecipeCategory.setText(recipe_category_title);
					healthyRecipeCategory.setTextColor(Color
							.parseColor("#00ACEA"));

					recipeTitle.setText(recipeDetails.getString("title"));
					recipeDetail.setText(recipeDetails.getString("description"));
					ingrediant.setText(recipeDetails.getString("ingredients"));
					preparationMethod.setText(recipeDetails
							.getString("preparation_method"));

					duration.setText(recipeDetails.getString("duration"));

					String imagePath = Config.SERVER_ROOT_URL
							+ "resources/images/applications/healthy_recipes/";

					recipeImageView
							.setImageResource(R.drawable.upload_img_icon);
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
