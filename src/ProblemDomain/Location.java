package ProblemDomain;

import java.util.ArrayList;


/**
 * @author jm
 * 
 * tour information that get from TourAPI
 * catList(index) 	: Big(0), middle(1), small(2) category
 * contentID 		: tour information id
 * contentTypeID 	: tour information type id 
 * title			: tour information title 
 */
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
