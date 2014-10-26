package com.sportzweb;

import java.util.ArrayList;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.sonuto.utils.component.CustomAdapter;
import com.sonuto.utils.component.NewsAdapter;
import com.sonuto.utils.component.RecipeBlogCustomAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class TestActivity extends Activity {

	private Context mContext;
	ListView listView;
	ArrayList<String> item;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthy_recipes);
		mContext = this;
		
		ArrayList<String> items = new ArrayList<String>();
		items.add("Item 1");
		items.add("Item 2");
		items.add("Item 3");
		items.add("Item 4");
		items.add("Item 1");
		items.add("Item 2");
		items.add("Item 3");
		items.add("Item 4");
		items.add("Item 1");
		items.add("Item 2");
		items.add("Item 3");
		items.add("Item 4");
		items.add("Item 1");
		items.add("Item 2");
		items.add("Item 3");
		items.add("Item 4");
		//HorizontalListView lvTest = (HorizontalListView) findViewById(R.id.HorizontalListView);
		//HorizontalListView lvTest1 = (HorizontalListView) findViewById(R.id.HorizontalListView1);
		
		/*ArrayAdapter<String> aItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		HorizontalListView lvTest = (HorizontalListView) findViewById(R.id.HorizontalListView);
		lvTest.setAdapter(aItems);*/
		

		
		
		RecipeBlogCustomAdapter adapter = new RecipeBlogCustomAdapter(this, items);
		//lvTest.setAdapter(adapter);
		//lvTest1.setAdapter(adapter);
		
		/*listView = (ListView) findViewById(R.id.listViewNews);

		item = new ArrayList<String>();
		item.add("Home");
		item.add("Football");
		item.add("Handball");
		item.add("Cricket");
		item.add("Rougby");
		item.add("Bollyball");
		item.add("Boxing");
		item.add("Tennis");

		CustomAdapter adapter = new CustomAdapter(this, item);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				Toast.makeText(getApplicationContext(), item.get(position),
						Toast.LENGTH_LONG).show();
			}
		});*/
	}

}
