package com.example;

import java.util.Calendar;

import com.example.gpstracking.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidGPSTrackingActivity extends Activity {

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);

	ImageButton btnShowLocation;
	TextView locInfo;
	// GPSTracker class
	GPSTracker gps;
	Calendar c;
	private GoogleMap map;

	// aq bursasporu
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
/*
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
		Marker kiel = map.addMarker(new MarkerOptions().position(KIEL).title("Kiel").snippet("Kiel is cool").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

		// Move the camera instantly to hamburg with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
*/
		// c = Calendar.getInstance();
		btnShowLocation = (ImageButton) findViewById(R.id.btnShowLocation);
		// locTitle = (TextView) findViewById(R.id.textView1);
		locInfo = (TextView) findViewById(R.id.LocInfo);

		// show location button click event
		btnShowLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// create class object
				gps = new GPSTracker(AndroidGPSTrackingActivity.this);

				// check if GPS enabled
				if (gps.canGetLocation()) {

					double latitude = gps.getLatitude();
					double longitude = gps.getLongitude();

					SendLocation s = new SendLocation("testuser", latitude, longitude);
					s.execute();
					
					c = Calendar.getInstance();

					locInfo.setText(locInfo.getText() + "Lat: " + latitude + "- Long: " + longitude + " at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":"
							+ c.get(Calendar.SECOND) + "\n");
					
					LatLng mePos = new LatLng(latitude, longitude);
/*
					Marker meMarker = map.addMarker(new MarkerOptions().position(mePos).title("You"));
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(mePos, 15));
					map.animateCamera(CameraUpdateFactory.zoomTo(16), 5000, null);
*/
					// Zoom in, animating the camera.

					locInfo.setVisibility(View.VISIBLE);
					// locTitle.setVisibility(View.VISIBLE);

					// \n is for new line
					Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
				} else {
					// can't get location
					// GPS or Network is not enabled
					// Ask user to enable GPS/network in settings
					gps.showSettingsAlert();
				}

			}
		});
	}

}