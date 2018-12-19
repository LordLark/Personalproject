package edu.valdosta.finalproject_backend;

public class Answer extends Post {
	Question q;
	
	public Answer(Question q, String s, User u) {
		postNum = q.getAnswerNum();
		this.content = s;
		this.poster = u;
		this.q = q;
	}
	
	
	public String getAnswer() {
		return "On " + postDate + ", user " + poster.getName() + " Answered: " + content;
	}
}
