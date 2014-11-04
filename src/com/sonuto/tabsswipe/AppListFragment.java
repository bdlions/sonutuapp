package com.sonuto.tabsswipe;

import com.sportzweb.BlogAppActivity;
import com.sportzweb.HealthyRecipeAppActivity;
import com.sportzweb.NewsAppActivity;
import com.sportzweb.R;
import com.sportzweb.xtreambanter.SportsActivity;

import android.content.Intent;
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

public class AppListFragment extends Fragment {
	String[] apps = new String[]{"BMI Calculator", "Healthy Recipy", "News", "Blog", "Xtream Banter"};
	ListView appListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_app_list, null);

		appListView = (ListView) rootView.findViewById(R.id.applicationListView);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, apps);
		appListView.setAdapter(adapter);

		appListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub

				if (position == 0) {
					getActivity().setTitle("BMI Calculator");
					BmiCalculatorFragment newFragment = new BmiCalculatorFragment();
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_container, newFragment);
					transaction.addToBackStack(null);

					// Commit the transaction
					transaction.commit();

				}
				else if (position == 1) {
					Intent intent = new Intent(getActivity(), HealthyRecipeAppActivity.class);
					startActivity(intent);
				}
				else if (position == 2) {
					Intent intent = new Intent(getActivity(), NewsAppActivity.class);
					startActivity(intent);
				}
				else if (position == 3) {
					Intent intent = new Intent(getActivity(), BlogAppActivity.class);
					startActivity(intent);
				}
				else if (position == 4) {
					Intent intent = new Intent(getActivity(), SportsActivity.class);
					startActivity(intent);
				}

			}
		});

		return rootView;
	}

}