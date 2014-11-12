package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.ServiceDirectoryApp;
import com.sonuto.utils.component.ServiceCategoryArrayListFragment;
import com.sonuto.utils.component.ServiceCategoryCustomAdapter;
import com.sonuto.utils.component.ServiceItemCustomAdapter;
import com.sportzweb.JSONObjectModel.ServiceCategory;
import com.sportzweb.JSONObjectModel.ServiceItem;


public class ServiceDirectoryActivity extends Fragment{
	// process dialer
	ProgressDialog pDialog;
	View rootView;
	Activity activity;
	Button btnSDPostCode,btnSDCategorySelect,rightArrowImgView;
	FrameLayout sd_postcode_setp, sd_category_setp,sd_result_list_setp;
	String city_or_postcode = "London";
	EditText servieDirectoryEdtTxt;
	TextView serviceSearchHeading;
	
	ListView serviceCategoryListView,serviceDirectoryResultList;
	private ArrayList<ServiceCategory> serviceCategoryItem = new ArrayList<ServiceCategory>();
	private ArrayList<ServiceItem> serviceResultItem = new ArrayList<ServiceItem>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		rootView = inflater.inflate(R.layout.activity_service_app, null);
		activity = getActivity();
		
		ServiceDirectoryApp serviceCategory = new ServiceDirectoryApp();
		serviceCategory.getServiceCategory(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object object) {
				JSONObject jsonObj = (JSONObject) object;
				try {
					JSONArray service_category_list = jsonObj.getJSONArray("service_category_list");
					JSONArray service_list = jsonObj.getJSONArray("service_list");
					Gson gson = new Gson();
					
					// this portion is for service category
					serviceCategoryItem = new ArrayList<ServiceCategory>();
					int total_service_category = service_category_list.length();
					for(int i=0;i<total_service_category;i++) {
						ServiceCategory item = gson.fromJson(service_category_list.get(i).toString(), ServiceCategory.class);
						serviceCategoryItem.add(item);
					}
					ServiceCategoryCustomAdapter adapter = new ServiceCategoryCustomAdapter(getActivity(), serviceCategoryItem);
					serviceCategoryListView.setAdapter(adapter);

					/*if (getFragmentManager().findFragmentById(R.id.serviceCategoryListFragmentLayout) == null) {
						
						ServiceCategoryArrayListFragment sList = new ServiceCategoryArrayListFragment();
			            sList.setServiceCategoryItem(serviceCategoryItem);
			            FragmentManager fm = getFragmentManager();
					    FragmentTransaction ft = fm.beginTransaction();
			            ft.add(R.id.serviceCategoryListFragmentLayout, sList);
			            ft.commit();
					    
			            //getFragmentManager().beginTransaction().add(R.id.serviceCategoryListFragmentLayout, sList).commit();
			        }*/
					
					
					//this portion is for service result
					serviceResultItem = new ArrayList<ServiceItem>();
					int total_service_result = service_list.length();
					for(int i=0;i<total_service_result;i++) {
						ServiceItem serviceItem = gson.fromJson(service_list.get(i).toString(), ServiceItem.class);
						serviceResultItem.add(serviceItem);
					}
					
					ServiceItemCustomAdapter serviceResultAdapter = new ServiceItemCustomAdapter(getActivity(), serviceResultItem);
					serviceDirectoryResultList.setAdapter(serviceResultAdapter);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			@Override
			public void callBackErrorHandler(Object object) {
				
				
			}
		}, city_or_postcode);
		
		// initialize all view component
		// FrameLayout
		sd_postcode_setp = (FrameLayout) rootView.findViewById(R.id.service_directory_postcode_setp);
		sd_category_setp = (FrameLayout) rootView.findViewById(R.id.service_directory_category_setp);
		sd_result_list_setp = (FrameLayout) rootView.findViewById(R.id.service_directory_result_list_setp);
		
		//ListView
		serviceCategoryListView = (ListView) rootView.findViewById(R.id.serviceCategoryListview);
		serviceDirectoryResultList = (ListView) rootView.findViewById(R.id.serviceDirectoryResultList);
		
		//button
		btnSDPostCode = (Button) rootView.findViewById(R.id.btnSDPostCode);
		btnSDCategorySelect = (Button) rootView.findViewById(R.id.btnSDCategorySelect);
		
		servieDirectoryEdtTxt = (EditText) rootView.findViewById(R.id.servieDirectoryEdtTxt);
		serviceSearchHeading = (TextView) rootView.findViewById(R.id.serviceSearchHeading);
		rightArrowImgView = (Button) rootView.findViewById(R.id.rightArrowImgView);
		
		
		btnSDPostCode.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if(isVerifiedCityPostcodeStep()) {
            		serviceSearchHeading.setText("Service Near : " + servieDirectoryEdtTxt.getText());
            		sd_result_list_setp.setVisibility(View.VISIBLE);
            		sd_postcode_setp.setVisibility(View.GONE);
        		}
            	
            }
        });
		
		rightArrowImgView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//if(isVerifiedCityPostcodeStep()) {
            		sd_category_setp.setVisibility(View.VISIBLE);
            		sd_result_list_setp.setVisibility(View.GONE);
            		
        		}
            	
            //}
        });
		
		
		btnSDCategorySelect.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if(isVerifiedCityPostcodeStep()) {
            		serviceSearchHeading.setText("");
            		sd_result_list_setp.setVisibility(View.VISIBLE);
            		sd_category_setp.setVisibility(View.GONE);
        		}
            	
            }
        });
		
		
		// gets the activity's default ActionBar
	    ActionBar actionBar = getActivity().getActionBar();
	    actionBar.show();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		return rootView;
	}
	
	/**
	 * createBlogTitleStep validation method
	 * @return boolean value
	 */
	public boolean isVerifiedCityPostcodeStep() {
		if (servieDirectoryEdtTxt.length() == 0) {
			Toast.makeText(getActivity().getBaseContext(), getString(R.string.serviceCityOrPostcodeRequired),
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

}
