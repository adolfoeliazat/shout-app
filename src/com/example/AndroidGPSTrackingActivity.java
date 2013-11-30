package com.example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidGPSTrackingActivity extends Activity{

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);

	ImageButton btnShowLocation;
	Button btnSendLoc, btnAddEvent, btnGetEventDetails;
	TextView locInfo;
	EditText eT;
	// GPSTracker class
	GPSTracker gps;
	Calendar c;
	private GoogleMap map;

	// aq bursasporu
	static double latitude = 0;
	static double longitude = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final RespCallback resp = new RespCallback(){
			@Override
			public void callback_events(ArrayList<Event> nearByEvents) {
				for(int i= 0 ; i< nearByEvents.size(); i++){
					Log.d("Event: " + i, nearByEvents.get(i).toString());
					eT.setText(eT.getText().toString()+ "-" + nearByEvents.get(i).toString());
				}

			}

			@Override
			public void callback_ack() {
				// TODO Auto-generated method stub
				eT.setText("ok");
				
			}};
			
			
	final ProfileCallback pCall = new ProfileCallback() {
		
		@Override
		public void callback_profilInfo(Profile profil) {
			// TODO Auto-generated method stub
			eT.setText(profil.name + " from " + profil.location);
			
		}
	};
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
			btnAddEvent = (Button) 	findViewById(R.id.AddEvent);
			btnSendLoc  = (Button) 	findViewById(R.id.buttonSendLoc);
			btnGetEventDetails = (Button) findViewById(R.id.GetEventDetails);
			eT	= (EditText) findViewById(R.id.editTextLocation);

			btnShowLocation = (ImageButton) findViewById(R.id.btnShowLocation);
			// locTitle = (TextView) findViewById(R.id.textView1);
			locInfo = (TextView) findViewById(R.id.LocationResult); 

			btnSendLoc.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					GetProfile s = new GetProfile (pCall,1);
							s.execute();
					Log.d("Asdas", s.res);

				}
			});
			

			// show location button click event
			btnShowLocation.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// create class object
					gps = new GPSTracker(AndroidGPSTrackingActivity.this);

					// check if GPS enabled
					if (gps.canGetLocation()) {

						latitude = gps.getLatitude();
						longitude = gps.getLongitude();

						c = Calendar.getInstance();

						locInfo.setText("Lat: " + latitude + "- Long: " + longitude + " at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":"
								+ c.get(Calendar.SECOND));

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