package com.example.myapplication.PhysicalArchitecture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.example.myapplication.DB.DBManager;
import com.example.myapplication.ProblemDomain.Posts;

public class Server extends Thread// ����������Ŭ����
{
	private ServerSocket server;
	private int port;
	private int clientNumber;
	private static ArrayList<EchoThread> clientList;
	
	public Server(int port) {
		clientList = new ArrayList<EchoThread>();
		this.port = port;
		clientNumber = 0;
	}

	public void run() {
		try {
			server = new ServerSocket(port);
			System.out.println("������ ��ٸ��ϴ�.");

			while (true) {
				Socket sock = server.accept(); 
				EchoThread echothread = new EchoThread(sock, clientNumber++); 
				echothread.start(); 
				System.out.println("ActiveCount : " + EchoThread.activeCount);
				clientList.add(echothread);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static ArrayList<EchoThread> getEchoThreadList() {
		return clientList;
	}

}

class EchoThread extends Thread { 
	private ArrayList<EchoThread> clientList;
	static int activeCount = 0;

	private Socket sock;
	private int clientNumber;

	ObjectOutputStream serverOutputStream;
	ObjectInputStream in;

	private ServerConsole serverConsole; 

	public EchoThread(Socket sock, int clientNumber) {
		this.sock = sock;
		activeCount++; 
		this.clientNumber = clientNumber;
		Server.getEchoThreadList();
	} 

	public int getClientNumber() {
		return clientNumber;
	}

	public ObjectOutputStream getOutput() {
		return serverOutputStream;
	}

	public void run() { 
		try { 
			InetAddress inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress() + " �κ��� �����Ͽ����ϴ�.");
			serverOutputStream = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			serverConsole = new ServerConsole(serverOutputStream);

			Object temp;
			String line = null;

			while(true)
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				temp = in.readObject();
				
				if(temp instanceof String)
				{
					line = (String) temp;
					System.out.println("-----send String message to server : " + line);
					serverConsole.handleMeg(line);
				}
				else if(temp instanceof Posts)
				{
					System.out.println("-----send Posts to server : " + temp.toString());
					serverConsole.post((Posts)temp);
				}
				
			}

		} catch (Exception ex) {
			activeCount--;
			System.out.println(ex);
			clientList = Server.getEchoThreadList();
			for (int i = 0; i < clientList.size(); i++)
			{
				if (clientNumber == clientList.get(i).getClientNumber())
					clientList.remove(i);
			}
		}
	}


}


