package com.sonuto.utils.component;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sportzweb.NewsDetailsActivity;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.News;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	ArrayList<News> newsItem;
	private Activity context;
	public ImageLoader imageLoader; 

	public CustomAdapter(Activity context, ArrayList<News> news) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.newsItem = news;
		imageLoader=new ImageLoader(context);

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
			convertView = LayoutInflater.from(context).inflate(R.layout.row,
					parent, false);
		}

		TextView newsTitle = (TextView) convertView.findViewById(R.id.newsTitle);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.flag);

		
		final News news = newsItem.get(position);
		
		//String name = newsItem.get(position);
		newsTitle.setText(news.getTitle());
		
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(context, NewsDetailsActivity.class);
				intent.putExtra("news_id",news.getId());
				intent.putExtra("news_category_title",news.getTitle());
				
				context.startActivity(intent);
			}
		});
		
		
		imageView.setImageResource(R.drawable.upload_img_icon);
        imageLoader.DisplayImage(news.getPicture(), imageView);
		
		
		return convertView;
	}

}
