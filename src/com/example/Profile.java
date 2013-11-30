package com.example;

public class Profile {
	
	String name;
	String location;
	int popularity;
	String picURL;

	
	public Profile(String name, String location, String url, int starCount){
		this.name = name;
		this.location = location;
		popularity = starCount;
		picURL = url;
		
	}

}
