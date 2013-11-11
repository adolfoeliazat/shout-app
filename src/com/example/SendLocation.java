package com.example;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class SendLocation extends AsyncTask<Void, Void, Void> {
	private String user;
	private double lat, lon;

	public SendLocation(String user, double lat, double lon) {
		this.user = user;
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://shoutaround.herokuapp.com/submitLocation/" + user + "/" + lat + "/" + lon);
		try {
			HttpResponse response = httpClient.execute(get);
			InputStream is = response.getEntity().getContent();			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
