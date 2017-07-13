package ProblemDomain;

import java.util.ArrayList;

public class Location {
	ArrayList<Integer> catList;
	int contentID;
	int contentTypeID;
	String title;
	
	public ArrayList<Integer> getCatList() {
		return catList;
	}
	public void setCatList(ArrayList<Integer> catList) {
		this.catList = catList;
	}
	public int getContentID() {
		return contentID;
	}
	public void setContentID(int contentID) {
		this.contentID = contentID;
	}
	public int getContentTypeID() {
		return contentTypeID;
	}
	public void setContentTypeID(int contentTypeID) {
		this.contentTypeID = contentTypeID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
