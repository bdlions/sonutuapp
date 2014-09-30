package com.sonuto.tabsswipe;

import com.sportzweb.LoginActivity;
import com.sportzweb.MainActivity;
import com.sportzweb.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	private Context mContext;
	/*
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) {
	 * 
	 * View rootView = inflater.inflate(R.layout.fragment_settings, container,
	 * false);
	 * 
	 * return rootView;
	 */
	String[] values = new String[] { "Create Business Profile",
			"Account Settings", "Profile Settings", "Log out" };
	ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_settings, container, false);
		lv = (ListView) v.findViewById(R.id.listViewSettings);
		perform(v);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub

				String category = values[position];
				System.out.print(position);

				// ListView Clicked item value
				String itemValue = (String) lv.getItemAtPosition(position);
				Intent intent = new Intent(getActivity(), MainActivity.class);
				startActivity(intent);

			}
		});

		return v;
	}

	public void perform(View v) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		lv.setAdapter(adapter);

	}
}
