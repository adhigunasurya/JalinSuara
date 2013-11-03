package com.jalinsuara.android.maps;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jalinsuara.android.BaseFragmentActivity;
import com.jalinsuara.android.R;

public class TestMapsActivity extends BaseFragmentActivity {

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	
	 static final LatLng KIEL = new LatLng(53.551, 9.993);
	
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_test_map);

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		if (map != null) {

			Marker hamburg = map.addMarker(new MarkerOptions()
					.position(HAMBURG).title("Hamburg"));
			
			Marker kiel = map.addMarker(new MarkerOptions()
	        .position(KIEL)
	        .title("Kiel")
	        .snippet("Kiel is cool")
	        .icon(BitmapDescriptorFactory
	            .fromResource(R.drawable.ic_launcher)));
			// Move the camera instantly to hamburg with a zoom of 15.
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

			// Marker kiel = map.addMarker(new MarkerOptions()
			// .position(KIEL)
			// .title("Kiel")
			// .snippet("Kiel is cool")
			// .icon(BitmapDescriptorFactory
			// .fromResource(R.drawable.ic_launcher)));
		}

		resetStatus();
		setStatusShowContent();

	}
}
