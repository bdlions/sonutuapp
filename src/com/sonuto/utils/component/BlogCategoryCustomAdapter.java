package com.sonuto.utils.component;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sportzweb.NewsDetailsActivity;
import com.sportzweb.R;
import com.sportzweb.RecipeDetailsActivity;
import com.sportzweb.JSONObjectModel.BlogCategory;
import com.sportzweb.JSONObjectModel.News;
import com.sportzweb.JSONObjectModel.SubNews;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class BlogCategoryCustomAdapter extends BaseAdapter {

	ArrayList<BlogCategory> blogCategoryItem;
	private Activity context;
	public ImageLoader imageLoader;
	private boolean[] checked;

	public BlogCategoryCustomAdapter(Activity context, ArrayList<BlogCategory> blogCategory) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.blogCategoryItem = blogCategory;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return blogCategoryItem.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.blog_category_row, parent, false);
		}
		
		final BlogCategory bCategory = blogCategoryItem.get(position);

		CheckBox categoryCheckBox = (CheckBox) convertView.findViewById(R.id.category_checkBox);
		//category.setText(bCategory.getTitle());
		
		TextView blogCategoryTitle = (TextView) convertView.findViewById(R.id.blogCategoryTitle);
		//Button categoryImg = (Button) convertView.findViewById(R.id.imgBtn);
		
		blogCategoryTitle.setText(bCategory.getTitle());
		//blogCategoryTitle.setLayoutParams();
		
		
		categoryCheckBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
//		categoryCheckBox.setChecked(checked[position]); // set the status as we stored it        
//		categoryCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
//
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                checked[position]=isChecked;
//            }
//        });
		
		return convertView;
	}
	


}
