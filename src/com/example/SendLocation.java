package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



import android.os.AsyncTask;
import android.util.Log;

public class SendLocation extends AsyncTask<Void, Void, Void> {
	private String user;
	private double lat, lon;
	String res ="";
	RespCallback resCall;
	ArrayList<Event> nearByEvents = new ArrayList<Event>();
	public SendLocation(String user, double lat, double lon, RespCallback resCall) {
		this.user = user;
		this.lat = lat;
		this.lon = lon;
		res = "";
		this.resCall = resCall;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://shoutaround.herokuapp.com/submitLocation/");
		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
		nameValuePair.add(new BasicNameValuePair("lat", "" + lat ));
		nameValuePair.add(new BasicNameValuePair("long", "" + lon));
		nameValuePair.add(new BasicNameValuePair("hash", "" + User.hash ));
		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		}
		catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}
		try {
			HttpResponse response = httpClient.execute(httpPost);
			BufferedReader inBuffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String newLine = System.getProperty("line.separator");
			int lineCount = 0;
			int nearByEventCount = 0;
			while ((line = inBuffer.readLine()) != null) {
				Log.d("direct_line", line);
				if(lineCount++ == 0){
					nearByEventCount = Integer.parseInt(line);
					Log.d("event count", ""+nearByEventCount);
				}else{
					int eventId=0, eventCreator_id=0, eventCategory=0;
					double eventLongtitute=0, eventLatitute=0, eventRadius=0;
					Date eventCreationDate = null, eventExpiredDate=null;
					String eventTitle="";
					Log.d("line", line);
					StringTokenizer st = new StringTokenizer(line, ";");
					int coloumnCounter = 0; 
					while(st.hasMoreElements()){
						String coloumnEntry = (String) st.nextElement();
						Log.d("colomnString", ""+ coloumnEntry);
						Log.d("counter", "" + coloumnCounter);
						if(coloumnCounter == 0){
							eventId = Integer.parseInt(coloumnEntry);
							Log.d("id", ""+ eventId);
						}else if(coloumnCounter == 1){
							eventLatitute = Double.parseDouble(coloumnEntry);
							Log.d("lat", ""+ eventLatitute);
						}else if(coloumnCounter == 2){
							eventLongtitute = Double.parseDouble(coloumnEntry);
							Log.d("long", ""+ eventLongtitute);
						}else if(coloumnCounter == 3){
							eventCategory = Integer.parseInt(coloumnEntry);
							Log.d("cat", ""+ eventCategory);
						}else if(coloumnCounter == 4){
							eventCreator_id = Integer.parseInt(coloumnEntry);
						}else if(coloumnCounter == 5){
							eventTitle = coloumnEntry; 
						}else if(coloumnCounter ==6){
							//TODO: creationDate
							eventCreationDate = new Date(Long.parseLong(coloumnEntry));
						}else if(coloumnCounter == 7){
							//TODO: expiredDate
							eventExpiredDate = new Date(Long.parseLong(coloumnEntry)); 
						}     
						coloumnCounter++;
					}
					Event e = new Event(eventTitle,eventLongtitute, eventLatitute, eventRadius,eventCreationDate, eventExpiredDate, eventCategory, eventCreator_id);
					e.setId(eventId);
					nearByEvents.add(e);

				}
				stringBuffer.append(line + newLine);
				//	Log.d("EventResponses", nearByEvents.get(0).toString());
			}
			//Log.d("EventResponses", nearByEvents.get(0).toString() );



		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		resCall.callback_events(nearByEvents);
	}
}
