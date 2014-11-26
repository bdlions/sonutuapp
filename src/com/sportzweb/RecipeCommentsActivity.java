package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.HealthyRecipeApp;
import com.sonuto.session.SessionManager;
import com.sonuto.users.AppID;
import com.sonuto.utils.custom.adapter.RCommentsCustomAdapter;
import com.sportzweb.JSONObjectModel.NewsComment;
import com.sportzweb.JSONObjectModel.RecipeComment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RecipeCommentsActivity extends Activity {

	EditText recipeCommentText;
	ImageView blogImageView;
	ListView commentListViewForRecipe;
	public ImageLoader imageLoader;
	Context context;
	String blog_category_title;
	// process dialer
	ProgressDialog pDialog;
	Button recipeCommentPostBtn;
	private RadioGroup radioGroup;
	private RadioButton positive, negitive, neutral;
	int recipe_id,rate_id = 0;
	String comments,userComments;
	JSONArray commentsJSONArr;
	private ArrayList<RecipeComment> recipeCommentObjList = new ArrayList<RecipeComment>();
	RCommentsCustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_comments);
		context = this;
		initUI();
		Process();
	}

	private void initUI() {

		radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
		positive = (RadioButton) findViewById(R.id.positive);
		negitive = (RadioButton) findViewById(R.id.negitive);
		neutral = (RadioButton) findViewById(R.id.neutral);
		
		recipeCommentText = (EditText) findViewById(R.id.recipeCommentText);
		recipeCommentPostBtn = (Button) findViewById(R.id.recipeCommentPostBtn);
		commentListViewForRecipe = (ListView) findViewById(R.id.commentListViewForRecipe);

	}

	private void Process() {
		Intent intent = getIntent();
		recipe_id = intent.getIntExtra("recipe_id", 0);
		comments = intent.getStringExtra("comments");
		try {
			commentsJSONArr = new JSONArray(comments);
			Gson gson = new Gson();
			int total_comments = commentsJSONArr.length();
			for (int i = 0; i < total_comments; i++) {
				RecipeComment comment = gson.fromJson(commentsJSONArr.get(i).toString(), RecipeComment.class);
				recipeCommentObjList.add(comment);
			}
			
			adapter = new RCommentsCustomAdapter(this, recipeCommentObjList);
			commentListViewForRecipe.setAdapter(adapter);

			//System.out.print(recipeCommentObjList);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if (checkedId == R.id.positive) {
					Toast.makeText(getApplicationContext(), "choice: positive",Toast.LENGTH_SHORT).show();
					rate_id = 1;
				} else if (checkedId == R.id.negitive) {
					Toast.makeText(getApplicationContext(), "choice: negitive",Toast.LENGTH_SHORT).show();
					rate_id = 2;
				} else {
					Toast.makeText(getApplicationContext(), "choice: neutral",Toast.LENGTH_SHORT).show();
				}
			}

		});

		recipeCommentPostBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				JSONObject jsonRecipeCommentObj = new JSONObject();
				userComments = recipeCommentText.getText().toString();
				if(isVerifiedCommentTextStep()) {
					try {
						int userId = SessionManager.getInstance().getUserId();
						jsonRecipeCommentObj.put("user_id", userId);
						jsonRecipeCommentObj.put("application_id", AppID.HEALTHY_RECIPE.getValue());
						jsonRecipeCommentObj.put("item_id", recipe_id);
						jsonRecipeCommentObj.put("comment", userComments);
						jsonRecipeCommentObj.put("rate_id", rate_id);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					
					pDialog = new ProgressDialog(context);
					pDialog.setMessage("Fetching data..");
					pDialog.setCancelable(false);
					pDialog.show();
					
					HealthyRecipeApp healthyRecipeapp = new HealthyRecipeApp();
					healthyRecipeapp.postrecipeComments(new ICallBack() {

						@Override
						public void callBackResultHandler(Object object) {
							pDialog.dismiss();
							JSONObject recipeCommentJSONObject = (JSONObject) object;
							
							try {
								JSONObject recipeInfoObj = recipeCommentJSONObject.getJSONObject("comment_info");
								Gson gson = new Gson();
								
								RecipeComment recipeInfo = gson.fromJson(recipeInfoObj.toString(), RecipeComment.class);
								recipeCommentObjList.add(recipeInfo);
								adapter.notifyDataSetChanged();
								recipeCommentText.setText("");
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void callBackErrorHandler(Object object) {

						}
					}, jsonRecipeCommentObj.toString());
				}
				
			}
		});
	}
	
	/**
	 * isVerifiedCommentTextStep validation method
	 * @return boolean value
	 */
	public boolean isVerifiedCommentTextStep() {
		if (userComments.length() == 0) {
			Toast.makeText(context, getString(R.string.commentRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

}
