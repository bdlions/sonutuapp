package com.sonuto.utils.custom.adapter;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sonuto.Config;
import com.sportzweb.R;
import com.sportzweb.UserProfileActivity;
import com.sportzweb.JSONObjectModel.RecipeComment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RCommentsCustomAdapter extends BaseAdapter {

	ArrayList<RecipeComment> recipeCommentItem;
	private Activity context;
	public ImageLoader imageLoader; 

	public RCommentsCustomAdapter(Activity context, ArrayList<RecipeComment> recipeComment) {
		this.context = context;
		this.recipeCommentItem = recipeComment;
		imageLoader=new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return recipeCommentItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.recipe_comment_row,
					parent, false);
		}

		TextView userNameInRecipeComments = (TextView) convertView.findViewById(R.id.userNameInRecipeComments);
		TextView userRecipeComments = (TextView) convertView.findViewById(R.id.userRecipeComments);
		TextView userRecipeCommentsTime = (TextView) convertView.findViewById(R.id.userRecipeCommentsTime);
		ImageView recipeCommentUserImg = (ImageView) convertView.findViewById(R.id.recipeCommentUserImg);

		
		final RecipeComment recipeCommentObject = recipeCommentItem.get(position);
		

		userNameInRecipeComments.setText(recipeCommentObject.getFirst_name() + " " + recipeCommentObject.getLast_name());
		userRecipeComments.setText(recipeCommentObject.getComment());
		userRecipeCommentsTime.setText(recipeCommentObject.getComment_created_on());
		
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		final String imagePath = Config.SERVER_ROOT_URL + "resources/uploads/profile_picture/";
		
		recipeCommentUserImg.setImageResource(R.drawable.upload_img_icon);
		if ((recipeCommentObject.getPhoto() != null) | (recipeCommentObject.getPhoto().length() != 0)) {
			 imageLoader.DisplayImage(imagePath + recipeCommentObject.getPhoto(), recipeCommentUserImg);
		}
		
		return convertView;
	}

}
