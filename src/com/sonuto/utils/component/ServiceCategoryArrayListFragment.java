package com.sonuto.utils.component;

import java.util.ArrayList;

import com.sportzweb.JSONObjectModel.ServiceCategory;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.support.v4.app.Fragment;

public class ServiceCategoryArrayListFragment extends ListFragment {

	private ArrayList<ServiceCategory> serviceCategoryItem;

	public ArrayList<ServiceCategory> getServiceCategoryItem() {
		return this.serviceCategoryItem;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final ListView lview = getListView();
		lview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		lview.setTextFilterEnabled(true);

		final ServiceCategoryCustomAdapter adapter = new ServiceCategoryCustomAdapter(getActivity(), this.serviceCategoryItem);
		setListAdapter(adapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("FragmentList", "Item clicked: " + id);
	}

	public void setServiceCategoryItem(ArrayList<ServiceCategory> serviceCategoryItem) {
		this.serviceCategoryItem = serviceCategoryItem;
	}
}