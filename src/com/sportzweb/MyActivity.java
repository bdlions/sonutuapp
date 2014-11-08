package com.sportzweb;

import java.util.ArrayList;

import com.sonuto.utils.component.CustomAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {
	ListView listView;
	ArrayList<String> item, champion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_news);

		listView = (ListView) findViewById(R.id.listViewNews);

		item = new ArrayList<String>();
		champion = new ArrayList<String>();
		item.add("Argentina");
		champion.add("2 time champion");
		item.add("Brazil");
		champion.add("5 time champion");
		item.add("Croatia");
		champion.add("0 time champion");
		item.add("Denmark");
		champion.add("0 time champion");
		item.add("England");
		champion.add("1 time champion");
		item.add("Germany");
		champion.add("4 time champion");
		item.add("Italy");
		champion.add("4 time champion");
		item.add("Spain");
		champion.add("1 time champion");

		//CustomAdapter adapter = new CustomAdapter(this, item);
		//listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				Toast.makeText(getApplicationContext(), item.get(position),
						Toast.LENGTH_LONG).show();
			}
		});
	}

}
