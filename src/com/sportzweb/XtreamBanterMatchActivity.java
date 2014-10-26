package com.sportzweb;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.Matches;
import com.sportzweb.JSONObjectModel.Match;
import com.sportzweb.JSONObjectModel.Tournament;

public class XtreamBanterMatchActivity extends Activity{


	ArrayList<String> matchesList = new ArrayList<String>();
	Tournament tournament;
	ListView listView;
	Gson gS;
	TextView today;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_all_league);	
		
		today = (TextView)findViewById(R.id.today);
		listView = (ListView)findViewById(R.id.list);
		gS = new Gson();
		
		Date date = new Date();
		
		today.setText(date.toString());
		String src = getIntent().getStringExtra("selectedTournament");
		tournament = gS.fromJson(src, Tournament.class);
		getAndSetLiistInSpinner();
		
	}
	
	private void getAndSetLiistInSpinner() {
		 Matches matchList = new Matches();
		 JSONObject jsonObject = new JSONObject();
		 matchList.getMatchesList(new ICallBack() {
				@Override
				public void callBackResultHandler(final Object object) {
					JSONObject jsonObject = (JSONObject) object;
					JSONArray matchList;
					try {
						matchList = jsonObject.getJSONArray("match_list");
						
						Gson gson = new Gson();
						int total_matches = matchList.length();
						for (int i = 0; i < total_matches; i++) {
							Match match = gson.fromJson(matchList.get(i).toString(), Match.class);
							matchesList.add(match.toString());
						}
						
						ArrayAdapter<String> matchesAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, matchesList);
						//this.setListAdapter(matchesAdapter);
						listView.setAdapter(matchesAdapter);
						//spinnerMatchesListView.setAdapter(matchesAdapter);


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
			}, gS.toJson(tournament));		 
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
