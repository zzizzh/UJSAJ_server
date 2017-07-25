package ProblemDomain;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable{
	int bigLocation;
	int midLocation;
	int smallLocation;
	int contentID;
	int contentTypeID;
	String title;
	
	public int getBigLocation() {
		return bigLocation;
	}
	public void setBigLocation(int bigLocation) {
		this.bigLocation = bigLocation;
	}
	public int getMidLocation() {
		return midLocation;
	}
	public void setMidLocation(int midLocation) {
		this.midLocation = midLocation;
	}
	public int getSmallLocation() {
		return bigLocation;
	}	
	public void setSmallLocation(int smallLocation) {
		this.smallLocation = smallLocation;
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
