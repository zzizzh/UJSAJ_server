package com.example.myapplication.test;

import java.util.ArrayList;
import java.util.Collection;

import com.example.myapplication.Foundation.PostsList;
import com.example.myapplication.ProblemDomain.Posts;
import com.example.myapplication.ProblemDomain.User;

/*
 * Ŭ���̾�Ʈ���� �ʿ��� �����͸� �����ϴ� Ŭ����
 * 7.22 �����
 */
public class ClientControl {

	private ArrayList<Posts> timeLine; // timeLine�� ���� Posts����Ʈ
	private ArrayList<Posts> myPostsList; // ���� �� Posts ����Ʈ
	private ArrayList<Posts> myLikeList; // ���� ���ƿ� �� Posts ����Ʈ
	private ArrayList<String> stringList; // ��� ���� string ����Ʈ

	private User me; // ���� �α��� ���� ����

	/*
	 * �⺻ ������ ArrayList�� �ʱ�ȭ
	 */
	public ClientControl() {
		timeLine = new ArrayList<Posts>();
		myPostsList = new ArrayList<Posts>();
		stringList = new ArrayList<String>();

		me = null;
	}

	/*
	 * �������� �޾ƿ� Ÿ�Ӷ��ο� ���� PostsList�� ����.
	 */
	public void addTimeLine(PostsList postsList) {
		timeLine.addAll(postsList.getAll());
	}

	/*
	 * �������� �޾ƿ� ���� PostsList�� ����.
	 */
	public void setMyPostsList(PostsList postsList) {
		myPostsList.addAll(postsList.getAll());
	}

	/*
	 * ���� �� ���� MyPostsList�� ����.
	 */
	public void addMyPostsList(Posts posts) {
		myPostsList.add(posts);
	}

	/*
	 * �������� �޾ƿ� ���� ���ƿ� �� PostsList�� ����.
	 */
	public void setLikeList(PostsList postsList) {
		myLikeList.addAll(postsList.getAll());
	}

	/*
	 * ���� ���ƿ� �� ���� PostsList�� ����.
	 */
	public void addLikeList(Posts posts) {
		myLikeList.add(posts);
	}

	/*
	 * �α����� �� �������� ��
	 */
	public void setMe(User user) {
		me = user;
	}

	public void addString(String string) {
		stringList.add(string);
	}

	public void resetAll() {
		stringList = new ArrayList<String>();
		timeLine = new ArrayList<Posts>();
		myPostsList = new ArrayList<Posts>();

		me = null;
	}
	
	public void resetTimeLine(){
		timeLine = new ArrayList<Posts>();
	}
	
	public void resetMyPostsList(){
		myPostsList = new ArrayList<Posts>();
	}
}
