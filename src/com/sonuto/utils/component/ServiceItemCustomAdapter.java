package com.sonuto.utils.component;

import java.util.ArrayList;

import com.bdlions.load.image.ImageLoader;
import com.sportzweb.R;
import com.sportzweb.ServiceDetailsActivity;
import com.sportzweb.JSONObjectModel.ServiceItem;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServiceItemCustomAdapter extends BaseAdapter {

	ArrayList<ServiceItem> serviceItem;
	private Activity context;
	public ImageLoader imageLoader;

	public ServiceItemCustomAdapter(Activity context, ArrayList<ServiceItem> serviceItem) {

		this.context = context;
		this.serviceItem = serviceItem;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return serviceItem.size();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.service_directory_item, parent, false);
		}
		
		final ServiceItem sItem = serviceItem.get(position);
		
		TextView serviceItemHeading = (TextView) convertView.findViewById(R.id.serviceItemHeading);
		TextView serviceAddressItemHeading = (TextView) convertView.findViewById(R.id.serviceAddressItemHeading);
		TextView serviceItemAddressMain = (TextView) convertView.findViewById(R.id.serviceItemAddressMain);
		TextView serviceItemAddressSub = (TextView) convertView.findViewById(R.id.serviceItemAddressSub);
		TextView serviceItemAddressPhoneNo = (TextView) convertView.findViewById(R.id.serviceItemAddressPhoneNo);
		TextView serviceItemAddressDistance = (TextView) convertView.findViewById(R.id.serviceItemAddressDistance);
		//TextView serviceItemAddressDetails = (TextView) convertView.findViewById(R.id.serviceItemAddressDetails);
		
		
		serviceItemHeading.setText(sItem.getTitle());
		serviceAddressItemHeading.setText(sItem.getTitle());
		serviceItemAddressMain.setText(sItem.getAddress());
		serviceItemAddressSub.setText(sItem.getAddress());
		serviceItemAddressPhoneNo.setText(sItem.getPhone());
		serviceItemAddressDistance.setText(sItem.getDistance());
		
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(context, ServiceDetailsActivity.class);
				intent.putExtra("service_id",sItem.getService_id());
				intent.putExtra("title",sItem.getTitle());
				intent.putExtra("address",sItem.getAddress());
				intent.putExtra("phone",sItem.getPhone());
				intent.putExtra("distance",sItem.getDistance());
				
				context.startActivity(intent);
			}
		});
		

		return convertView;
	}
	


}
