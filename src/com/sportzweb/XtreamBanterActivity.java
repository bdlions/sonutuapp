package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.sonuto.utils.UtilityJsonParser;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;


public class XtreamBanterActivity extends Activity implements OnItemSelectedListener{

	ArrayList<String> spinnerSportsList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter);
		
		Spinner spinnerSportsListView = (Spinner)findViewById(R.id.spinner_Month);
		
		String jsonString = "[Select_sports,Football,Cricket,Hockey]";		
		
		try {
			spinnerSportsList = UtilityJsonParser.jsonStringToArray(jsonString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setListToSpinner(spinnerSportsList,spinnerSportsListView);
		
	}	
		
	 private void setListToSpinner(ArrayList<String> spinnerSportsList,	Spinner spinnerSportsListView) {
		 	
		ArrayAdapter<String> sportsList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinnerSportsList);
		sportsList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSportsListView.setAdapter(sportsList);
		spinnerSportsListView.setOnItemSelectedListener(this);
			 
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		String selectedSport = spinnerSportsList.get(arg2);
		Intent i = new Intent(getApplicationContext(), XtreamBanterTournamentActivity.class);
		i.putExtra("selectedSport",selectedSport);
		
		if(arg2 > 0){
			startActivity(i);
		}
	}	

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
		
}
