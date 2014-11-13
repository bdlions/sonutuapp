package com.sonuto.applications.xstreambanter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.sonuto.rpc.AppXstreamBanter;
import com.sonuto.rpc.ICallBack;
import com.sportzweb.R;
import com.sportzweb.JSONObjectModel.Sport;

public class SportsActivity extends Fragment {

	ArrayList<Sport> spinnerSportsList = new ArrayList<Sport>();
	Spinner spinnerSportsListView;
	ProgressDialog pDialog;
	View rootView;
	Activity activity;
	// JSONObject jsonObject = (JSONObject) getJsonObject();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_xtream_banter, null);
		activity = getActivity();
		
		
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_xtream_banter);

		spinnerSportsListView = (Spinner) rootView.findViewById(R.id.spinner_sports);
		setSportsListInSpinner();
		spinnerSportsListView
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,
							int arg2, long arg3) {

						if (spinnerSportsList.size() > 0) {
							Sport selectedSport = spinnerSportsList.get(arg2);
							Gson gS = new Gson();
							String target = gS.toJson(selectedSport);

							Intent i = new Intent(getActivity(),
									TournamentActivity.class);
							i.putExtra("selectedSport", target);

							startActivity(i);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		return rootView;
	}

	private void setSportsListInSpinner() {
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Processing sports list..");
		pDialog.setCancelable(false);
		pDialog.show();
		new AppXstreamBanter().getAllSports(new ICallBack() {
			@Override
			public void callBackResultHandler(final Object object) {
				pDialog.dismiss();
				JSONObject jsonObject = (JSONObject) object;
				JSONArray sportsList;
				try {
					sportsList = jsonObject.getJSONArray("sports_list");

					Gson gson = new Gson();
					int total_sports = sportsList.length();
					for (int i = 0; i < total_sports; i++) {
						Sport sport = gson.fromJson(sportsList.get(i)
								.toString(), Sport.class);
						spinnerSportsList.add(sport);
					}

					ArrayAdapter<Sport> sportsAdapter = new ArrayAdapter<Sport>(
							getActivity(),
							android.R.layout.simple_list_item_1,
							spinnerSportsList);
					// bind adapter and view
					spinnerSportsListView.setAdapter(sportsAdapter);

				} catch (JSONException e) {
					// give proper error message to the client
					e.printStackTrace();
				} catch (NullPointerException npe) {
					// give proper error message to the client
					npe.printStackTrace();
				}
			}

			@Override
			public void callBackErrorHandler(Object object) {
				pDialog.dismiss();
				System.out.println(object);
			}
		});
	}

}
