package Foundation;

import java.io.Serializable;
import java.util.ArrayList;

import ProblemDomain.Posts;

public class PostsList implements Serializable{
	ArrayList<Posts> postsList;
	
	public PostsList() {
		postsList = new ArrayList<Posts>();
	}
	
	public PostsList(ArrayList<Posts> p) {
		postsList = p;
	}

	public void addPosts(Posts p) {
		postsList.add(p);
	}

	public Posts getPosts(int i) {
		return postsList.get(i);
	}
	
	public ArrayList<Posts> getAll(){
		return postsList;
	}

	public int size() {
		return postsList.size();
	}

	public void deletePosts(int i) {
		if (postsList.size() > 0)
			postsList.remove(i);
		else
			System.out.println("������ �Ұ��� �մϴ�.");
	}

	@Override
	public String toString() {
		String s = new String();
		for(int i = 0;i<this.size();i++){
			s += this.getPosts(i).toString();
			s += "\n";
		}
		return s;
	}
	
	
}
