package com.example;

import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



import android.os.AsyncTask;
import android.util.Log;

public class SendLocation extends AsyncTask<Void, Void, Void> {
	private String user;
	private double lat, lon;
	String res ="";
	RespCallback resCall;
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
		HttpGet get = new HttpGet("http://shoutaround.herokuapp.com/submitLocation/" + user + "/" + lat + "/" + lon);
		try {
			HttpResponse response = httpClient.execute(get);
			BufferedReader inBuffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			Log.d("sadasda", "asdasd");
//			char c;
//			do{
//				c = ((char)inBuffer.read());
//				if(c>0)res += c;
//				Log.d("dataa", res);
//			}
//			while(c>0); 
			
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String newLine = System.getProperty("line.separator");
			while ((line = inBuffer.readLine()) != null) {
				stringBuffer.append(line + newLine);
				Log.d("sadasda", line);
			}
			resCall.callback(stringBuffer.toString());
			

				
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
