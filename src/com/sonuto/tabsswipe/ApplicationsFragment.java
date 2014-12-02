package com.sonuto.tabsswipe;
import com.sonuto.utils.Communicator;
import com.sportzweb.ActivitySearch;
import com.sportzweb.R;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ApplicationsFragment extends Fragment implements Communicator {

	private AppListFragment newFragment = new AppListFragment();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_applications, container, false);
		setHasOptionsMenu(true);
		
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
	@Override 
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_main_actions, menu);
	    menu.findItem(R.id.action_post_status).setVisible(false);
	    super.onCreateOptionsMenu(menu, inflater);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_search){
			Intent searchIntent = new Intent(getActivity(), ActivitySearch.class);
			startActivity(searchIntent);
		}
		return super.onOptionsItemSelected(item);
	}
}
