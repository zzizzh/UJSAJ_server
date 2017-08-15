package com.example.myapplication.Foundation;

<<<<<<< HEAD:src/com/example/myapplication/Foundation/PostsList.java

=======
>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/Foundation/PostsList.java
import java.io.Serializable;
import java.util.ArrayList;

import com.example.myapplication.ProblemDomain.Posts;

public class PostsList implements Serializable{
	ArrayList<Posts> postsList;
	static final long serialVersionUID = 100L;

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
<<<<<<< HEAD:src/com/example/myapplication/Foundation/PostsList.java

=======
		else
			System.out.println("������ �Ұ��� �մϴ�.");
>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/Foundation/PostsList.java
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
