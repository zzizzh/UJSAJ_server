package ProblemDomain;

import java.awt.Image;
import java.io.File;

public class Posts {

	int index;
	Location locationInfo;
	String url;
	String artist;
	String song;
	String comment;
	
	int postsID;
	int like;
	
	long createTime;
	File image;
	
	public Posts(){
		this.index = 0;
		this.locationInfo = null;
		this.url = null;
		this.artist = null;
		this.song = null;
		this.postsID = 0;
		this.like = 0;
		this.createTime = 0;
		this.image = null;
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
		this.image = null;
	}
	
	public int getPostsIndex(){
		return index;
	}
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
	public File getImage(){
		return image;
	}
	public void setImage(File image){
		this.image = image;
	}
	
}
