package com.jalinsuara.android.maps;

import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;

/**
 * 
 * Show maps activity, it use google map api. 
 * 
 * @author tonoman3g
 * 
 */
@Deprecated
public class ShowGoogleMapActivity extends BaseFragmentActivity {

	private GoogleMap map;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_show_map);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.activity_show_map_mapfragment)).getMap();

		if (map != null) {

			// Marker hamburg = map.addMarker(new MarkerOptions()
			// .position(HAMBURG).title("Hamburg"));
			//
			// Marker kiel = map.addMarker(new MarkerOptions()
			// .position(KIEL)
			// .title("Kiel")
			// .snippet("Kiel is cool")
			// .icon(BitmapDescriptorFactory
			// .fromResource(R.drawable.ic_launcher)));
			// // Move the camera instantly to hamburg with a zoom of 15.
			// map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
			//
			// // Marker kiel = map.addMarker(new MarkerOptions()
			// // .position(KIEL)
			// // .title("Kiel")
			// // .snippet("Kiel is cool")
			// // .icon(BitmapDescriptorFactory
			// // .fromResource(R.drawable.ic_launcher)));
		}

		resetStatus();
		setStatusShowContent();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			finish();
			return true;
		}

		}
		return super.onOptionsItemSelected(item);
	}
}
