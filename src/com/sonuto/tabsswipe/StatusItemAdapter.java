package com.sonuto.tabsswipe;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.JsonArray;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.StatusFeed;
import com.sonuto.session.SessionManager;
import com.sonuto.share.StatusShareActivity;
import com.sonuto.utils.IActivityResultFromAdapter;
import com.sonutu.constants.SHARE_TYPE;
import com.sonutu.constants.STATUS_CATEGORY;
import com.sonutu.constants.STATUS_TYPE;
import com.sportzweb.R;
import com.sportzweb.StatusCommentsActivity;
import com.sportzweb.JSONObjectModel.StatusInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusItemAdapter extends ArrayAdapter<StatusInfo>{
	
	private ArrayList<StatusInfo> list;
	private Context context;
	private Activity activity;
	private ImageLoader imageLoader;
	
	private IActivityResultFromAdapter resultFromAdapter; 
	
	public StatusItemAdapter(IActivityResultFromAdapter resultFromAdapter, Activity activity, ArrayList<StatusInfo> list){
		super(activity.getApplicationContext(), R.layout.activity_news_feed_item, list);
		this.list = list;
		context = activity.getApplicationContext();
		this.activity = activity;
		this.resultFromAdapter = resultFromAdapter;
		imageLoader=new ImageLoader(context);
	}
	
	public void addItemAt(int index, StatusInfo statusInfo){
		this.list.add(index, statusInfo);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public StatusInfo getItem(int index) {
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

		final StatusInfo statusInfo = (StatusInfo)getItem(index);
		final int status_id = statusInfo.getStatus_id();
		
		
		final JsonArray statusComments = statusInfo.getFeedbacks();
		final JsonArray liked_user_list = statusInfo.getLiked_user_list();
		int likeCounter = liked_user_list.size();
		int commentCounter = statusComments.size();
		
		TextView textViewNameOfUser = (TextView)convertView.findViewById(R.id.textViewNameOfUser);
		TextView textViewTotalLike = (TextView)convertView.findViewById(R.id.textViewTotalLike);
		TextView textViewTotalcomments = (TextView)convertView.findViewById(R.id.textViewTotalcomments);
		TextView textViewpostedStatusTime = (TextView)convertView.findViewById(R.id.textViewpostedStatusTime);
		TextView textViewDescription = (TextView)convertView.findViewById(R.id.textViewDescription);
		ImageView imgViewOfUsers = (ImageView) convertView.findViewById(R.id.imageOfUser);
		ImageView commentStatusBtn = (ImageView) convertView.findViewById(R.id.commentStatusBtn);
		LinearLayout llComment = (LinearLayout) convertView.findViewById(R.id.llComment);
		LinearLayout llShare = (LinearLayout) convertView.findViewById(R.id.llShare);
		ImageView likeStatusBtn = (ImageView) convertView.findViewById(R.id.likeStatusBtn);
		ImageView shareStatusBtn = (ImageView) convertView.findViewById(R.id.shareStatusBtn);
		
		
		likeStatusBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(final View button) {
				// increment the value of like counter by one and call the rpc to update
				StatusFeed statusFeed = new StatusFeed();
				statusFeed.update_status_like(new ICallBack() {
					
					@Override
					public void callBackResultHandler(Object object) {
						((ImageButton)button).setImageResource(R.drawable.like_icon);
					}
					
					@Override
					public void callBackErrorHandler(Object object) {
						
					}
				}, status_id);
				
				
			}
		});
		
		commentStatusBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				//int status_id = statusInfo.getStatus_id();
				Intent i = new Intent(context, StatusCommentsActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra("status_id", status_id);
				i.putExtra("statusComments", statusComments.toString());
				context.startActivity(i);
			}
		});
		
		shareStatusBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				//int status_id = statusInfo.getStatus_id();
				Intent i = new Intent(activity.getApplicationContext(), StatusShareActivity.class);
				
				Bundle params = new Bundle();

				params.putInt("status_type_id", STATUS_TYPE.GENERAL.getValue());
				params.putInt("status_category_id", STATUS_CATEGORY.NEWSFEED.getValue());
				params.putInt("mapping_id", SessionManager.getInstance().getUserId());
				params.putInt("reference_id", status_id);
				params.putInt("shared_type_id", SHARE_TYPE.OTHER_STATUS.getValue());
				
				
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtras(params);
				
				//context.startActivity(i);
				
				//activity.startActivityForResult(i, 2);
				resultFromAdapter.startActivityResultFromAdapter(i, 2);
			
			}
		});
		
		textViewTotalLike.setText(likeCounter + " Likes");
		textViewTotalcomments.setText(commentCounter + " Comments");
		
		textViewNameOfUser.setText(statusInfo.getFirst_name() + " " + statusInfo.getLast_name());
		textViewDescription.setText(Html.fromHtml(statusInfo.getDescription()));
		textViewpostedStatusTime.setText(statusInfo.getStatus_created_on());
		
		//imgViewOfUsers.setImageResource(R.drawable.upload_img_icon);
        imageLoader.DisplayImage(Config.PROFILE_PIC_DIR_LG + statusInfo.getPhoto(), imgViewOfUsers);
		
        ImageView imageViewRemove = (ImageView) convertView.findViewById(R.id.imageViewRemove);
        imageViewRemove.setVisibility(statusInfo.getAllow_to_delete() == true? View.VISIBLE : View.INVISIBLE);
		imageViewRemove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new StatusFeed().deleteStatus(new ICallBack() {
					
					@Override
					public void callBackResultHandler(Object object) {
						JSONObject jsonObject = (JSONObject)object;
						try
						{
							if(jsonObject.get("status").toString().equalsIgnoreCase("true"))
							{
								remove(statusInfo);
								notifyDataSetChanged();
							}
						}
						catch(JSONException e)
						{
							
						}
						
					}
					
					@Override
					public void callBackErrorHandler(Object object) {
						// TODO Auto-generated method stub
						
					}
				}, statusInfo.getStatus_id());
			}
		});
        
		return convertView;
	}

}
