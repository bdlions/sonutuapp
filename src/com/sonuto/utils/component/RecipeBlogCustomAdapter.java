package com.sonuto.utils.component;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sportzweb.BlogAppActivity;
import com.sportzweb.BlogDetailsActivity;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Blogs;
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

public class RecipeBlogCustomAdapter extends BaseAdapter {

	ArrayList<Blogs> blogsItem;
	private Activity context;
	public ImageLoader imageLoader; 

	public RecipeBlogCustomAdapter(Activity context, ArrayList<Blogs> blogs) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.blogsItem = blogs;
		imageLoader=new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return blogsItem.size();
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

		final Blogs blogs = blogsItem.get(position);
		
		
		TextView newsTitle = (TextView) convertView
				.findViewById(R.id.blog_recipe_title);
		//newsTitle.setOnClickListener(listerner);
		
		
		
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.blog_or_recipe_image);
		//imageView.setOnClickListener(listerner);
		
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "Id is : " + blogs.getId(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(context, BlogDetailsActivity.class);
				intent.putExtra("blog_id",blogs.getId());
				intent.putExtra("blog_category_title",blogs.getBlog_category());
				
				context.startActivity(intent);
			}
		});
		
		newsTitle.setText(blogs.getTitle());
		imageView.setImageResource(R.drawable.upload_img_icon);
        imageLoader.DisplayImage(blogs.getPicture(), imageView);
        
		return convertView;
	}

}
