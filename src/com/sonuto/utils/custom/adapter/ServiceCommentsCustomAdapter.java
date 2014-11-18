package com.sonuto.utils.custom.adapter;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sonuto.Config;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.ServiceComment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ServiceCommentsCustomAdapter extends BaseAdapter {

	ArrayList<ServiceComment> serviceCommentItem;
	private Activity context;
	public ImageLoader imageLoader; 

	public ServiceCommentsCustomAdapter(Activity context, ArrayList<ServiceComment> serviceComment) {
		this.context = context;
		this.serviceCommentItem = serviceComment;
		imageLoader=new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return serviceCommentItem.size();
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

		
		final ServiceComment newsCommentObject = serviceCommentItem.get(position);
		

		userNameInRecipeComments.setText(newsCommentObject.getFirst_name() + " " + newsCommentObject.getLast_name());
		userRecipeComments.setText(newsCommentObject.getComment());
		userRecipeCommentsTime.setText(newsCommentObject.getComment_created_on());
		
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		final String imagePath = Config.SERVER_ROOT_URL + "resources/uploads/profile_picture/";
		
		recipeCommentUserImg.setImageResource(R.drawable.upload_img_icon);
		if ((newsCommentObject.getPhoto() != null) | (newsCommentObject.getPhoto().length() != 0)) {
			 imageLoader.DisplayImage(imagePath + newsCommentObject.getPhoto(), recipeCommentUserImg);
		}
		
		return convertView;
	}

}
