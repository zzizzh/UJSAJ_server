package test;

import java.util.ArrayList;
import java.util.Collection;

import Foundation.PostsList;
import ProblemDomain.Posts;
import ProblemDomain.User;

/*
 * 클라이언트에서 필요한 데이터를 관리하는 클래스
 * 7.22 지재민
 */
public class ClientControl {

	private ArrayList<Posts> timeLine;		// timeLine에 보일 Posts리스트
	private ArrayList<Posts> myPostsList;	// 내가 쓴 Posts 리스트
	private ArrayList<Posts> myLikeList;	// 내가 좋아요 한 Posts 리스트
	private ArrayList<String> stringList;	// 통신 관련 string 리스트
	
	private User me;						// 현재 로그인 유저 정보
	
	
	/*
	 * 기본 생성자
	 * ArrayList만 초기화
	 */
	public ClientControl()
	{
		timeLine=new ArrayList<Posts>();
		myPostsList=new ArrayList<Posts>();
		stringList=new ArrayList<String>();
		
		me = null;
	}	
	
	/*
	 * 서버에서 받아온 타임라인에 보일 PostsList를 저장.
	 */
	public void addTimeLine(PostsList postsList)
	{
		timeLine.addAll(postsList.getAll());
	}

	/*
	 * 서버에서 받아온 나의 PostsList를 저장.
	 */
	public void setMyPostsList(PostsList postsList)
	{
		myPostsList.addAll(postsList.getAll());
	}
	/*
	 * 내가 쓴 글을 MyPostsList에 저장.
	 */
	public void addMyPostsList(Posts posts)
	{
		myPostsList.add(posts);
	}
	
	/*
	 * 서버에서 받아온 내가 좋아요 한 PostsList를 저장.
	 */
	public void setLikeList(PostsList postsList)
	{
		myLikeList.addAll(postsList.getAll());
	}
	/*
	 * 내가 좋아요 한 글을 PostsList에 저장.
	 */
	public void addLikeList(Posts posts)
	{
		myLikeList.add(posts);
	}
	
	
	public void addString(String string)
	{
		stringList.add(string);
	}

	public void setMe(User user)
	{
		me = user;
	}
	
	public void resetAll()
	{
		stringList=new ArrayList<String>();
		timeLine=new ArrayList<Posts>();
		myPostsList=new ArrayList<Posts>();
		stringList=new ArrayList<String>();
		
		me = null;
	}
}
