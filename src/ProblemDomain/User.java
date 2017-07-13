package ProblemDomain;

import java.util.ArrayList;

public class User {
	String userID;
	String PW;
	ArrayList<Integer> likeList;
	ArrayList<Integer> myList;
	
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPW() {
		return PW;
	}
	public void setPW(String pW) {
		PW = pW;
	}
	public ArrayList<Integer> getLikeList() {
		return likeList;
	}
	public void setLikeList(ArrayList<Integer> likeList) {
		this.likeList = likeList;
	}
	public ArrayList<Integer> getMyList() {
		return myList;
	}
	public void setMyList(ArrayList<Integer> myList) {
		this.myList = myList;
	}
	
	
}
