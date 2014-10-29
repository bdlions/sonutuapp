package com.sportzweb.xtreambanter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.Sports;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Sport;
import com.sportzweb.R.id;
import com.sportzweb.R.layout;


public class SportsActivity extends Activity{

	ArrayList<Sport> spinnerSportsList = new ArrayList<Sport>();
	Spinner spinnerSportsListView;
	
	//JSONObject jsonObject = (JSONObject) getJsonObject();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter);
		
		spinnerSportsListView = (Spinner)findViewById(R.id.spinner_sports);
		getAndSetLiistInSpinner();
		spinnerSportsListView.setOnItemSelectedListener(new OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

	        	if(spinnerSportsList.size()>0){
		        	Sport selectedSport = spinnerSportsList.get(arg2);
		    		Gson gS = new Gson();
		    		String target = gS.toJson(selectedSport);
		        	
		    		Intent i = new Intent(getApplicationContext(), TournamentActivity.class);
		    		i.putExtra("selectedSport",target);
		    		
		    		startActivity(i);
	        	
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

		/*String jsonString = "[Select_sports,Football,Cricket,Hockey]";		
		
		try {
			spinnerSportsList = UtilityJsonParser.jsonStringToArray(jsonString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//setListToSpinner(spinnerSportsList,spinnerSportsListView);
		
	}	
		
	 private void getAndSetLiistInSpinner() {
		 Sports sportsList = new Sports();
		 JSONObject jsonObject = new JSONObject();
		 sportsList.getSportsList(new ICallBack() {
				@Override
				public void callBackResultHandler(final Object object) {
					JSONObject jsonObject = (JSONObject) object;
					JSONArray sportsList;
					try {
						sportsList = jsonObject.getJSONArray("sports_list");
						
						Gson gson = new Gson();
						int total_sports = sportsList.length();
						for (int i = 0; i < total_sports; i++) {
							Sport sport = gson.fromJson(sportsList.get(i).toString(), Sport.class);
							spinnerSportsList.add(sport);
						}
						
						ArrayAdapter<Sport> sportsAdapter = new ArrayAdapter<Sport>(getApplicationContext(),android.R.layout.simple_list_item_1, spinnerSportsList);
						// bind adapter and view
						spinnerSportsListView.setAdapter(sportsAdapter);

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
			},jsonObject.toString());		 
	}
		
}
