package com.sportzweb;

import java.util.ArrayList;

import com.sonuto.utils.component.CustomAdapter;
import com.sonuto.utils.component.NewsAdapter;
import com.sonuto.utils.component.RecipeBlogCustomAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;

public class TestActivity extends ListActivity {

	private Context mContext;
	ListView listView;
	ArrayList<String> item;
	String one;

	private RadioGroup radioGroup;
	private RadioButton positive, negitive, neutral;

	// RadioGroup rg;
	// RadioButton micro, j2ee, php;
	String[] city= {
			"Bangalore",
			"Chennai",
			"Mumbai",
			"Pune",
			"Delhi",
			"Jabalpur",
			"Indore",
			"Ranchi",
			"Hyderabad",
			"Ahmedabad",
			"Kolkata",
			"Bhopal"
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;

		setContentView(R.layout.custom_list_view_with_checkbox);
		
		ListView listview= getListView();
		//	listview.setChoiceMode(listview.CHOICE_MODE_NONE);
		//	listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
			listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);
			
			//--	text filtering
			listview.setTextFilterEnabled(true);
				
				setListAdapter(new ArrayAdapter<String>(this, 
						android.R.layout.simple_list_item_checked,city));
		
		//setContentView(R.layout.common_comments);
		/*radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if (checkedId == R.id.positive) {
					Toast.makeText(getApplicationContext(), "choice: positive",
							Toast.LENGTH_SHORT).show();
				} else if (checkedId == R.id.negitive) {
					Toast.makeText(getApplicationContext(), "choice: negitive",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "choice: neutral",
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		positive = (RadioButton) findViewById(R.id.positive);
		negitive = (RadioButton) findViewById(R.id.negitive);
		neutral = (RadioButton) findViewById(R.id.neutral); */

		// rg = (RadioGroup) findViewById(R.id.radioGroupforcomment);
		// rg.clearCheck();

		// rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		// // TODO Auto-generated method stub
		// if (checkedId == R.id.positive) {
		// Toast.makeText(getApplicationContext(), "choice: Silent",
		// Toast.LENGTH_SHORT).show();
		// } else if (checkedId == R.id.negitive) {
		// Toast.makeText(getApplicationContext(), "choice: Sound",
		// Toast.LENGTH_SHORT).show();
		// } else {
		// Toast.makeText(getApplicationContext(),
		// "choice: Vibration", Toast.LENGTH_SHORT).show();
		// }
		// }
		// });

		// rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		//
		// // find which radio button is selected
		// if (checkedId == R.id.positive) {
		// Toast.makeText(getApplicationContext(), "choice: Silent",
		// Toast.LENGTH_SHORT).show();
		// } else if (checkedId == R.id.negitive) {
		// Toast.makeText(getApplicationContext(), "choice: Sound",
		// Toast.LENGTH_SHORT).show();
		//
		// } else {
		// Toast.makeText(getApplicationContext(),
		// "choice: Vibration",
		// Toast.LENGTH_SHORT).show();
		//
		// }
		//
		// }
		//
		// });

		// OnClickListener listener = new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// RadioButton rb = (RadioButton) v;
		// Toast.makeText(TestActivity.this, rb.getText(),
		// Toast.LENGTH_SHORT).show();
		// }
		// };

		// micro = (RadioButton) findViewById(R.id.positive);
		// micro.setOnClickListener(listener);

		// j2ee = (RadioButton) findViewById(R.id.negitive);
		// j2ee.setOnClickListener(listener);

		// php = (RadioButton) findViewById(R.id.neutral);
		// php.setOnClickListener(listener);

		// ArrayList<String> items = new ArrayList<String>();
		// items.add("Item 1");
		// items.add("Item 2");
		// items.add("Item 3");
		// items.add("Item 4");
		// items.add("Item 1");
		// items.add("Item 2");
		// items.add("Item 3");
		// items.add("Item 4");
		// items.add("Item 1");
		// items.add("Item 2");
		// items.add("Item 3");
		// items.add("Item 4");
		// items.add("Item 1");
		// items.add("Item 2");
		// items.add("Item 3");
		// items.add("Item 4");
		// HorizontalListView lvTest = (HorizontalListView)
		// findViewById(R.id.HorizontalListView);
		// HorizontalListView lvTest1 = (HorizontalListView)
		// findViewById(R.id.HorizontalListView1);

		/*
		 * ArrayAdapter<String> aItems = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, items); HorizontalListView
		 * lvTest = (HorizontalListView) findViewById(R.id.HorizontalListView);
		 * lvTest.setAdapter(aItems);
		 */

		// RecipeBlogCustomAdapter adapter = new RecipeBlogCustomAdapter(this,
		// items);
		// lvTest.setAdapter(adapter);
		// lvTest1.setAdapter(adapter);

		/*
		 * listView = (ListView) findViewById(R.id.listViewNews);
		 * 
		 * item = new ArrayList<String>(); item.add("Home");
		 * item.add("Football"); item.add("Handball"); item.add("Cricket");
		 * item.add("Rougby"); item.add("Bollyball"); item.add("Boxing");
		 * item.add("Tennis");
		 * 
		 * CustomAdapter adapter = new CustomAdapter(this, item);
		 * listView.setAdapter(adapter);
		 * 
		 * listView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> adapter, View v, int
		 * position, long arg3) { Toast.makeText(getApplicationContext(),
		 * item.get(position), Toast.LENGTH_LONG).show(); } });
		 */
	}

	/*
	 * public void oneRadioButtonClicked(View view) { RadioButton micro =
	 * (RadioButton) findViewById(R.id.positive); RadioButton j2ee =
	 * (RadioButton) findViewById(R.id.negitive); RadioButton php =
	 * (RadioButton) findViewById(R.id.neutral); TextView result = (TextView)
	 * findViewById(R.id.result);
	 * 
	 * String s = "Your Choice is - "; if (micro.isChecked()) { s = s +
	 * " Microsoft.NET"; result.setText(""); } if (j2ee.isChecked()) {
	 * result.setText(""); s = s + " J2EE"; } if (php.isChecked()) {
	 * result.setText(""); s = s + " Php"; } result.setText(s); }
	 */
	
	public void onListItemClick(ListView parent, View v,int position,long id) {
		CheckedTextView item = (CheckedTextView) v;
		Toast.makeText(this, city[position] + " checked : " +
		item.isChecked(), Toast.LENGTH_SHORT).show();
	}

}
