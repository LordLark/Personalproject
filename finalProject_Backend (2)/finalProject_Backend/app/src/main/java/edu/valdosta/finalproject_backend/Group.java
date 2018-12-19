package edu.valdosta.finalproject_backend;


import java.util.Date;
import java.util.ArrayList;

public class Group {
	private SiteManager manager;
	private Date creationDate;
	private int groupNum;
	private String groupName;
	private String desc;
	private ArrayList<User> members;
	private ArrayList<Question> questions;

	public Group (SiteManager manager, String groupName, String desc, int groupNum) {
		this.manager = manager;
		this.groupName = groupName;
		this.groupNum = groupNum;
		this.desc = desc;
		creationDate = new java.util.Date();
		members = new ArrayList<User>();
		questions = new ArrayList<Question>();
	}

	public void askQuestion(Question q, User u) {
		if (this.alreadyMember(u) != null) {
			questions.add(q);
		}
	}

	public void answerQuestion(Question q, User u, String s) {
		if (this.alreadyMember(u) != null) {
			for(Question q1:questions) {
				if(q1.equals(q)) {
					q1.addAnswer(u,s);
				}

			}
		}
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getGroupName(){
		return groupName;
	}

	public String getGroupDesc() {
		return desc;
	}

	public int getGroupNum()
	{
		return groupNum;
	}

	public int createQuestionNum() {
		return questions.size() * 100;
	}

	public String getGroupInfo() {
		return "Group#:" + groupNum + ", " + groupName + ": " + desc + ". Created on " + creationDate;
	}

	public String getAllMembers () {
		String s = "Members in " + this.groupName + ": ";
		for(User m: members) {
			s = s + (m.getName() + " ");
		}
		s += "\n\n";
		return s;
	}

	public ArrayList<Question> getAllQuestions () {
		return questions;
	}

	public String getAllAnswers () {
		String s = "All answers for the "+ questions.size() + " questions in " + this.groupName + ":\n";
		for (Question q: questions) {
			s += q.getAllAnswers() + "\n";
		}
		return s;
	}

	public String getAllQuestionsAndAnswers () {
		String s = "All "+ questions.size() + " Questions and their corresponding answers in " + this.groupName + ":\n";
		for (Question q: questions) {
			s += q.getQuestion() + "\n";
			s += q.getAllAnswers() + "\n";
		}
		return s;
	}

	public String getAllRecentQuestionsAndAnswers(int n) {
		String s = n + " most recent Questions and their corresponding answers in " + this.groupName + ":\n";
		for(int i=questions.size()-1; i>questions.size()-n-1;i--) {
			s += questions.get(i).getQuestion() + "\n";
			s += questions.get(i).getAllAnswers() + "\n";
		}
		return s;
	}

	public String getUnansweredQuestions() {
		String s = "All unanswered Questions in " + this.groupName + ":\n";
		for (Question q: questions) {
			if(q.hasAnswer() == false) {
				s += q.getQuestion() + "\n";
			}
		}
		return s;
	}

	public String getRecentUnansweredQuestions(int n) {
		int count = 0;
		String s = n + " most recent unanswered Questions in " + this.groupName + ":\n";
		for (int i = questions.size() - 1; i >= 0; i--) {
			if((questions.get(i).hasAnswer() == false) && (count < n)) {
				s += questions.get(i).getQuestion() + "\n";
				count += 1;
			}
		}
		return s;
	}



	public User alreadyMember(User nu) {
		for(User u: members) {
			if(nu.getUserID() == u.getUserID()) {
				return u;
			}
		}
		return null;
	}

	public void addMember(User nu) {
		if (manager.doesUserExist(nu.getJoinDate()) == true) {
			if (alreadyMember(nu) != null) {
				return;
			}
		}
		members.add(nu);
	}

	public Question getQuestion (int queNum) {
		for(Question q: questions) {
			if (q.getQuestionNum()/100 == queNum) {
				return q;
			}
		}
		return null;
	}

	public String userPosts(User u) {
		ArrayList<Post> temp = new ArrayList<Post>();
		String s;
		for(Question q:questions) {
			if(q.getPosterID() == u.getUserID()) {
				temp.add(q);
			}
			for(Answer a:q.getAnswerList()) {
				if(a.getPosterID() == u.getUserID()) {
					temp.add(a);
				}
			}

		}
		s = u.getName() + "'s posts in " + this.groupName + ":\n";
		for(Post p:temp) {
			String t = p.getClass() + ": " + p.content + "\n";
			s += t.substring(12);
		}

		return s;
	}

	public String recentUserPosts(User u, int n) {
		ArrayList<Post> temp = new ArrayList<Post>();
		String s;
		for(Question q:questions) {
			if(q.getPosterID() == u.getUserID()) {
				temp.add(q);
			}
			for(Answer a:q.getAnswerList()) {
				if(a.getPosterID() == u.getUserID()) {
					temp.add(a);
				}
			}

		}
		s = u.getName() + "'s " + n + " most recent posts in " + this.groupName + ":\n";
		for(int i=temp.size()-1; i > temp.size()-1-n; i--) {
			String t = temp.get(i).getClass() + ": " + temp.get(i).content + "\n";
			s += t.substring(12);
		}

		return s;
	}

	public String search(String s) {
		ArrayList<Post> temp = new ArrayList<Post>();
		String ret;
		for(Question q:questions) {
			if(q.content.contains(s)) {
				temp.add(q);
			}
			for(Answer a:q.getAnswerList()) {
				if(a.content.contains(s)) {
					temp.add(a);
				}
			}

		}
		ret = "Posts that contain the keyword (" + s + "):\n";
		for(Post p:temp) {
			String t = p.getClass() + ": " + p.content + "\n";
			ret += t.substring(12);
		}
		return ret;
	}

	public Question findQ(String s){
		for(Question q: questions){
			if(q.getContent().equals(s)){
				return q;
			}
		}
		return null;
	}
}