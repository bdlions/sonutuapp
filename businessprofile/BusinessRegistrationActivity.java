package com.sonuto.businessprofile;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sampanit.sonutoapp.utils.AlertMessage;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BusinessProfile;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.users.BusinessCategory;
import com.sonuto.users.Country;
import com.sportzweb.LoginActivity;
import com.sportzweb.R;
import com.sportzweb.R.id;
import com.sportzweb.R.layout;
import com.sportzweb.R.string;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class BusinessRegistrationActivity extends Activity {

	Button businessRegInitialBtn, businessRegInfoBtn, businessRegExrtaInfoBtn,
			businessRegDescriptionBtn;

	FrameLayout business_reg_initial_step_layout,
			business_reg_info_step_layout, business_reg_extra_info_step_layout,
			business_reg_desc_step_layout;

	private EditText bName, bStreet, bStreetName,
			bCity, bPostcode, bTelephone, bEmail, bWebsite, bOpeningTime,
			bRegisteredCompNo, bDescription;

	String businessName, businessStreet,
			businessCity, businessPostalcode,
			businessSreetName, businessDescription, businessEmail,
			businessTelephone, businessWebsite, businessOpeningTime,
			businessRegisteredCompNo;
	int businessType, businessCategory, businessCountry;

	private Context mContext;
	private Country selectedCountry = null;

	// array adapter for counties and business category
	ArrayAdapter<String> countriesAdapter;
	ArrayAdapter<String> bCategoryAdapter;
	
	// view of spinner and AutoCompleteTextView
	private AutoCompleteTextView actCounties;
	private Spinner spinnerBCategory, spinnerBType;

	// array list for BusinessCategory spinner adapter and country AutoCompleteTextView adapter
	private ArrayList<BusinessCategory> bcategoriesList;
	private ArrayList<Country> countryList;

	// process dialer
	ProgressDialog pDialog;
	
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	JSONArray business_categories,countries;
	ISessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_resigtration);
		// Session Manager
		session = new SessionManager(getApplicationContext());
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
		countryList = new ArrayList<Country>();
		BusinessProfile bProfile = new BusinessProfile();

		JSONObject jsonBusinessObject = new JSONObject();
		
		List<BusinessCategory> bcl = new ArrayList<BusinessCategory>();
		bcl.add(new BusinessCategory());
		
		ArrayAdapter<BusinessCategory> spinnerAdapter = new ArrayAdapter<BusinessCategory>(mContext, android.R.layout.simple_spinner_item,bcl);
		spinnerBCategory.setAdapter(spinnerAdapter);
		
		
		ArrayAdapter<BusinessCategory> spinnerSubCategoryAdapter = new ArrayAdapter<BusinessCategory>(mContext, android.R.layout.simple_spinner_item,bcl);
		spinnerBType.setAdapter(spinnerSubCategoryAdapter);
		
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
					//String business_c = jsonObject.get("business_profile_type_list").toString();
					//countries = jsonObject.get("country_list").toString();
					
					countries = jsonObject.getJSONArray("country_list");
					
					Gson gson = new Gson();
					int total_country = countries.length();
					for (int i = 0; i < total_country; i++) {
						Country countryObj = gson.fromJson(countries.get(i).toString(), Country.class);
						countryList.add(countryObj);
					}
					
					ArrayAdapter<Country> countriesAdapter = new ArrayAdapter<Country>(mContext,android.R.layout.simple_list_item_1, countryList);
					// bind adapter and view
					actCounties.setAdapter(countriesAdapter);
					
					
					business_categories = jsonObject.getJSONArray("business_profile_type_list");
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
					e.printStackTrace();
				}
				catch(NullPointerException npe){
					//give proper error message to the client
					npe.printStackTrace();
				}
			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				System.out.println(object);
			}
		}, jsonBusinessObject.toString());
			 
		
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
		actCounties.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectedCountry = (Country) parent.getAdapter().getItem(position);
			}
			
			
		});
		
	}
	
	// get the value from all input field
	public void businessProfileValue() {
		
		BusinessCategory businessCategoryValue =(BusinessCategory)spinnerBCategory.getSelectedItem();
		BusinessCategory businessTypeValue = (BusinessCategory) spinnerBType.getSelectedItem();
		
		businessCategory = businessCategoryValue == null ? 0 : businessCategoryValue.getId();
		businessType = businessTypeValue == null ? 0 : businessTypeValue.getId();
		
		businessName = bName.getText().toString().trim();
		businessStreet = bStreet.getText().toString().trim();
		
		businessCountry = selectedCountry == null ? 0 : selectedCountry.getId();
		businessSreetName = bStreetName.getText().toString().trim();
		businessCity = bCity.getText().toString().trim();
		businessPostalcode = bPostcode.getText().toString().trim();

		businessTelephone = bTelephone.getText().toString().trim();
		businessEmail = bEmail.getText().toString().trim();
		businessWebsite = bWebsite.getText().toString().trim();
		businessOpeningTime = bOpeningTime.getText().toString().trim();
		businessRegisteredCompNo = bRegisteredCompNo.getText().toString().trim();

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

		if (businessCategory <= 0) {
			Toast.makeText(mContext,
					getString(R.string.businessCategoryRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (businessType <= 0) {
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
		if (businessCountry < 0) {
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
			
			JSONObject jsonBusinessProfile = new JSONObject();
			
			try {
				jsonBusinessProfile.put("user_id", session.getUserId());
				//jsonBusinessProfile.put("user_id", 5);
				jsonBusinessProfile.put("business_profile_type", businessCategory);
				jsonBusinessProfile.put("business_profile_sub_type", businessType);
				jsonBusinessProfile.put("business_name", businessName);
				jsonBusinessProfile.put("street_name", businessStreet);
				jsonBusinessProfile.put("business_country", businessCountry);
				jsonBusinessProfile.put("business_city", businessCity);
				jsonBusinessProfile.put("post_code", businessPostalcode);
				jsonBusinessProfile.put("telephone", businessTelephone);
				jsonBusinessProfile.put("business_email", businessEmail);
				jsonBusinessProfile.put("website_address", businessWebsite);
				jsonBusinessProfile.put("business_hour", businessOpeningTime);
				jsonBusinessProfile.put("registered_company_number", businessRegisteredCompNo);
				jsonBusinessProfile.put("business_description", businessDescription);
				
				BusinessProfile bprofile = new BusinessProfile();
				bprofile.regiserBusinessProfile(new ICallBack() {
					
					@Override
					public void callBackResultHandler(Object object) {
						JSONObject jsonObject = (JSONObject)object;
						try {
							if(jsonObject.get("status").toString().equalsIgnoreCase("1")) {
								//alert.showAlertDialog(mContext, "Business Profile Registration complete..","Registration successfull", false);
								//AlertMessage.showMessage(mContext, "Business Profile Registration complete..","Registration successfull");
								JSONObject bObject = jsonObject.getJSONObject("business_profile_info");
								Intent i = new Intent(mContext, BusinessProfileActivity.class);
								i.putExtra("business_profile_info", bObject.toString());
								startActivity(i);
								finish();
							} else {

								// Registration unsuccessful
								//alert.showAlertDialog(BusinessRegistrationActivity.this, "Business Profile Registration failed..","Registration unsuccessfull", false);
								AlertMessage.showMessage(mContext, "Business Profile Registration failed..","Registration unsuccessfull");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					
					@Override
					public void callBackErrorHandler(Object object) {
						System.out.println(object);
					}
				}, jsonBusinessProfile.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
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
