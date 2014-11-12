package com.sonuto.utils.component;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sportzweb.NewsDetailsActivity;
import com.sportzweb.R;
import com.sportzweb.RecipeDetailsActivity;
import com.sportzweb.JSONObjectModel.BlogCategory;
import com.sportzweb.JSONObjectModel.News;
import com.sportzweb.JSONObjectModel.ServiceCategory;
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
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class ServiceCategoryCustomAdapter extends BaseAdapter {

	ArrayList<ServiceCategory> serviceCategoryItem;
	private final Activity context;
	public ImageLoader imageLoader;
	private boolean[] checked;

	public ServiceCategoryCustomAdapter(Activity context, ArrayList<ServiceCategory> serviceCategory) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.serviceCategoryItem = serviceCategory;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.serviceCategoryItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.serviceCategoryItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.service_category_row, parent, false);
			//convertView = LayoutInflater.from(this.context).inflate(R.layout.service_category_row, parent, false);
		}
		final ServiceCategory sCategory = serviceCategoryItem.get(position);
		
		final CheckedTextView serviceCategoryCheckBox = (CheckedTextView) convertView.findViewById(R.id.service_category_checkBox);
		serviceCategoryCheckBox.setText(sCategory.getTitle());
		serviceCategoryCheckBox.setTag(Integer.valueOf(position)); 
		return convertView;
	}
	


}
