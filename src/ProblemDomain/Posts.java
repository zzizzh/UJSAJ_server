package ProblemDomain;

import java.awt.Image;
import java.io.File;
import java.io.Serializable;

<<<<<<< HEAD
public class Posts implements Serializable{
=======
/**
 * @author jm
 * 
 * Posts data writed by User
 * Location 	: tour information
 * url 			: youtube movie url
 * artist		: 
 */
public class Posts {
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862

	int index;
	Location locationInfo;
	String url;
	String artist;
	String song;
	String comment;
	
	int postsID;
	int like;
	
	long createTime;
<<<<<<< HEAD
	File Fimage;
	Image Iimage;
	
	public Posts(){
		this.index = 0;
		this.locationInfo = null;
		this.url = null;
		this.artist = null;
		this.song = null;
		this.postsID = 0;
		this.like = 0;
		this.createTime = 0;
		this.Fimage = null;
		this.Iimage = null;
	}
	
	public Posts(int index){
		this.index = index;
		this.locationInfo = null;
		this.url = null;
		this.artist = null;
		this.song = null;
		this.postsID = 0;
		this.like = 0;
		this.createTime = 0;
		this.Fimage = null;
		this.Iimage = null;
	}
	
	public int getPostsIndex(){
		return index;
	}
	public void setPostsIndex(int index){
		this.index = index;
	}
=======
	Image image;
	
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862
	public Location getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(Location locationInfo) {
		this.locationInfo = locationInfo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getSong() {
		return song;
	}
	public void setSong(String song) {
		this.song = song;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getPostsID() {
		return postsID;
	}
	public void setPostsID(int postsID) {
		this.postsID = postsID;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public String getFileName(){
		return Integer.toString(index);
	}	
	public File getFImage(){
		return Fimage;
	}
	public void setFImage(File image){
		this.Fimage = image;
	}
	public Image getIImage(){
		return Iimage;
	}
	public void setIImage(Image image){
		this.Iimage = image;
	}
	
}
