package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bdlions.components.HListView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BlogsApp;
import com.sonuto.utils.component.RecipeBlogCustomAdapter;
import com.sportzweb.JSONObjectModel.Blogs;
import com.sportzweb.JSONObjectModel.BlogsTab;


public class ServiceDirectoryActivity extends Fragment{
	// process dialer
	ProgressDialog pDialog;
	private Context mContext;
	View rootView;
	Activity activity;
	Button btnSDPostCode;
	FrameLayout service_directory_postcode_setp, service_directory_category_setp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		rootView = inflater.inflate(R.layout.activity_service_app, null);
		activity = getActivity();
		
		
		service_directory_postcode_setp = (FrameLayout) rootView.findViewById(R.id.service_directory_postcode_setp);
		service_directory_category_setp = (FrameLayout) rootView.findViewById(R.id.service_directory_category_setp);
		
		btnSDPostCode = (Button) rootView.findViewById(R.id.btnSDPostCode);
		btnSDPostCode.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	service_directory_category_setp.setVisibility(View.VISIBLE);
            	service_directory_postcode_setp.setVisibility(View.GONE);
            }
        });
		
		// gets the activity's default ActionBar
	    ActionBar actionBar = getActivity().getActionBar();
	    actionBar.show();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		return rootView;
	}

}
