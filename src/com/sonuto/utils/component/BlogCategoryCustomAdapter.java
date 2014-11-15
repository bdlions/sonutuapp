package com.sonuto.utils.component;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.BlogCategory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class BlogCategoryCustomAdapter extends BaseAdapter {

	ArrayList<BlogCategory> blogCategoryItem;
	private final Activity context;
	public ImageLoader imageLoader;
	CheckedTextView category_checkBox;

	public BlogCategoryCustomAdapter(Activity context, ArrayList<BlogCategory> blogCategory) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.blogCategoryItem = blogCategory;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.blogCategoryItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.blogCategoryItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.blog_category_row, parent, false);
		}

		BlogCategory bCategory = this.blogCategoryItem.get(position);
		final CheckedTextView categoryCheckBox = (CheckedTextView) convertView.findViewById(R.id.category_checkBox);
		categoryCheckBox.setText(bCategory.getTitle());
		categoryCheckBox.setTag(Integer.valueOf(position));
		if(bCategory.isSelected())
			((ListView) parent).setItemChecked(position, true);
		//((ListView) parent).setItemChecked(position, bCategory.isSelected());

		
		return categoryCheckBox;
	}

}
