package ProblemDomain;

import java.awt.Image;
import java.io.Serializable;

public class Posts implements Serializable{

	Location locationInfo;
	String url;
	String artist;
	String song;
	String comment;
	
	int postsID;
	int like;
	
	long createTime;
	Image image;
	
	public Posts(){
		locationInfo = new Location();
		url = "http://mwultong.blogspot.com/2006/11/java-user-input-string-number.html";
		artist = "아이유";
		song = "밤편지";
		comment = "테스트";
		
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
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Posts [locationInfo=" + locationInfo + ", url=" + url + ", artist=" + artist + ", song=" + song
				+ ", comment=" + comment + ", postsID=" + postsID + ", like=" + like + ", createTime=" + createTime
				+ ", image=" + image + "]";
	}
}
