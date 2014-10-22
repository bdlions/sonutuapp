package com.sportzweb;

import java.util.ArrayList;

import com.sonuto.utils.component.NewsAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class TestActivity extends Activity {

	private Context mContext;
	ListView listView;
	ArrayList<String> item, champion;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_news);
		mContext = this;

		listView = (ListView) findViewById(R.id.listView1);
		item = new ArrayList<String>();
		champion = new ArrayList<String>();

		item.add("Home");
		champion.add("2 time champion");
		item.add("Football");
		champion.add("5 time champion");
		item.add("Cricket");
		champion.add("0 time champion");
		item.add("Rougby");
		champion.add("0 time champion");
		item.add("Baseball");

		// NewsAdapter nAdapter=new NewsAdapter(this, item,champion);
		NewsAdapter nAdapter = new NewsAdapter(this, item, champion);
		listView.setAdapter(nAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				Toast.makeText(mContext, item.get(position),
						Toast.LENGTH_LONG).show();
			}
		});
	}

}
