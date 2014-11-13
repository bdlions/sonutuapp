package com.sonuto.applications.xstreambanter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sonuto.rpc.AppXstreamBanter;
import com.sonuto.rpc.ICallBack;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Match;
import com.sportzweb.JSONObjectModel.Tournament;

public class MatchesActivity extends Activity{


	ArrayList<Match> matchesList = new ArrayList<Match>();
	Tournament tournament;
	ListView listView;
	Gson gS;
	TextView today;
	ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xtream_banter_all_league);	
		
		today = (TextView)findViewById(R.id.today);
		listView = (ListView)findViewById(R.id.list);
		gS = new Gson();
		
		
		Date date = new Date(0);
		
		today.setText(DateFormat.getDateInstance().format(date).toString());
		String selectedTournament = getIntent().getStringExtra("selectedTournament");
		tournament = gS.fromJson(selectedTournament, Tournament.class);
		setMatchListInSpinner();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(matchesList.size()>0){
					Intent i = new Intent(getApplicationContext(), MatchActivity.class);
		        	Match selectedMatch = matchesList.get(position);
		    		i.putExtra("selectedMatch",gS.toJson(selectedMatch));
		    		i.putExtra("selectedTournament", gS.toJson(tournament));		    		
		    		startActivity(i);
		    		finish();
	        	}
				
			}
		});
		
	}
	
	private void setMatchListInSpinner() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Processing match list..");
		pDialog.setCancelable(false);
		pDialog.show();  
		new AppXstreamBanter().getAllMatches(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {
				pDialog.dismiss();
				JSONObject jsonObject = (JSONObject) object;
				JSONArray matchList;
				try {
					matchList = jsonObject.getJSONArray("match_list");
					
					Gson gson = new Gson();
					int total_matches = matchList.length();
					for (int i = 0; i < total_matches; i++) {
						Match match = gson.fromJson(matchList.get(i).toString(), Match.class);
						matchesList.add(match);
					}
					
					ArrayAdapter<Match> matchesAdapter = new ArrayAdapter<Match>(MatchesActivity.this,android.R.layout.simple_list_item_1, matchesList);
					listView.setAdapter(matchesAdapter);
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
				pDialog.dismiss();
				System.out.println(object);
			}
		}, tournament.getId());		 
	}		
}
