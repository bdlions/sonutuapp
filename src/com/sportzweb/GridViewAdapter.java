package com.sportzweb;
import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class GridViewAdapter extends ArrayAdapter {
	private Context context;
	private int layoutResourceId;
	private ArrayList<String> data = new ArrayList<String>();
	
	public GridViewAdapter(Context context, int layoutResourceId,
			ArrayList<String> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		ImageLoader imageLoader = new ImageLoader(context);		
		imageLoader.DisplayImage((String) data.get(position), holder.image);
		return row;
	}

	static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}
}