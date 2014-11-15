package com.sonuto.utils.component;

import java.util.ArrayList;

import com.sportzweb.JSONObjectModel.BlogCategory;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;


public class ArrayListFragment extends ListFragment {

	private ArrayList<BlogCategory> blogCategoryItem;
	ArrayList<String> selectedItem = new ArrayList<String>();

	public ArrayList<BlogCategory> getBlogCategoryItem() {
		return this.blogCategoryItem;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final ListView lview = getListView();
		lview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		lview.setTextFilterEnabled(true);

		final BlogCategoryCustomAdapter adapter = new BlogCategoryCustomAdapter(getActivity(), this.blogCategoryItem);
		setListAdapter(adapter);
		
		

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("FragmentList", "Item clicked: " + id);
		CheckedTextView ctv = (CheckedTextView) v;
		BlogCategory bc = (BlogCategory)l.getItemAtPosition(position);
		bc.setSelected(!bc.isSelected());
	}

	public void setBlogCategoryItem(ArrayList<BlogCategory> blogCategoryItem) {
		this.blogCategoryItem = blogCategoryItem;
	}
}