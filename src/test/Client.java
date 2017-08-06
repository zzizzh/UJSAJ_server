package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Foundation.PostsList;
import ProblemDomain.Posts;
import ProblemDomain.User;

/*
 * client 클래스안에 
 * read와 write하는 class를 갖고있음.
 * 
 */
public class Client
{
	Socket sock;
	clientWrite clientW;
	clientRead clientR;
	private ClientControl cControl;

	public Client(String host, int port)
	{
		cControl=new ClientControl();
		try {
			System.out.println("-----클라이언트가 실행되었습니다.");
			sock = new Socket(host, port);

		} catch (IOException e) {
			e.printStackTrace();
		} 

		clientW=new clientWrite(sock);
		clientR=new clientRead(sock,cControl);
		clientW.start();
		clientR.start();
	}

	public void sendToServer(Object obj)
	{
		if(obj instanceof String)
			clientW.sendToServer((String)obj);
		else if(obj instanceof Posts)
			clientW.sendToServerPosts((Posts)obj);
	}
	
	public ClientControl getcControl() {
		return cControl;
	}
	public void setcControl(ClientControl cControl) {
		this.cControl = cControl;
	}
}
class clientRead extends Thread//서버로 부터 메세지 받기.
{
	Socket socket;
	private ClientControl cControl;

	public clientRead(Socket socket,ClientControl cControl)
	{
		this.socket=socket;
		this.cControl=cControl;
	}
	public void run()
	{
		try {
			ObjectInputStream clientInputStream = new ObjectInputStream(socket.getInputStream());
			Object temp;
			while(true)
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				temp = clientInputStream.readObject();
				
				if(temp instanceof PostsList)
				{					
						
				}
				else if(temp instanceof User)
				{
					
				}
				
				else if(temp instanceof String)
				{
					String line = (String) temp;
					System.out.println("-----서버에서받은 msg >: "+line);
					cControl.addString(line);
				}
			}	
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {

				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}	


}

class clientWrite extends Thread//서버로 메세지 보내기
{
	private Socket socket;
	private String console;
	private Posts postsConsole;
	
	private boolean sendToReadyString;
	private boolean sendToReadyPosts;
	
	public clientWrite(Socket socket)
	{
		this.socket=socket;
		sendToReadyString=false;
		sendToReadyPosts=false;
	}
	public void run()
	{
		ObjectOutputStream out;

		try {
			out = new ObjectOutputStream(socket.getOutputStream());

			while (true) //&전체 thread의 반복문
			{			
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(sendToReadyString==true)//& 보낼준비가 될때만 실행도록 하는 반복문
				{
					out.writeObject(console);
					sendToReadyString=false;
					System.out.println("-----server로 -메세지-전송");					
				}
				while(sendToReadyPosts==true)//& 보낼준비가 될때만 실행도록 하는 반복문
				{
					out.writeObject(postsConsole);
					sendToReadyPosts=false;
					System.out.println("-----server로 -list-전송");					
				}
				
			}		
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void sendToServer(String msg)
	{
		console=msg;
		sendToReadyString=true;
	}
	public void sendToServerPosts(Posts posts)
	{
		postsConsole=posts;
		sendToReadyPosts=true;
	}

}
