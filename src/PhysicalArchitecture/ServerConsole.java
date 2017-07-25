package PhysicalArchitecture;

import java.io.IOException;
import java.io.ObjectOutputStream;

import DB.DBManager;
import ProblemDomain.Posts;
import ProblemDomain.User;

public class ServerConsole {
	
	private ObjectOutputStream objOutput;
	private DBManager dbManager;
	
	public ServerConsole(ObjectOutputStream objout)
	{
		dbManager = new DBManager();
		objOutput=objout;
	}
	
	/*
	 * Ŭ���̾�Ʈ���� ���� ��ɾ ó���ϴ� �Լ�
	 * ��ɾ�� #���� �����ϰ� $�� ��ū�� �����Ѵ�.
	 */
	public void handleMeg(String msg)
	{
		if (msg.startsWith("#register"))
		{
			msg = msg.substring(9);
			String[] token = msg.split("$");
			String id = token[0];
			String pass = token[1];
			
			
			User user = new User( dbManager.userIndex, id, pass);
			
			dbManager.insertUser(user);
			
			sendString("fin");
		}
		
		else if(msg.startsWith("#login"))
		{
			msg = msg.substring(6);
			String[] token = msg.split("$");
			String id = token[0];
			String pass = token[1];
			
			User user = new User( dbManager.userIndex, id, pass);
			
			dbManager.insertUser(user);
		}
		else if(msg.startsWith("#moreposts"))
		{
			
		}
		else if(msg.startsWith("refresh"))
		{
			
		}
		else if(msg.startsWith("mylike"))
		{
			
		}
		else if(msg.startsWith("morelike"))
		{
			
		}
		else if(msg.startsWith("post"))
		{
			
		}
		else if(msg.startsWith("delete"))
		{
			
		}
		else if(msg.startsWith("like"))
		{
			
		}
	}
	
	//----------------function------------//

	private void Login(String msg)
	{
		
	}
	
	private void register(String msg)
	{
		
	}
	
	public void refresh()
	{
		
	}
	
	public void morePosts()
	{
		
	}

	public void myLike()
	{
		
	}
	
	public void moreLike()
	{
		
	}
	
	public void post(Posts p)
	{
		
	}
	
	public void delete(int index)
	{
		
	}
	
	//------------------send---------------//

	private void sendString(String str)
	{
		try{
			objOutput.writeObject(str);
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendUser(User user)
	{
		try {
			objOutput.writeObject(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}