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

public class NewsAdapter extends BaseAdapter {

	ArrayList<String> newsPicture;
	ArrayList<String> newsItem;
	private Activity context;

	public NewsAdapter(Activity context, ArrayList<String> newsImage,
			ArrayList<String> newsItem) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.newsPicture = newsImage;
		this.newsItem = newsItem;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
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
		convertView = LayoutInflater.from(context).inflate(R.layout.news_item,parent, false);

		TextView countryName = (TextView) convertView.findViewById(R.id.news);
		ImageView flag = (ImageView) convertView.findViewById(R.id.news_image);

		String name = newsPicture.get(position);
		countryName.setText(name);
		countryName.setText(newsItem.get(position));
		if (name.contains("Home")) {
			flag.setImageResource(R.drawable.gear);
		}else if (name.contains("Football")) {
			flag.setImageResource(R.drawable.gear);
		}else if (name.contains("Cricket")) {
			flag.setImageResource(R.drawable.gear);
		}else if (name.contains("Rougby")) {
			flag.setImageResource(R.drawable.gear);
		}else {
			flag.setImageResource(R.drawable.gear);
		}

		return convertView;
	}

}
