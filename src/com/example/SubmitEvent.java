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

public class SubmitEvent extends AsyncTask<Void, Void, Void> {
	private String user;
	private double lat, lon;
	String res ="";
	RespCallback resCall;
	int category, creator_id;
	String title, description;
	Date creationdate, expiredate;

	public SubmitEvent(String user, String title, String description, double lat, double lon, int category, int creator_id, Date creationDate, Date expiredDate, RespCallback resCall) {
		
		this.lat = lat;
		this.lon = lon;
		this.user = user;
		this.title = title;
		this.description = description;
		this.category = category;
		this.creator_id = creator_id;
		this.creationdate = creationDate;
		this.expiredate = expiredDate;
		res = "";
		this.resCall = resCall;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://shoutaround.herokuapp.com/submitEvent/");
		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(10);
		nameValuePair.add(new BasicNameValuePair("lat", "" + lat ));
		nameValuePair.add(new BasicNameValuePair("long", "" + lon));
		nameValuePair.add(new BasicNameValuePair("user", "" + user ));
		nameValuePair.add(new BasicNameValuePair("title", "" + title));
		nameValuePair.add(new BasicNameValuePair("description", "" + description ));
		nameValuePair.add(new BasicNameValuePair("category", "" + category));
		nameValuePair.add(new BasicNameValuePair("creator", "" + creator_id ));
		nameValuePair.add(new BasicNameValuePair("creationdate", "" + creationdate));
		nameValuePair.add(new BasicNameValuePair("expireddate", "" + expiredate ));
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
				stringBuffer.append(line + newLine);
				Log.d("sadasda", line);
			}
			if(line.equalsIgnoreCase("OK"))resCall.callback_ack();



		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
