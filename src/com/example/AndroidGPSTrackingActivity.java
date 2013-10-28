package com.example;

import java.util.Calendar;

import com.example.gpstracking.R;

import android.app.Activity;  
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton; 
import android.widget.TextView;
import android.widget.Toast;
  
public class AndroidGPSTrackingActivity extends Activity {
	
	ImageButton btnShowLocation;
	TextView locInfo;  
	// GPSTracker class  
	GPSTracker gps;
	Calendar c; 
 
	 // aq bursasporu
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);
        


     //  c = Calendar.getInstance(); 
       
        btnShowLocation = (ImageButton) findViewById(R.id.btnShowLocation);
        //locTitle		= (TextView)	findViewById(R.id.textView1);
        locInfo			= (TextView)	findViewById(R.id.LocInfo);
        
        // show location button click event   
        btnShowLocation.setOnClickListener(new View.OnClickListener() { 
			 
			@Override
			public void onClick(View arg0) {		
				// create class object
		        gps = new GPSTracker(AndroidGPSTrackingActivity.this);
 
				// check if GPS enabled		
		        if(gps.canGetLocation()){
		        	
		        	double latitude = gps.getLatitude(); 
		        	double longitude = gps.getLongitude();
		        	
		        	c = Calendar.getInstance();
		        	
		        	locInfo.setText(locInfo.getText() + "Lat: " + latitude + "- Long: " + longitude + " at "+ c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+"\n");
		        	
		        	locInfo.setVisibility(View.VISIBLE);
		        	//locTitle.setVisibility(View.VISIBLE);
		        	
		        	// \n is for new line
		        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
		        }else{
		        	// can't get location
		        	// GPS or Network is not enabled
		        	// Ask user to enable GPS/network in settings
		        	gps.showSettingsAlert();
		        }
				
			} 
		});  
    }
    
}