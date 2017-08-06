package PhysicalArchitecture;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import DB.DBManager;
import Foundation.PostsList;
import ProblemDomain.Posts;
import ProblemDomain.User;

public class ServerConsole {

	private ObjectOutputStream objOutput;
	private DBManager dbManager;

	private int likeCnt = 0;
	private User user;
	
	public ServerConsole(ObjectOutputStream objout) {
	//	dbManager = new DBManager();
		objOutput = objout;
	}

	/*
	 * 클라이언트에서 받은 명령어를 처리하는 함수 명령어는 #으로 시작하고 $로 토큰을 구분한다.
	 */
	public void handleMeg(String msg) {
		System.out.println("클라이언트로부터 받은 문자열 : " + msg);
		
		if (msg.startsWith("#register")) {
			System.out.println("등록");
			msg = msg.substring(9);
			
			register(msg);
		}

		else if (msg.startsWith("#login")) {
			System.out.println("로그인");
			
			msg = msg.substring(7);
			login(msg);
			
		} else if (msg.startsWith("#morePosts")) {
			
			morePosts();
		} else if (msg.startsWith("#refresh")) {
			refresh();
		} else if (msg.startsWith("#myLike")) {
			msg = msg.substring(8);
			
			myLike(Integer.parseInt(msg));
			
		} else if (msg.startsWith("#moreLike")) {
			moreLike();
		} else if (msg.startsWith("#post")) {
			sendString("#ready");
		} else if (msg.startsWith("#delete")) {
			msg = msg.substring(7);
			delete(msg);
		} else if (msg.startsWith("#like")) {

		} 
	}

	// ----------------function------------//

	private void login(String msg) {
		System.out.println(msg);
		String[] token = msg.split("%");
		String id = token[0];
		String pass = token[1];

		System.out.println("id : " + token[0] + "/ pass : " + token[1]);
		
		if (pass.compareTo(dbManager.getPWByID(id)) == 0){
			user = dbManager.getUserByID(id);
			sendUser(user);
		}
		else
			sendString("#err");
	}

	private void register(String msg) {
		String[] token = msg.split("%");
		String id = token[0];
		String pass = token[1];
		
		if (dbManager.getPWByID(id) == null)
			sendString("#err");

		else {
			User user = new User(dbManager.getUserIndex(), id, pass);

			dbManager.insertUser(user);

			sendString("#fin");
		}
	}

	public void refresh() {
		try {
			PostsList p = new PostsList(dbManager.refreshTimeLine());
			sendPostsList(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void morePosts() {
		try {
			PostsList p = new PostsList(dbManager.getMorePosts());
			sendPostsList(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void myLike(int userIndex) {
		ArrayList<Integer> temp = user.getMyList();
		
		PostsList p = new PostsList();
		
		for(int i=0; i<temp.size(); i++){
			if(i==10)
				break;
			
			p.addPosts(dbManager.getPostsByIndex(temp.get(i)));
			likeCnt++;
		}
		sendPostsList(p);
	}

	public void moreLike() {
		
	}

	public void post(Posts p) {
		p.toString();
	}

	public void delete(String msg) {
		String[] token = msg.split("%");
		
		try{
			int index = Integer.parseInt(token[0]);
			
		}catch(NumberFormatException e){
			sendString("#err");
			e.printStackTrace();
		}	
	}
	
	public void like(){
		
	}
	
	public void disLike(){
		
	}

	// ------------------send---------------//

	private void sendString(String str) {
		try {
			objOutput.writeObject(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendUser(User user) {
		try {
			objOutput.writeObject(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendPostsList(PostsList p){
		try {
			objOutput.writeObject(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}