package com.sonuto.tabsswipe;
import com.sportzweb.R;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ApplicationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	View rootView = inflater.inflate(R.layout.fragment_applications,container, false);
	AppListFragment newFragment = new AppListFragment();
	FragmentTransaction transaction = getFragmentManager().beginTransaction();
	transaction.add(R.id.fragment_container, newFragment);
	transaction.commit();
	return rootView;
    }

}
