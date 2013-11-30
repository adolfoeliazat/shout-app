package com.example;
import java.util.ArrayList;
import java.util.Date;


public class Event {
	
	int id, creator_id, category;
	double longtitute, latitute, radius;
	Date creationDate, expiredDate;
	String title,description;
	ArrayList<Comment> comments;
	
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
		comments = new ArrayList<Comment>();
	}
	
	void setId (int id){
		this.id = id;
	}
	
	public String toString(){
		String s = title + "\n";
		for(Comment c: comments){
			s += c + "\n";
		}
		return s ; // +..+.. + (TODO)
	}

	public void setDesc(String desc) {
		description = desc;
		
	}
	public void addComment(Comment c){
		comments.add(c);
	}

}
