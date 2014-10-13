package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.users.Country;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileInformationActivity extends Activity {
	private Context mContext;
	private EditText mInstituiton, mOccupation, mEmployee;
	String country, occupation, institution, employee;
	ISessionManager session;

	private AutoCompleteTextView actCounties;
	ArrayAdapter<String> countriesAdapter;
	JSONArray countries;
	private Country selectedCountry = null;
	int countryId = -1;

	// array list for country AutoCompleteTextView adapter
	private ArrayList<Country> countryList;

	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_information);
		mContext = this;

		// Session Manager
		session = new SessionManager(getApplicationContext());

		initUi();
	}

	/*
	 * Initialize UI
	 */
	private void initUi() {

		// initialize AutoCompleteTextView
		actCounties = (AutoCompleteTextView) findViewById(R.id.actSelectCountry);

		// Load data from server for AutoCompleteTextView
		User getCountryList = new User();
		countryList = new ArrayList<Country>();
		getCountryList.countryList(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				JSONObject jsonObject = (JSONObject) object;
				try {
					countries = jsonObject.getJSONArray("country_list");

					Gson gson = new Gson();
					int total_country = countries.length();
					for (int i = 0; i < total_country; i++) {
						Country countryObj = gson.fromJson(countries.get(i)
								.toString(), Country.class);
						countryList.add(countryObj);
					}

					ArrayAdapter<Country> countriesAdapter = new ArrayAdapter<Country>(
							mContext, android.R.layout.simple_list_item_1,
							countryList);
					// bind adapter and view
					actCounties.setAdapter(countriesAdapter);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void callBackErrorHandler(Object object) {

			}
		});

		// Here get the selected item from AutoCompleteTextView
		actCounties.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedCountry = (Country) parent.getAdapter().getItem(
						position);

			}
		});

		mInstituiton = (EditText) findViewById(R.id.regInstituteInputEdtTxt);
		mOccupation = (EditText) findViewById(R.id.regOccupationInputEdtTxt);
		mEmployee = (EditText) findViewById(R.id.regemployeInputEdtTxt);
	}

	public void userValue() {
		countryId = selectedCountry == null ? 0 : selectedCountry.getId();
		occupation = mOccupation.getText().toString().trim();
		institution = mInstituiton.getText().toString().trim();
		employee = mEmployee.getText().toString().trim();
	}

	/*
	 * Verification setting Screen Data not need
	 */
	public boolean isVerified() {

		if (countryId <= 0) {
			Toast.makeText(mContext, getString(R.string.countyRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (occupation.length() == 0) {
			Toast.makeText(mContext, getString(R.string.occupationRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (institution.length() == 0) {
			Toast.makeText(mContext, getString(R.string.institutionRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (employee.length() == 0) {
			Toast.makeText(mContext, getString(R.string.employeRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}

	}

	/*
	 * Save and continue click action
	 */
	public void saveANDcontinue(View view) {
		userValue();
		if (isVerified()) {
			User user = new User();
			try {
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("user_id", session.getUserId());
				jsonUser.put("country_id", countryId);
				jsonUser.put("occupation", occupation);
				jsonUser.put("clg_or_uni", institution);
				jsonUser.put("employer", employee);

				user.updateUsersProfileInfo(new ICallBack() {
					@Override
					public void callBackResultHandler(final Object object) {
						JSONObject jsonObject = (JSONObject) object;
						try {
							if (jsonObject.get("status").toString()
									.equalsIgnoreCase("1")) {
								Intent intent = new Intent(mContext,
										BirthDaySettingActivity.class);
								startActivity(intent);
								finish();
							} else {
								// Login unsuccessful
								alert.showAlertDialog(
										ProfileInformationActivity.this,
										"Profile Edit failed..",
										"Profile edit unsuccessfull", false);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void callBackErrorHandler(Object object) {
						// TODO Auto-generated method stub
						System.out.println(object);
					}
				}, jsonUser.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
