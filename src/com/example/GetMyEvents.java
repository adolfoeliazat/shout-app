package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class GetMyEvents extends AsyncTask<Void, Void, Void> {
	private String user;

	String res ="";
	RespCallback resCall;
	ArrayList<Event> myEvents = new ArrayList<Event>();
	public GetMyEvents(String user, RespCallback resCall) {
		this.user = user;
		this.resCall = resCall;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://shoutaround.herokuapp.com/getMyEvents/");
		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
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
				if(lineCount++ == 0){
					nearByEventCount = Integer.parseInt(line);
				}else{
					
					for(int i = 0; i<nearByEventCount; i++){
						int eventId=0, eventCreator_id=0, eventCategory=0;
						double eventLongtitute=0, eventLatitute=0, eventRadius=0;
						Date eventCreationDate = null, eventExpiredDate=null;
						String eventTitle="";

						StringTokenizer st = new StringTokenizer(line, ";");
						int coloumnCounter = 0; 
						while(st.hasMoreElements()){
							String coloumnEntry = (String) st.nextElement();
							if(coloumnCounter++ == 0){
								eventId = Integer.parseInt(coloumnEntry);
							}else if(coloumnCounter++==1){
								eventLatitute = Integer.parseInt(coloumnEntry);
							}else if(coloumnCounter++==2){
								eventLongtitute = Integer.parseInt(coloumnEntry);
							}else if(coloumnCounter++==3){
								eventCategory = Integer.parseInt(coloumnEntry);
							}else if(coloumnCounter++==4){
								eventCreator_id = Integer.parseInt(coloumnEntry);
							}else if(coloumnCounter++==5){
								eventTitle = coloumnEntry;
							}else if(coloumnCounter++==6){
								//TODO: creationDate
							}else if(coloumnCounter++==7){
								//TODO: expiredDate
							}    
						}
						Event e = new Event(eventTitle,eventLongtitute, eventLatitute, eventRadius,eventCreationDate, eventExpiredDate, eventCategory, eventCreator_id);
						e.setId(eventId);
						myEvents.add(e);
					}
				}
				stringBuffer.append(line + newLine);
				Log.d("sadasda", line);
			}
			resCall.callback_events(myEvents);



		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
