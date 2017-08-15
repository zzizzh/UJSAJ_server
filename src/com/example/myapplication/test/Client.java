package com.example.myapplication.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.example.myapplication.Foundation.PostsList;
import com.example.myapplication.ProblemDomain.Posts;
import com.example.myapplication.ProblemDomain.User;

/*
 * client Ŭ�����ȿ� 
 * read�� write�ϴ� class�� ��������.
 * 
 */
public class Client
{
	Socket sock;
	clientWrite clientW;
	clientRead clientR;
	private static ClientControl cControl;

	public Client(String host, int port)
	{
		cControl=new ClientControl();
		try {
			System.out.println("-----Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.");
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
	
	static public ClientControl getcControl() {
		return cControl;
	}
	public void setcControl(ClientControl cControl) {
		this.cControl = cControl;
	}
}
class clientRead extends Thread//������ ���� �޼��� �ޱ�.
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
					System.out.println("-----������������ msg >: "+line);
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

class clientWrite extends Thread//������ �޼��� ������
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

			while (true) //&��ü thread�� �ݺ���
			{			
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(sendToReadyString==true)//& �����غ� �ɶ��� ���൵�� �ϴ� �ݺ���
				{
					out.writeObject(console);
					sendToReadyString=false;
					System.out.println("-----server�� -�޼���-����");					
				}
				while(sendToReadyPosts==true)//& �����غ� �ɶ��� ���൵�� �ϴ� �ݺ���
				{
					out.writeObject(postsConsole);
					sendToReadyPosts=false;
					System.out.println("-----server�� -list-����");					
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
