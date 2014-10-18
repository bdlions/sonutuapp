package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONException;

import com.sonuto.utils.UtilityJsonParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class XtreamBanterTournamentActivity extends Activity implements OnItemSelectedListener{


	ArrayList<String> spinnerTournamentsList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_tournament);
		
		Spinner spinnerTournamentsListView = (Spinner)findViewById(R.id.spinner_Month);

		Bundle extras = getIntent().getExtras();
		String selectedSport = "";
		String jsonString = "";
		if (extras != null) {
		    selectedSport = extras.getString("selectedSport");		
		    jsonString = getTournamentListJSON(selectedSport);
		
			try {
				spinnerTournamentsList = UtilityJsonParser.jsonStringToArray(jsonString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		setListToSpinner(spinnerTournamentsList,spinnerTournamentsListView);
		
	}
	
	private String getTournamentListJSON(String selectedSport){
		if(selectedSport.equalsIgnoreCase("Football"))
			return "[Select_Tournament, FCI, Euro, WCUP]";
		else
			return "[Select_Tournament, Cric, Bric, BPL]";
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
		String selectedSport = spinnerTournamentsList.get(arg2);
		switch (arg2) {
        case 0:
            break;
            
        case 1:
             Intent ir = new Intent();
             //ir.setClass(CurrentActivity.this, Results.class);
             //startActivity(ir);
            break;
        case 2:
             Intent ic = new Intent();
             //ic.setClass(CurrentActivity.this, Clasification.class);
             //startActivity(ic);
             break;
		}
        //i.setClass(this, NewsFeedActivity.class);
         //startActivity(i);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
		
}
