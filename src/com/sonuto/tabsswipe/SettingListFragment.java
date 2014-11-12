package com.sonuto.tabsswipe;

import java.util.ArrayList;

import com.sonuto.utils.APP_INFO;
import com.sonuto.utils.TAB_INFO;
import com.sportzweb.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingListFragment extends Fragment {
	ListView settingsListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_setting_list, null);
		getActivity().setTitle(TAB_INFO.SETTINGS.TITLE);
		settingsListView = (ListView) rootView.findViewById(R.id.settingsListView);

		ArrayList<String> appTitles = new ArrayList<String>();
		for (APP_INFO app : APP_INFO.class.getEnumConstants()) {
			appTitles.add(app.TITLE);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, appTitles);
		settingsListView.setAdapter(adapter);

		settingsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub

				getActivity().setTitle(APP_INFO.class.getEnumConstants()[position].TITLE);
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_container, APP_INFO.class.getEnumConstants()[position].INSTANCE);
				transaction.addToBackStack(null);
				// Commit the transaction
				transaction.commit();

			}
		});

		return rootView;
	}

}