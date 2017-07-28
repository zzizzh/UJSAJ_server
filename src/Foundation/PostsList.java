package Foundation;

import java.util.ArrayList;

import ProblemDomain.Posts;

public class PostsList {
	ArrayList<Posts> postsList;
	
	public PostsList() {
		postsList = new ArrayList<Posts>();
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
			System.out.println("삭제가 불가능 합니다.");
	}
}
