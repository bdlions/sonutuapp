package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class ProfileInformationActivity extends Activity {
	private Context mContext;
	private EditText mInstituiton, mOccupation, mEmployee;
	String country, occupation, institution, employee;
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
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_information);
		mContext = this;

		initUi();
	}

	/*
	 * Initialize UI
	 */
	private void initUi() {

		// initialize view
		actCounties = (AutoCompleteTextView) findViewById(R.id.actSelectCountry);
		countriesAdapter = new ArrayAdapter<String>(
				ProfileInformationActivity.this,
				android.R.layout.simple_list_item_1, countries);

		// bind adapter and view
		actCounties.setAdapter(countriesAdapter);

		mInstituiton = (EditText) findViewById(R.id.regInstituteInputEdtTxt);
		mOccupation = (EditText) findViewById(R.id.regOccupationInputEdtTxt);
		mEmployee = (EditText) findViewById(R.id.regemployeInputEdtTxt);
	}

	public void userValue() {
		country = actCounties.getText().toString().trim();
		occupation = mOccupation.getText().toString().trim();
		institution = mInstituiton.getText().toString().trim();
		employee = mEmployee.getText().toString().trim();
	}

	/*
	 * Verification setting Screen Data not need
	 */
	public boolean isVerified() {

		if (country.length() == 0) {
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

		User user = new User();

		try {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("country", country);
			jsonUser.put("occupation", occupation);
			jsonUser.put("institution", institution);
			jsonUser.put("employee", employee);
			
			user.updateUsersProfileInfo(new ICallBack() {
				@Override
				public void callBackResultHandler(final Object object) {
					JSONObject jsonObject = (JSONObject)object;
					try {
						//Toast.makeText(getApplicationContext(), jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
						if(jsonObject.get("msg").toString().equalsIgnoreCase("UPDATE_OK")){
							 Intent intent = new Intent(mContext,BirthDaySettingActivity.class);
							 startActivity(intent);
							 finish();
						} else {
							// Login unsuccessful
							alert.showAlertDialog(ProfileInformationActivity.this, "Profile Edit failed..",
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
