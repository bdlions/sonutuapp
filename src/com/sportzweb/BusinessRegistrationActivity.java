package com.sportzweb;


import java.util.ArrayList;



import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.users.Country;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;


public class BusinessRegistrationActivity extends Activity {

	private Context mContext;
	
	// data source for auto complete text view
		private static String[] countries = { "Albania", "Algeria", "Argentina",
				"Australia", "Austria", "Bahrain", "Bangladesh", "Belarus",
				"Belgium", "Belgium", "Bolivia", "Bosnia and Herzegovina",
				"Bosnia and Herzegovina", "Brazil", "Bulgaria", "Canada", "Canada",
				"Chile", "China", "Colombia", "Costa Rica", "Croatia", "Cyprus",
				"Czech Republic", "Denmark", "Dominican Republic", "Ecuador",
				"Egypt", "El Salvador", "Estonia", "Finland", "France", "Germany",
				"Greece", "Guatemala", "Honduras", "Hong Kong", "Hungary",
				"Iceland", "India", "India", "Indonesia", "Iraq", "Ireland",
				"Ireland", "Israel", "Italy", "Japan", "Japan", "Jordan", "Kuwait",
				"Latvia", "Lebanon", "Libya", "Lithuania", "Luxembourg",
				"Luxembourg", "Macedonia", "Malaysia", "Malta", "Malta", "Mexico",
				"Montenegro", "Montenegro", "Morocco", "Netherlands",
				"New Zealand", "Nicaragua", "Norway", "Norway", "Oman", "Panama",
				"Paraguay", "Peru", "Philippines", "Poland", "Portugal",
				"Puerto Rico", "Qatar", "Romania", "Russia", "Saudi Arabia",
				"Serbia", "Serbia", "Serbia and Montenegro", "Singapore",
				"Singapore", "Slovakia", "Slovenia", "South Africa", "South Korea",
				"Spain", "Spain", "Sudan", "Sweden", "Switzerland", "Switzerland",
				"Switzerland", "Syria", "Taiwan", "Thailand", "Thailand",
				"Tunisia", "Turkey", "Ukraine", "United Arab Emirates",
				"United Kingdom", "United States", "United States", "Uruguay",
				"Venezuela", "Vietnam", "Yemen" };
		private AutoCompleteTextView actCounties;
		ArrayAdapter<String> countriesAdapter;
		
		private Spinner spinnerFood;
		// array list for spinner adapter
		private ArrayList<Country> countryList;
		ProgressDialog pDialog;
		
		String json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_profile_information);
		
		
		mContext = this;
		initUi();
	}
	
	/*
	 *  Initialize UI
	 */
	private void initUi() {
		
		// initialize view
				actCounties = (AutoCompleteTextView) findViewById(R.id.actSelectCountry);
//				countriesAdapter = new ArrayAdapter<String>(
//						BusinessRegistrationActivity.this,
//						android.R.layout.simple_list_item_1, countries);
//				// bind adapter and view
//				actCounties.setAdapter(countriesAdapter);
				
				countryList = new ArrayList<Country>();
				new GetCountries().execute();
	}
	
	
	/**
	 * Async task to get all food categories
	 **/
	private class GetCountries extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(BusinessRegistrationActivity.this);
			pDialog.setMessage("Fetching all country..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			//ServiceHandler jsonParser = new ServiceHandler();
			//String json = jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.GET);
			User user = new User();
			try {
				JSONObject jsonUser = new JSONObject();
				 json="{'categories':[{'id':'1','name':'Bangladesh'},{'id':'2','name':'Bahrian'},{'id':'3','name':'Canada'},{'id':'4','name':'China'},{'id':'5','name':'USA'},{'id':'6','name':'UK'}]}";
				
				user.businessRegistration(new ICallBack() {
					@Override
					public void callBackResultHandler(final Object object) {
						JSONObject jsonObject = (JSONObject)object;
					}

					@Override
					public void callBackErrorHandler(Object object) {
						// TODO Auto-generated method stub
						System.out.println(object);
					}
				}, jsonUser.toString());
			} finally {
				
			}
			
			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray categories = jsonObj
								.getJSONArray("categories");						

						for (int i = 0; i < categories.length(); i++) {
							JSONObject catObj = (JSONObject) categories.get(i);
							Country cat = new Country(catObj.getInt("id"),
									catObj.getString("name"));
							countryList.add(cat);
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			populateSpinner();
		}

	}
	
	/**
	 * Adding spinner data
	 * */
	private void populateSpinner() {
		List<String> lables = new ArrayList<String>();
		

		for (int i = 0; i < countryList.size(); i++) {
			lables.add(countryList.get(i).getName());
		}
		
		countriesAdapter = new ArrayAdapter<String>(
				BusinessRegistrationActivity.this,
				android.R.layout.simple_list_item_1, lables);
		// bind adapter and view
		actCounties.setAdapter(countriesAdapter);
	}
	

	/*
	 * Back to login click action
	 */
	public void backLogin(View view) {
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
