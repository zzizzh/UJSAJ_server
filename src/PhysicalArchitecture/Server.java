package PhysicalArchitecture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import DB.DBManager;
import ProblemDomain.Posts;

public class Server extends Thread// ＆서버오픈클래스
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
			System.out.println("접속을 기다립니다.");

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
			System.out.println(inetaddr.getHostAddress() + " 로부터 접속하였습니다.");
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
					System.out.println("-----클라이언트로 부터 전송받은 문자열 : " + line);
					serverConsole.handleMeg(line);
				}
				else if(temp instanceof Posts)
				{
					System.out.println("-----클라이언트로 부터 전송받은 데이터 : " + temp.toString());
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


