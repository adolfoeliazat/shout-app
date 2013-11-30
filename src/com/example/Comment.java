package com.example;

public class Comment {
	String content;
	String userName;
	int    userId;
	public Comment(int id, String userName, String content){
		this.content = content;
		this.userName =userName;
		this.userId = id;
	}
	public String toString(){
		return content + " by " + userName;
	}
}
