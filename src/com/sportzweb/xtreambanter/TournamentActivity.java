package com.sportzweb.xtreambanter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.Tournaments;
import com.sonuto.utils.UtilityJsonParser;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Sport;
import com.sportzweb.JSONObjectModel.Tournament;
import com.sportzweb.R.id;
import com.sportzweb.R.layout;

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

public class TournamentActivity extends Activity{


	ArrayList<Tournament> spinnerTournamentsList = new ArrayList<Tournament>();
	Spinner spinnerTournamentsListView;
	Sport sport;
	Gson gS = new Gson();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_tournament);
		spinnerTournamentsListView = (Spinner)findViewById(R.id.spinner_tournaments);
		String src = getIntent().getStringExtra("selectedSport");
		sport = gS.fromJson(src, Sport.class);
		getAndSetLiistInSpinner();
		spinnerTournamentsListView.setOnItemSelectedListener(new OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

	        	if(spinnerTournamentsList.size()>0){
		        	Tournament selectedTournament = spinnerTournamentsList.get(arg2);
		    		Gson gS = new Gson();
		    		String target = gS.toJson(selectedTournament);
		        	
		    		final Intent i = new Intent(getApplicationContext(), MatchesActivity.class);
		    		i.putExtra("selectedTournament",target);
		    		
		    		startActivity(i);
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

		
		/*Bundle extras = getIntent().getExtras();
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
		*/
	}
	
	private void getAndSetLiistInSpinner() {
		 Tournaments tournamentsList = new Tournaments();
		 JSONObject jsonObject = new JSONObject();
		 tournamentsList.getTournamentsList(new ICallBack() {
				@Override
				public void callBackResultHandler(final Object object) {
					JSONObject jsonObject = (JSONObject) object;
					JSONArray tournamentsList;
					try {
						tournamentsList = jsonObject.getJSONArray("tournament_list");
						
						Gson gson = new Gson();
						int total_tournaments = tournamentsList.length();
						for (int i = 0; i < total_tournaments; i++) {
							Tournament tournament = gson.fromJson(tournamentsList.get(i).toString(), Tournament.class);
							spinnerTournamentsList.add(tournament);
						}
						
						ArrayAdapter<Tournament> tournamentsAdapter = new ArrayAdapter<Tournament>(TournamentActivity.this,android.R.layout.simple_list_item_1, spinnerTournamentsList);
						// bind adapter and view
						spinnerTournamentsListView.setAdapter(tournamentsAdapter);

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
			}, gS.toJson(sport));		 
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
		// TODO Auto-generated method stub
		
	}
			
}
