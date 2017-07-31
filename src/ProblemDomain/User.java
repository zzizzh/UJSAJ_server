package ProblemDomain;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	
	private int index;
	private String id,pw;
	private ArrayList<Integer> likeList;
	private ArrayList<Integer> myList;
	
	public User(){
		this.index = 0;
		this.id = null;
		this.pw = null;
		this.likeList = null;
		this.myList = null;
	}
	public User(int index, String id, String pw){
		
		this.index = index;
		this.id = id;
		this.pw = pw;
	}
	
	public String getId(){
		return id;
	}
	public String getPw(){
		return pw;
	}
	public int getUserIndex(){
		return index;
	}
	public ArrayList<Integer> getLikeList(){
		return likeList;
	}
	public ArrayList<Integer> getMyList(){
		return myList;
	}
	public void setUser(int index, String id, String pw){
		this.index = index;
		this.id = id;
		this.pw = pw;
	}
	public void setUserIndex(int userIndex){
		this.index = userIndex;
	}
	public void setUserId(String id){
		this.id = id;
	}
	public void setUserPw(String pw){
		this.pw = pw;
	}
	public void setUserLikeList(ArrayList<Integer> likeList){
		this.likeList = likeList;
	}
	public void setUserMyList(ArrayList<Integer> myList){
		this.myList = myList;
	}
	
}
