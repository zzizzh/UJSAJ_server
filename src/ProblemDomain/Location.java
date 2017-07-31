package ProblemDomain;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable {
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
	
	@Override
	public String toString() {
		return "Location [catList=" + catList + ", contentID=" + contentID + ", contentTypeID=" + contentTypeID
				+ ", title=" + title + "]";
	}
	
	
}
