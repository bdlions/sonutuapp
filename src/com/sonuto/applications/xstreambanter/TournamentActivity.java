package com.sonuto.applications.xstreambanter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sonuto.rpc.AppXstreamBanter;
import com.sonuto.rpc.ICallBack;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Sport;
import com.sportzweb.JSONObjectModel.Tournament;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
	ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_tournament);
		spinnerTournamentsListView = (Spinner)findViewById(R.id.spinner_tournaments);
		String selectedSport = getIntent().getStringExtra("selectedSport");
		sport = gS.fromJson(selectedSport, Sport.class);
		setTournamentListInSpinner();
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
		    		finish();
	        	}
	        }
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub				
			}
		});
	}
	
	private void setTournamentListInSpinner() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Processing tournament list..");
		pDialog.setCancelable(false);
		pDialog.show(); 
		new AppXstreamBanter().getAllTournaments(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {
				pDialog.dismiss();
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
				pDialog.dismiss();
				System.out.println(object);
			}
		}, sport.getId());		 
	}			
}
