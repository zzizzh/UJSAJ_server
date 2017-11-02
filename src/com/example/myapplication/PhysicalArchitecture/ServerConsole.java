package com.example.myapplication.PhysicalArchitecture;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;

import com.example.myapplication.DB.DBManager;
import com.example.myapplication.Foundation.PostsList;
import com.example.myapplication.ProblemDomain.Posts;
import com.example.myapplication.ProblemDomain.User;

public class ServerConsole {

	private ObjectOutputStream objOutput;
	private DBManager dbManager;

	private int likeCnt = 0;
	private int myCnt = 0;
	private User user = new User();

	public ServerConsole(ObjectOutputStream objout) {
		dbManager = new DBManager();
		objOutput = objout;
	}

	/*
	 * 클라이언트에서 받은 명령어를 처리하는 함수 명령어는 #으로 시작하고 $로 토큰을 구분한다.
	 */
	public void handleMeg(String msg) throws Exception {
		System.out.println("클라이언트로부터 받은 문자열 : " + msg);

		if (msg.startsWith("#register")) {
			register(msg);
		} else if (msg.startsWith("#login")) {
			login(msg);
		}
		else if(msg.startsWith("#findPass")){
			findPass(msg);
		}
		else if (msg.startsWith("#morePosts")) {
			morePosts();
		} else if (msg.startsWith("#refresh")) {
			refresh(msg);
		} else if (msg.startsWith("#myLike")) {
			myLike();
		} else if (msg.startsWith("#moreLike")) {
			moreLike();
		} else if (msg.startsWith("#post")) {
			sendString("#ready");
		} else if (msg.startsWith("#delete")) {
			delete(msg);
		} else if (msg.startsWith("#like")) {
			like(msg);
		} else if (msg.startsWith("#delete")) {
			delete(msg);
		} else if (msg.startsWith("#myPosts")) {
			myPosts();
		} else if (msg.startsWith("#moreMyPosts")) {
			moreMyPosts();
		} else if (msg.startsWith("#updateUser")) {
			updateUser();
		}
	}

	// ----------------function------------//
	 public String formatTime(long lTime) {
	        Calendar c = Calendar.getInstance();
	        c.setTimeInMillis(lTime);
	        return (c.get(Calendar.HOUR_OF_DAY) + "시 " + c.get(Calendar.MINUTE) + "분 " + c.get(Calendar.SECOND) + "." + c.get(Calendar.MILLISECOND) + "초");
	    } 
	private void login(String msg) throws Exception {
		msg = msg.substring(7);
		String[] token = msg.split("%");
		String id = token[0];
		String pass = token[1];
	    // 시작 시간
        long startTime = System.currentTimeMillis();
        // 실행 시간 체크될 녀석들...
    /*
        User U = new User();
        sendUser(U);
  */
		User k = dbManager.getUserByID(id);
		System.out.println(k);
		 // 종료 시간
        long endTime = System.currentTimeMillis();
        // 시간 출력
        System.out.println("##  시작시간 : " + formatTime(startTime));
        System.out.println("##  종료시간 : " + formatTime(endTime));
        System.out.println("##  소요시간(초.0f) : " + ( endTime - startTime )/1000.0f +"초");
		if (dbManager == null) {
			System.out.println("dbManaer = null");
		}

		else if (k == null) {// id가 없으면 회원가임

			sendString("#err");
		}
		
		else if (pass.compareTo(dbManager.getPWByID(id)) == 0) {//로그인성공
			user = k;
			sendUser(k);
		}

		else {// 비밀번호가틀리면
			System.out.println("비밀번호 틀림");
			// System.out.println("Success!");
			sendString("#err");
		}
		

	}

	private void register(String msg) {
		msg = msg.substring(10);
		String[] token = msg.split("%");
		String id = token[0];
		String pass = token[1];

		if (dbManager.getPWByID(id) != null)
			sendString("#err");

		else {
			User user = new User(dbManager.getUserIndex(), id, pass);

			dbManager.insertUser(user);
			sendUser(user);
		}
	}

	private void register(String id, String pw) {
		if (dbManager.getPWByID(id) != null)
			sendString("#err");

		else {
			User user = new User(dbManager.getUserIndex(), id, pw);

			dbManager.insertUser(user);
			this.user = user;
			
		}
	}
	public void findPass(String msg) throws Exception{
		msg = msg.substring(10);
		String[] token = msg.split("%");
		String id = token[0];
		
		User user = dbManager.getUserByID(id);
		if(user == null){
			sendString("#err");
		}
		else{
			MailManager m = new MailManager(user);
			sendString("#fin");
		}

	}

	public void refresh(String msg) {
		msg = msg.substring(9);
		String[] token = msg.split("%");
		String option = token[0];
		int[] location = null;
		int k = 1;
		
		if (option == "time") {
			try {
				PostsList p = new PostsList(dbManager.refreshTimeLine());

				System.out.println("postsList size : " + p.size());
				System.out.println(p.toString());
				sendPostsList(p);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(option == "like"){
			try {
				PostsList p = new PostsList(dbManager.getPostsByLike());

				System.out.println("postsList size : " + p.size());
				System.out.println(p.toString());
				sendPostsList(p);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(option == "distance"){
			try {
				while(token[k] == null){
					location[k-1] =	Integer.parseInt(token[k]);
				}
				PostsList p = new PostsList(dbManager.getPostsByLocation(location));

				System.out.println("postsList size : " + p.size());
				System.out.println(p.toString());
				sendPostsList(p);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			sendString("#err");
		}
	}

	public void morePosts() {
		try {
			PostsList p = new PostsList(dbManager.getMorePosts());
			System.out.println(p);
			sendPostsList(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void myPosts() throws Exception {
		myCnt = 0;

		System.out.println("==============in myPosts===============");

		System.out.println(user.toString());
		ArrayList<Integer> temp = user.getMyList();

		PostsList p = new PostsList();
		System.out.println("temp size : " + temp.size());

		for (int i = 0; i < temp.size(); i++) {
			if (i == 5)
				break;
			Posts posts = dbManager.getPostsByIndex((int)(temp.get(i)));

			p.addPosts(posts);
			myCnt++;
		}

		System.out.println(p);
		sendPostsList(p);
	} 

	public void moreMyPosts() throws Exception {
		ArrayList<Integer> temp = user.getMyList();

		PostsList p = new PostsList();

		for (int i = likeCnt; i < temp.size(); i++) {
			if (i == likeCnt + 10)
				break;

			p.addPosts(dbManager.getPostsByIndex((int)(temp.get(i))));
			myCnt++;
		}
		System.out.println(p);
		sendPostsList(p);
	}

	public void myLike() throws Exception {
		likeCnt = 0;

		ArrayList<Integer> temp = user.getLikeList();

		PostsList p = new PostsList();

		for (int i = 0; i < temp.size(); i++) {
			if (i == 5)
				break;

			p.addPosts(dbManager.getPostsByIndex((int)(temp.get(i))));
			likeCnt++;
		}
		System.out.println(p);
		sendPostsList(p);
	}

	public void moreLike() throws Exception {
		ArrayList<Integer> temp = user.getLikeList();

		PostsList p = new PostsList();

		for (int i = likeCnt; i < temp.size(); i++) {
			if (i == likeCnt + 10)
				break;

			p.addPosts(dbManager.getPostsByIndex((int)(temp.get(i))));
			likeCnt++;
		}
		System.out.println(p);
		sendPostsList(p);
	}
	public void insertUserImage(User user){
		
		File fImage;
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(user.getIImage());
			BufferedImage bufferedImage = ImageIO.read(inputStream);

			ImageIO.write(bufferedImage, "png",
					(fImage = new File("C:\\Users\\안준영\\Desktop\\DB사진\\프로필사진\\" + user.getUserIndex() + ".png")));
			user.setFImage(fImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void post(Posts p) throws IOException {
		p.setPostsIndex(dbManager.getPostsIndex());
		File fImage;
		if(p.getIImage() != null){
			ByteArrayInputStream inputStream = new ByteArrayInputStream(p.getIImage());
			BufferedImage bufferedImage = ImageIO.read(inputStream);

			ImageIO.write(bufferedImage, "png",
					(fImage = new File("C:\\Users\\안준영\\Desktop\\DB사진\\" + p.getPostsIndex() + ".png")));
			p.setFImage(fImage);
		}
		dbManager.insertPosts(p);
		user.getMyList().add(p.getPostsIndex());
		dbManager.updateUser(user);

		System.out.println(p);
		sendString("#fin");
	}

	public void delete(String msg) {
		msg = msg.substring(8);
		int index = Integer.parseInt(msg);
		try {
			ArrayList<Integer> temp = user.getLikeList();

			for (int i = 0; i < temp.size(); i++)
				if ((int)(temp.get(i)) == index)
					temp.remove(i);

			dbManager.updateUser(user);
			dbManager.deletePosts(index);
		} catch (NumberFormatException e) {
			sendString("#err");
			e.printStackTrace();
		}
	}

	public void like(String msg) throws Exception {
		msg = msg.substring(6);
		int index = Integer.parseInt(msg);

		user.getLikeList().add(index);

		Posts posts = dbManager.getPostsByIndex(index);
		posts.setLike(posts.getLike() + 1);
		dbManager.updatePostsList(posts);
		dbManager.updateUser(user);
		System.out.println("updateSuccess!");
		sendString("#fin");
	}

	public void dislike(String msg) throws Exception {
		msg = msg.substring(9);
		int index = Integer.parseInt(msg);
		ArrayList<Integer> temp = user.getLikeList();

		for (int i = 0; i < temp.size(); i++)
			if ((int)(temp.get(i)) == index)
				temp.remove(i);

		Posts posts = dbManager.getPostsByIndex(index);

		if (posts.getLike() > 0)
			posts.setLike(posts.getLike() - 1);
		dbManager.updatePostsList(posts);

		dbManager.updateUser(user);

		sendUser(user);
	}

	/*
	 * request for updating user data in android
	 */
	public void updateUser() {
		sendUser(user);
		System.out.println(user.toString());
	}

	// ------------------send---------------//

	private void sendString(String str) {
		try {
			objOutput.writeObject(str);
			System.out.println("send message : " + str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendUser(User user) {
		try {
			objOutput.writeObject(user);
			System.out.println(user);
			System.out.println("send user!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendPostsList(PostsList p) {
		try {
			System.out.println("Send postsLists...");
			objOutput.writeObject(p);
			System.out.println("sending postsList complete");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}