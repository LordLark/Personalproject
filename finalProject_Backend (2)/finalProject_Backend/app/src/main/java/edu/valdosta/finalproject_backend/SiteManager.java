package edu.valdosta.finalproject_backend;

import java.util.ArrayList;

public class SiteManager {
	ArrayList<User> users;
	ArrayList<Group> groups;
	private int userNum;
	private int groupNum;
	
	public SiteManager() {
		users = new ArrayList<User>();
		groups  = new ArrayList<Group>();
	}
	
	public String getAllGroupInfo() {
		String s = "Groups: \n";
		for(Group g: groups) {
			s += g.getGroupInfo() + " \n" + g.getAllMembers();
		}
		return s;
	}
	
	public void addGroup(Group g) {
		if(doesGroupExist(g) == false) {
			groups.add(g);
		}
	}
	
	public Group getGroup(int groupNum) {
		for(Group g: groups) {
			if(groupNum == g.getGroupNum()) {
				return g;
			}
		}
		return null;
	}
	
	public Group getGroup(Group ng) {
		for(Group g: groups) {
			if(ng.getGroupNum() == g.getGroupNum()) {
				return g;
			}
		}
		return null;
	}
	
	public int getGroupNum() {
		groupNum = (int)(Math.random() * 1000);
		boolean doesNumExist = false;
		for(Group g: groups) {
			if(groupNum == g.getGroupNum()) {
				doesNumExist = true;
				break;
			}
		}
		if(doesNumExist) {
			groupNum = getGroupNum();
		}
		return groupNum + 1;
	}
	
	public boolean doesGroupExist(Group ng) {
		for(Group g: groups) {
			if ( ng.getGroupNum() == g.getGroupNum() ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean doesGroupExist(int groupNum) {
		for(Group g: groups) {
			if ( groupNum == g.getGroupNum() ) {
				return true;
			}
		}
		return false;
	}
	
	public String getAllUserInfo() {
		String s = "Users: \n";
		for(int i = 0; i<users.size(); i++) {
			s = s + users.get(i).getUserInfo() + "\n";
		}
		return s;
	}
	
	public User addUser(User u) {
		if(doesUserExist(u.getName()) == false) {
			users.add(u);
		}
		return u;
	}
	
	public int getUserNum() {
		userNum = (int)(Math.random() * 1000);
		boolean doesNumExist = false;
		for(User u: users) {
			if(userNum == u.getUserID()) {
				doesNumExist = true;
				break;
			}
		}
		if(doesNumExist) {
			userNum = getUserNum();
		}
		return userNum + 1;
	}
	
	public boolean doesUserExist(String nu) {
		boolean doesExist = false;
		for(User u: users) {
			if(u.getName().equals(nu)) {
				doesExist = true;
				break;
			}
		}
		return doesExist;
	}

	public User findUser(String userName){
		for(User u: users){
			if(u.getName().equals(userName)) {
				return u;
			}
		}
		return null;
	}

	public Group findGroup(String groupName){
		for(Group g: groups){
			if(g.getGroupName().equals(groupName)) {
				return g;
			}
		}
		return null;
	}
	
}
