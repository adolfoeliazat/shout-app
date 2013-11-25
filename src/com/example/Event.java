package com.example;
import java.util.Date;


public class Event {
	
	int id, creator_id, category;
	double longtitute, latitute, radius;
	Date creationDate, expiredDate;
	String title,description;
	
	public Event(String title, double longtitute, double latitute, double radius, Date creationDate, Date expiredDate,
			 int cat, int creator){
		this.title = title;
		this.longtitute = longtitute;
		this.latitute = latitute;
		this.radius = radius;
		this.creationDate = creationDate;
		this.expiredDate = expiredDate;	
		category = cat;
		creator_id = creator; 
		description = "";
	}
	
	void setId (int id){
		this.id = id;
	}
	
	public String toString(){
		return title; // +..+.. + (TODO)
	}

	public void setDesc(String desc) {
		description = desc;
		
	}
}
