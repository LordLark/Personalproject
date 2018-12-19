package edu.valdosta.finalproject_backend;

import java.util.ArrayList;

public class Question extends Post{

	ArrayList<Answer> ans = new ArrayList<Answer>();
	Group location;

	public Question(String s, Group g, User u) {
		location = g;
		content = s;
		postNum = g.createQuestionNum();
		poster = u;

	}
	
	public String getQuestion() {
		return "On " + postDate + ", " + poster.getName() + " asked: " + content;
	}
	
	public int getQuestionNum() {
		return postNum;
	}
	
	public void addAnswer(Answer a){
		ans.add(a); 
	}
	
	public void addAnswer(User u, String s) {
		Answer a = new Answer(this, s, u);
		ans.add(a);
	}
	
	public int getAnswerNum() {
		return postNum = ans.size();
	}
	
	public boolean hasAnswer() {
		boolean e = false;
		if(ans.size() == 0) {
			e = true;
		}
		return e;
	}
	
	public String getUserAnswer(User u) {
		String retUserAns = "";
		for(Answer a: ans) {
			retUserAns += a.getAnswer();
		}
		return retUserAns;
	}
	
	public ArrayList<Answer> getAllAnswers(){
		return ans;
	}

	public String getGroup(){
		return location.getGroupName();
	}
	
	public ArrayList<Answer> getAnswerList(){
		return ans;
	}
	
}
