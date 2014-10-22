package com.sonuto.newstabsswipe;

import com.sportzweb.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsCommonFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		int tabId = getArguments().getInt("tabId"); 
		
		//call server to get information/data for this tabid
		//and set news in the fragment
		View rootView = inflater.inflate(R.layout.fragment_formula, container, false);
		
		TextView tv = (TextView) rootView.findViewById(R.id.textViewSample);
		tv.setText("Tab Id: " + tabId);
		
		return rootView;
	}
	
}
