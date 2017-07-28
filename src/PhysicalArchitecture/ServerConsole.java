package PhysicalArchitecture;

import java.io.IOException;
import java.io.ObjectOutputStream;

import DB.DBManager;
import ProblemDomain.Posts;
import ProblemDomain.User;

public class ServerConsole {

	private ObjectOutputStream objOutput;
	private DBManager dbManager;

	public ServerConsole(ObjectOutputStream objout) {
		dbManager = new DBManager();
		objOutput = objout;
	}

	/*
	 * Ŭ���̾�Ʈ���� ���� ��ɾ ó���ϴ� �Լ� ��ɾ�� #���� �����ϰ� $�� ��ū�� �����Ѵ�.
	 */
	public void handleMeg(String msg) {
		if (msg.startsWith("#register")) {
			msg = msg.substring(9);
			
			register(msg);
			
		}

		else if (msg.startsWith("#login")) {
		
			msg = msg.substring(6);
			login(msg);
			
		} else if (msg.startsWith("#morePosts")) {
			morePosts();
		} else if (msg.startsWith("#refresh")) {
			refresh();
		} else if (msg.startsWith("#myLike")) {
			myLike();
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
		String[] token = msg.split("$");
		String id = token[0];
		String pass = token[1];

		if (pass.compareTo(dbManager.getPWByID(id)) == 0)
			sendUser(dbManager.getUserByID(id));
		else
			sendString("#err");
	}

	private void register(String msg) {
		String[] token = msg.split("$");
		String id = token[0];
		String pass = token[1];

		if (dbManager.getPWByID(id) == null)
			sendString("#err");

		else {
			User user = new User(dbManager.userIndex, id, pass);

			dbManager.insertUser(user);

			sendString("#fin");
		}
	}

	public void refresh() {
		
	}

	public void morePosts() {

	}

	public void myLike() {

	}

	public void moreLike() {

	}

	public void post(Posts p) {

	}

	public void delete(String msg) {
		String[] token = msg.split("$");
		
		try{
			int index = Integer.parseInt(token[0]);
			
		}catch(NumberFormatException e){
			sendString("#err");
			e.printStackTrace();
		}	
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
}