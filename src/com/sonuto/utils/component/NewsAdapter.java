package com.sonuto.utils.component;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sportzweb.NewsDetailsActivity;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.SubNews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	ArrayList<SubNews> newsItem;
	private Context context;
	public ImageLoader imageLoader; 

	public NewsAdapter(Context context, ArrayList<SubNews> news) {

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
			convertView = LayoutInflater.from(context).inflate(R.layout.sub_news_item,
					parent, false);
		}

		final SubNews subNews = newsItem.get(position);
		
		OnClickListener listerner = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsDetailsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("news_id",subNews.getId());
				intent.putExtra("news_category_title",subNews.getHeadline());
				context.startActivity(intent);
			}
		};
		
		TextView newsTitle = (TextView) convertView.findViewById(R.id.sub_news_title);
		newsTitle.setOnClickListener(listerner);
		
		
		
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.sub_news_image);
		imageView.setOnClickListener(listerner);
		
		newsTitle.setText(subNews.getHeadline());
		newsTitle.setTextColor(Color.BLACK);
		
		imageView.setImageResource(R.drawable.upload_img_icon);
        imageLoader.DisplayImage(subNews.getPicture(), imageView);
        
		return convertView;
	}

}
