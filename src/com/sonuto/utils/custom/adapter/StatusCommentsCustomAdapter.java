package com.sonuto.utils.custom.adapter;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.sonuto.Config;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.StatusComment;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StatusCommentsCustomAdapter extends BaseAdapter {

	ArrayList<StatusComment> statusCommentItem;
	private Activity context;
	public ImageLoader imageLoader; 

	public StatusCommentsCustomAdapter(Activity context, ArrayList<StatusComment> statusComment) {
		this.context = context;
		this.statusCommentItem = statusComment;
		imageLoader=new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return statusCommentItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.status_comment_row,
					parent, false);
		}

		TextView userNameInStatusComments = (TextView) convertView.findViewById(R.id.userNameInStatusComments);
		TextView userStatusComments = (TextView) convertView.findViewById(R.id.userStatusComments);
		TextView userStatusCommentsTime = (TextView) convertView.findViewById(R.id.userStatusCommentsTime);
		ImageView commentUserImg = (ImageView) convertView.findViewById(R.id.statusCommentUserImg);

		
		StatusComment statusCommentObject = statusCommentItem.get(position);
		
		JSONObject userInfo =  statusCommentObject.getUser_info();
		System.out.print(userInfo);
		Log.d("My App", userInfo.toString());
//		Gson gson = new Gson();

		final String imagePath = Config.PROFILE_PIC_DIR_LG;
		
		commentUserImg.setImageResource(R.drawable.upload_img_icon);

		try {
			userNameInStatusComments.setText(userInfo.get("first_name") + " " + userInfo.get("last_name"));
			if ((userInfo.get("photo") != null) ) {
				 imageLoader.DisplayImage(imagePath + userInfo.get("photo"), commentUserImg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		userStatusComments.setText(statusCommentObject.getDescription());
		userStatusCommentsTime.setText(statusCommentObject.getCreated_on());
		
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		return convertView;
	}

}
