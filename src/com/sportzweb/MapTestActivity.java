package com.sportzweb;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;

public class MapTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_test);
		// Get a handle to the Map Fragment
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		LatLng dhaka = new LatLng(23.709921000000000000, 90.407143000000020000);

		LatLng mohakhali = new LatLng( 23.777628200000000000, 90.405449800000040000);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 13));

        map.addMarker(new MarkerOptions()
                .title("Dhaka")
                .snippet("The most populous city in Bangladesh.")
                .position(dhaka));
        
        map.addMarker(new MarkerOptions()
        .title("Mohakhali")
        .snippet("Center of Dhaka.")
        .position(mohakhali));

	}
}
