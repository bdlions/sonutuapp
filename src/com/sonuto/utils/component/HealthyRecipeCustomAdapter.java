package com.sonuto.utils.component;

import java.util.ArrayList;

import com.sonuto.loadimage.ImageLoader;
import com.sportzweb.BlogAppActivity;
import com.sportzweb.BlogDetailsActivity;
import com.sportzweb.R;
import com.sportzweb.RecipeDetailsActivity;
import com.sportzweb.JSONObjectModel.HealthyRecipes;
import com.sportzweb.JSONObjectModel.News;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HealthyRecipeCustomAdapter extends BaseAdapter {

	ArrayList<HealthyRecipes> healthyRecipesItem;
	private Activity context;
	public ImageLoader imageLoader; 

	public HealthyRecipeCustomAdapter(Activity context, ArrayList<HealthyRecipes> healthyRecipes) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.healthyRecipesItem = healthyRecipes;
		imageLoader=new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return healthyRecipesItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.blog_or_recipe_item,
					parent, false);
		}

		final HealthyRecipes healthyRecipes = healthyRecipesItem.get(position);
		
		
		TextView newsTitle = (TextView) convertView
				.findViewById(R.id.blog_recipe_title);
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.blog_or_recipe_image);

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(context, RecipeDetailsActivity.class);
				intent.putExtra("recipe_id",healthyRecipes.getId());
				intent.putExtra("recipe_category_title",healthyRecipes.getRecipe_category());
				
				context.startActivity(intent);
			}
		});
		
		newsTitle.setText(healthyRecipes.getTitle());
		imageView.setImageResource(R.drawable.upload_img_icon);
        imageLoader.DisplayImage(healthyRecipes.getPicture(), imageView);
        
		return convertView;
	}

}
