package com.sportzweb;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BusinessProfile;
import com.sonuto.rpc.register.User;
import com.sonuto.users.BusinessCategory;
import com.sonuto.users.Country;

import android.R.bool;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class BusinessRegistrationActivity extends Activity {

	Button businessRegInitialBtn, businessRegInfoBtn, businessRegExrtaInfoBtn,
			businessRegDescriptionBtn;

	FrameLayout business_reg_initial_step_layout,
			business_reg_info_step_layout, business_reg_extra_info_step_layout,
			business_reg_desc_step_layout;

	private EditText bCategory, bType, bName, bStreet, bCountry, bStreetName,
			bCity, bPostcode, bTelephone, bEmail, bWebsite, bOpeningTime,
			bRegisteredCompNo, bDescription;

	String businessType, businessCategory, businessName, businessStreet,
			businessCountry, businessCity, businessPostalcode,
			businessSreetName, businessDescription, businessEmail,
			businessTelephone, businessWebsite, businessOpeningTime,
			businessRegisteredCompNo;

	private Context mContext;

	// data source for auto complete text view
	private AutoCompleteTextView actCounties;
	// array adapter for counties
	ArrayAdapter<String> countriesAdapter;

	// array adapter for business category
	ArrayAdapter<String> bCategoryAdapter;

	private Spinner spinnerBCategory, spinnerBType;

	// array list for BusinessCategory spinner adapter
	private ArrayList<BusinessCategory> bcategoriesList;

	// array list for country Autocomplete adapter
	private ArrayList<Country> countryList;

	// process dialer
	ProgressDialog pDialog;

	String json, countries;
	JSONArray business_categories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_resigtration);

		mContext = this;
		initUi();
	}

	/*
	 * Initialize UI
	 */
	private void initUi() {
		
		spinnerBCategory = (Spinner) findViewById(R.id.business_category);
		spinnerBType = (Spinner) findViewById(R.id.business_type);
		
		bcategoriesList = new ArrayList<BusinessCategory>();
		BusinessProfile bProfile = new BusinessProfile();

		JSONObject jsonUser = new JSONObject();
		
		pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Fetching data..");
		pDialog.setCancelable(false);
		pDialog.show();
		
		bProfile.getBusinessProfileData(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {
				JSONObject jsonObject = (JSONObject) object;
				pDialog.dismiss();
				try {
					//business_categories = jsonObject.get("business_profile_type_list").toString();
					countries = jsonObject.get("country_list").toString();
					business_categories = jsonObject.getJSONArray("business_profile_type_list");
					
					Gson gson = new Gson();
					int size = business_categories.length();
					for (int i = 0; i < size; i++) {
						BusinessCategory catObj = gson.fromJson(business_categories.get(i).toString(), BusinessCategory.class);
						bcategoriesList.add(catObj);
					}
						
					ArrayAdapter<BusinessCategory> spinnerAdapter = new ArrayAdapter<BusinessCategory>(mContext, android.R.layout.simple_spinner_item,bcategoriesList);
					spinnerBCategory.setAdapter(spinnerAdapter);
			
					spinnerBCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

						@Override
                        public void onItemSelected(AdapterView<?> arg0,
                                View arg1, int position, long arg3) {
					
							// TODO Auto-generated method stub
							if(spinnerBCategory.getSelectedItem() != null){
								BusinessCategory businessCategory = (BusinessCategory)spinnerBCategory.getSelectedItem();
								ArrayList<BusinessCategory> bSubCategories = new ArrayList<BusinessCategory>();
								
								JsonArray subCategories = businessCategory.getSub_type_list();
								
								Gson gson = new Gson();
								int size = subCategories.size();
								
								for (int i = 0; i < size; i++) {
									BusinessCategory catObj = gson.fromJson(subCategories.get(i).toString(), BusinessCategory.class);
									bSubCategories.add(catObj);
									
									
									ArrayAdapter<BusinessCategory> spinnerSubCategoryAdapter = new ArrayAdapter<BusinessCategory>(mContext, android.R.layout.simple_spinner_item,bSubCategories);
									spinnerBType.setAdapter(spinnerSubCategoryAdapter);
								}
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
						
					});


				} 
				catch (JSONException e) {
					//give proper error message to the client
				}
				catch(NullPointerException npe){
					//give proper error message to the client
				}
			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				System.out.println(object);
			}
		}, jsonUser.toString());
			 
		
		bName = (EditText) findViewById(R.id.businessNameInputEdtTxt);
		bStreet = (EditText) findViewById(R.id.businessStreetNameInputEdtTxt);
		
		bStreetName = (EditText) findViewById(R.id.businessStreetEdtTxt);
		bCity = (EditText) findViewById(R.id.businessCityEdtTxt);
		bPostcode = (EditText) findViewById(R.id.businessPostEdtTxt);
		
		bTelephone = (EditText) findViewById(R.id.businessTelephoneNoEdtTxt);
		bEmail = (EditText) findViewById(R.id.businessEmailEdtTxt);
		bWebsite = (EditText) findViewById(R.id.businessWebsiteEdtTxt);
		bOpeningTime = (EditText) findViewById(R.id.businessOpeningTimeEdtTxt);
		bRegisteredCompNo = (EditText) findViewById(R.id.businessCompanyNoEdtTxt);
		
		bDescription = (EditText) findViewById(R.id.businessDescriptionEdtTxt);
		
		business_reg_initial_step_layout = (FrameLayout) findViewById(R.id.business_profile_reg_box1);
		business_reg_info_step_layout = (FrameLayout) findViewById(R.id.business_profile_reg_box2);
		business_reg_extra_info_step_layout = (FrameLayout) findViewById(R.id.business_profile_reg_box3);
		business_reg_desc_step_layout = (FrameLayout) findViewById(R.id.business_profile_reg_box4);
		
		businessRegInitialBtn = (Button) findViewById(R.id.btnBusinessRegIntial);
		businessRegInfoBtn = (Button) findViewById(R.id.btnBusinessRegInfo);
		businessRegExrtaInfoBtn = (Button) findViewById(R.id.btnBusinessRegExtraInfo);
		businessRegDescriptionBtn = (Button) findViewById(R.id.btnBusinessRegDescription);
		
		// initialize view
		actCounties = (AutoCompleteTextView) findViewById(R.id.actSelectCountry);
		countryList = new ArrayList<Country>();
	}

	public void businessProfileValue() {
		businessCategory = spinnerBCategory.getSelectedItem().toString();
		businessType = spinnerBType.getSelectedItem().toString().trim();
		businessName = bName.getText().toString().trim();
		businessStreet = bStreet.getText().toString().trim();

		businessCountry = actCounties.getText().toString().trim();
		businessSreetName = bStreetName.getText().toString().trim();
		businessCity = bCity.getText().toString().trim();
		businessPostalcode = bPostcode.getText().toString().trim();

		businessTelephone = bTelephone.getText().toString().trim();
		businessEmail = bEmail.getText().toString().trim();
		businessWebsite = bWebsite.getText().toString().trim();
		businessOpeningTime = bOpeningTime.getText().toString().trim();
		businessRegisteredCompNo = bRegisteredCompNo.getText().toString()
				.trim();

		businessDescription = bDescription.getText().toString().trim();
	}

	/*
	 * continue button click action for step 1
	 */
	public void bpRegInitialStep(View view) {
		businessProfileValue();
		if (isVerifiedInitialStep()) {
			business_reg_info_step_layout.setVisibility(View.VISIBLE);
			business_reg_initial_step_layout.setVisibility(View.GONE);
		}

	}

	public boolean isVerifiedInitialStep() {

		if (businessCategory.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessCategoryRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessType.length() == 0) {
			Toast.makeText(mContext, getString(R.string.businessTypeRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessName.length() == 0) {
			Toast.makeText(mContext, getString(R.string.businessNameRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessName.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessStreetRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}

	}

	/*
	 * continue button click action for step 2
	 */
	public void bpRegInfoStep(View view) {
		businessProfileValue();
		if (isVerifiedInfoStep()) {
			business_reg_extra_info_step_layout.setVisibility(View.VISIBLE);
			business_reg_info_step_layout.setVisibility(View.GONE);
		}

	}

	public boolean isVerifiedInfoStep() {
		if (businessCountry.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessCountryRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessSreetName.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessStreetNameRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessCity.length() == 0) {
			Toast.makeText(mContext, getString(R.string.businessCityRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessPostalcode.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessPostcodeRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	/*
	 * continue button click action for step 3
	 */
	public void bpRegExtraInfoStep(View view) {
		businessProfileValue();
		if (isVerifiedExtraInfoStep()) {
			business_reg_desc_step_layout.setVisibility(View.VISIBLE);
			business_reg_extra_info_step_layout.setVisibility(View.GONE);
		}

	}

	public boolean isVerifiedExtraInfoStep() {
		if (businessTelephone.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessTelephoneRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessEmail.length() == 0) {
			Toast.makeText(mContext, getString(R.string.businessEmailRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessWebsite.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessWebsiteRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessOpeningTime.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessOpeningHourRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessRegisteredCompNo.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessRegistredCompNoRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	/*
	 * continue button click action for final step
	 */
	public void bpRegDescriptionStep(View view) {
		businessProfileValue();
		if (isVerifiedDescriptionStep()) {
			// do ur job here
		}

	}

	public boolean isVerifiedDescriptionStep() {
		if (businessDescription.length() == 0) {
			Toast.makeText(mContext,
					getString(R.string.businessDescriptionRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
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
