package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class GetProfile extends AsyncTask<Void, Void, Void> {

	String res ="";
	ProfileCallback resCall;
	int userId;
	Profile p = null;

	public GetProfile(ProfileCallback resCall, int userId ) {
		this.resCall = resCall;
		this.userId = userId;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://shoutaround.herokuapp.com/getProfile/");
		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("userId", "" + userId));
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
			String name = "";
			String location = "";
			int popularity = 0;
			String resimURL = ""; 

			Event e = null;
			while ((line = inBuffer.readLine()) != null) {

				if(lineCount == 1){
					name = line;
				}else if(lineCount ==2){
					location = line;
				}else if(lineCount ==3){
					popularity = Integer.parseInt(line);
				}else if(lineCount ==4){
					resimURL= (line);
				}
				lineCount++;
				stringBuffer.append(line + newLine);
				Log.d("sadasda", line);
			}
			p = new Profile (name, location, resimURL,popularity);


		}

		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		resCall.callback_profilInfo(p);
	}
}