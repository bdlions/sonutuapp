package com.sonuto.tabsswipe;

import java.util.ArrayList;

import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.StatusInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StatusItemAdapter extends BaseAdapter{
	private ArrayList<StatusInfo> list;
	private Context context;
	
	public StatusItemAdapter(Context context, ArrayList<StatusInfo> list){
		this.list = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		if(index < list.size()){
			return list.get(index);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_news_feed_item,
					parent, false);
		}

		TextView textViewDescription = (TextView)convertView.findViewById(R.id.textViewDescription);
		StatusInfo statusInfo = (StatusInfo)getItem(index);
		textViewDescription.setText(statusInfo.getDescription());
		
		return convertView;
	}


}
