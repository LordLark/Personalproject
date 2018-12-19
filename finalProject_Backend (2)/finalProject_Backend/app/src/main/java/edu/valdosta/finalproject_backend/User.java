package edu.valdosta.finalproject_backend;

import java.util.Date;
import java.util.ArrayList;

public class User {
	private SiteManager manager;
	private String fullName;
	private String userName;
	private String password;
	private int userID;
	private Date joinDate;
	private ArrayList<Group> groups = new ArrayList<Group>();
	private ArrayList<Question> p = new ArrayList<>();

	public User (SiteManager manager, String userName, String fullName, int userID, String password) {
		this.manager = manager;
		this.fullName = fullName;
		this.userName = userName;
		this.userID = userID;
		this.password = password;
		joinDate = new java.util.Date();

	}

	public void postQuestion(Group g, String q) {
		Question que = new Question(q, g, this);
		p.add(que);
		g.askQuestion(que, this);
	}

	public void postAnswer(Group g, Question q, String s) {
		g.answerQuestion(q, this, s);
	}

	public String getName() {
		return userName;
	}

	public String getUserInfo() {
		return fullName + " " + userID + " " + userName + "; Member since: " + joinDate;
	}

	public int getUserID() {
		return userID;
	}

	public void createGroup(SiteManager manager, String name, String desc, int groupNum) {
		Group g = new Group(manager, name, desc, manager.getGroupNum());
		joinGroup(g);
		if(manager.doesGroupExist(g) == false) {
			manager.addGroup(g);
		}
	}

	public void joinGroup(int groupNum) {
		Group g;
		if (manager.doesGroupExist(groupNum) == true) {
			g = manager.getGroup(groupNum);
			g.addMember(this);
			groups.add(g);
		}
	}

	public void joinGroup(Group g) {
		if (manager.doesGroupExist(g) == true) {
			manager.getGroup(g);
			g.addMember(manager.addUser(this));
			groups.add(g);
		}
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public String getPassword(){
		return password;
	}

	public String getJoinDate() {
		return joinDate.toString();
	}

	public ArrayList<Question> getPosts(){
		return p;
	}
}
