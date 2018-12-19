package edu.valdosta.finalproject_backend;

import java.util.Date;

public abstract class Post {
	
	int postNum;
	String content;
	User poster;
	Date postDate;
	
	public Post() {
		postDate = new Date();
	}
	
	public int getPosterID() {
		return poster.getUserID();
	}

	public String getContent(){
		return content;
	}


}
