package com.sonuto.tabsswipe;
import com.sonuto.utils.Communicator;
import com.sportzweb.R;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ApplicationsFragment extends Fragment implements Communicator {

	private AppListFragment newFragment = new AppListFragment();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_applications, container, false);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.fragment_container, newFragment, "TAG_1");
		transaction.commit();
		return rootView;
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		FragmentManager fm = getFragmentManager();
		if (fm.popBackStackImmediate()) {
			fm.popBackStack("APP_LIST",FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}

}
