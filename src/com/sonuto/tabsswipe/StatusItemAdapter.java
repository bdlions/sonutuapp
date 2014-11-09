package com.sonuto.tabsswipe;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sportzweb.R;
import com.sportzweb.StatusCommentsActivity;
import com.sportzweb.JSONObjectModel.StatusInfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StatusItemAdapter extends BaseAdapter{
	private ArrayList<StatusInfo> list;
	private Context context;
	public ImageLoader imageLoader;
	StatusInfo statusInfo;
	
	public StatusItemAdapter(Context context, ArrayList<StatusInfo> list){
		this.list = list;
		this.context = context;
		imageLoader=new ImageLoader(context);
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

		statusInfo = (StatusInfo)getItem(index);
		
		TextView textViewNameOfUser = (TextView)convertView.findViewById(R.id.textViewNameOfUser);
		TextView textViewpostedStatusTime = (TextView)convertView.findViewById(R.id.textViewpostedStatusTime);
		TextView textViewDescription = (TextView)convertView.findViewById(R.id.textViewDescription);
		ImageView imgViewOfUsers = (ImageView) convertView.findViewById(R.id.imageOfUser);
		ImageView commentStatusBtn = (ImageView) convertView.findViewById(R.id.commentStatusBtn);
		
		commentStatusBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				int status_id = statusInfo.getStatus_id();
				Intent i = new Intent(context, StatusCommentsActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}
		});
		
		String img_path = "resources/uploads/profile_picture/50x50/";
		textViewNameOfUser.setText(statusInfo.getFirst_name() + " " + statusInfo.getLast_name());
		textViewDescription.setText(statusInfo.getDescription());
		textViewpostedStatusTime.setText(statusInfo.getStatus_created_on());
		
		//imgViewOfUsers.setImageResource(R.drawable.upload_img_icon);
        //imageLoader.DisplayImage(img_path+statusInfo.getPhoto(), imgViewOfUsers);
		
		return convertView;
	}


}