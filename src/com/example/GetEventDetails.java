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

public class GetEventDetails extends AsyncTask<Void, Void, Void> {
	private String user;

	String res ="";
	RespCallback resCall;
	ArrayList<Event> myEvents = new ArrayList<Event>();
	int eId;
	public GetEventDetails(String user, int id, RespCallback resCall) {
		this.user = user;
		eId = id;
		this.resCall = resCall;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://shoutaround.herokuapp.com/getEvent/");
		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("eventId", "" + eId ));
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
			int lineCount = 1;
			int eventCreator_id=0, eventCategory=0;
			double eventLongtitute=0, eventLatitute=0, eventRadius=0;
			Date eventCreationDate = null, eventExpiredDate=null;
			String eventTitle="", desc = "";

			while ((line = inBuffer.readLine()) != null) {

				if(lineCount++==1){
					eventLatitute = Integer.parseInt(line);
				}else if(lineCount++==2){
					eventLongtitute = Integer.parseInt(line);
				}else if(lineCount++==3){
					eventCategory = Integer.parseInt(line);
				}else if(lineCount++==4){
					eventCreator_id = Integer.parseInt(line);
				}else if(lineCount++==5){
					eventTitle = line;
				}else if(lineCount++==6){
					//TODO: creationDate
				}else if(lineCount++==7){
					//TODO: expiredDate
				}else if(lineCount++==8){
					desc = line;
				}   
				stringBuffer.append(line + newLine);
				Log.d("sadasda", line);
			}

			Event e = new Event(eventTitle,eventLongtitute, eventLatitute, eventRadius,eventCreationDate, eventExpiredDate, eventCategory, eventCreator_id);
			e.setId(eId);
			e.setDesc(desc);
			myEvents.add(e);
			resCall.callback_events(myEvents);

		}

		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
