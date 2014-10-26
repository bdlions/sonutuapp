package com.sonuto.utils.component;

import java.util.ArrayList;

import com.sportzweb.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeBlogCustomAdapter extends BaseAdapter {

	ArrayList<String> newsItem;
	private Activity context;

	public RecipeBlogCustomAdapter(Activity context, ArrayList<String> news) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.newsItem = news;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsItem.size();
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

		TextView newsTitle = (TextView) convertView
				.findViewById(R.id.blog_recipe_title);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.blog_or_recipe_image);

		String name = newsItem.get(position);
		newsTitle.setText(name);
		
		imageView.setImageResource(R.drawable.upload_img_icon);
		return convertView;
	}

}
