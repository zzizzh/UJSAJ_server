package PhysicalArchitecture;

<<<<<<< HEAD
import java.io.BufferedReader;

=======
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import DB.DBManager;
<<<<<<< HEAD
import DB.*;
public class Server extends Thread// ����������Ŭ����
=======
import ProblemDomain.Posts;

public class Server extends Thread// ����������Ŭ����
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862
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
<<<<<<< HEAD

				Socket sock = server.accept(); // ���� ���

				EchoThread echothread = new EchoThread(sock, clientNumber++); // Ŭ���̾�Ʈ
																				// ����
																				// ��
																				// ����Ǹ�
				// echoThread ����
				echothread.start(); // run()�޼ҵ� ����

=======
				Socket sock = server.accept(); 
				EchoThread echothread = new EchoThread(sock, clientNumber++); 
				echothread.start(); 
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862
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

<<<<<<< HEAD
class EchoThread extends Thread { // ��Ŭ���̾�Ʈ�� ��Ƽ������ ����
=======
class EchoThread extends Thread { 
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862
	private ArrayList<EchoThread> clientList;
	static int activeCount = 0;

	private Socket sock;
	private int clientNumber;

	ObjectOutputStream serverOutputStream;
<<<<<<< HEAD
	OutputStream out;
	InputStream in;
	PrintWriter pw;
	BufferedReader br;
	DBManager dbManager;
	
	public EchoThread(Socket sock, int clientNumber) {
		this.sock = sock;
		activeCount++; // �ش� Ŭ���� ���� �� �� ����
		this.clientNumber = clientNumber;
		Server.getEchoThreadList();
	} // ������
=======
	ObjectInputStream in;

	private ServerConsole serverConsole; 

	public EchoThread(Socket sock, int clientNumber) {
		this.sock = sock;
		activeCount++; 
		this.clientNumber = clientNumber;
		Server.getEchoThreadList();
	} 
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862

	public int getClientNumber() {
		return clientNumber;
	}

	public ObjectOutputStream getOutput() {
		return serverOutputStream;
	}

<<<<<<< HEAD
	public void run() { // start() ���� �� ȣ��
		try { // I/O �� ��� ����
			InetAddress inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress() + " �κ��� �����Ͽ����ϴ�.");
			serverOutputStream = new ObjectOutputStream(sock.getOutputStream());// ��Ŭ���̾�Ʈ��	������   object��Ʈ��
			in = sock.getInputStream();// ��Ŭ���̾�Ʈ�κ��� �޴� strign��Ʈ��
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
=======
	public void run() { 
		try { 
			InetAddress inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress() + " �κ��� �����Ͽ����ϴ�.");
			serverOutputStream = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			serverConsole = new ServerConsole(serverOutputStream);
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862

			Object temp;
			String line = null;

<<<<<<< HEAD
			while ((line = br.readLine()) != null) {
				// ��� Ŭ���̾�Ʈ�� ���´��� Ȯ�� �� �� �ֳ�?
				System.out.println("Ŭ���̾�Ʈ�� ���� ���۹��� ���ڿ� : " + line);
				handleMeg(line);
			}

			br.close();
			sock.close();
			activeCount--;
			clientList = Server.getEchoThreadList();
			for (int i = 0; i < clientList.size(); i++)// ��Ŭ���̾�Ʈ�� Ż���� �ش� Ŭ���̾�Ʈã�Ƽ� ����
=======
			while(true)
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862
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
					System.out.println("-----Ŭ���̾�Ʈ�� ���� ���۹��� ���ڿ� : " + line);
					serverConsole.handleMeg(line);
				}
				else if(temp instanceof Posts)
				{
					System.out.println("-----Ŭ���̾�Ʈ�� ���� ���۹��� ������ : " + temp.toString());
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

<<<<<<< HEAD
	public void handleMeg(String msg)// �� Ŭ���̾�Ʈ�� ���� �Է¹��� ��ɾ �������� ó���ϴ� �޼ҵ�
	// ��Ŀ������� ��� ������ �� . ex #book"ȸ�ǽ�30"�뱸�ϱ�"������"01049497193
	{
		StringTokenizer token = new StringTokenizer(msg, "#");
		
		if (msg.startsWith("login")) {
			String pw = dbManager.getPWByID(token.nextToken());
			if (pw.equals(token.nextToken()))
				sendToClientString("success");
			else
				sendToClientString("false");
		}
		else if (msg.startsWith("regist")) {
			if (dbManager.getPWByID(token.nextToken()) != null)
				sendToClientString("success");
			else 
				sendToClientString("false");
			
		}
		/*} else if (msg.startsWith("#login")) {
			Login(msg);
		} else if (msg.startsWith("#joinC"))
		{
			JoinC(msg);
		} else if (msg.startsWith("#joinB"))
		{
			JoinB(msg);
		} else if (msg.startsWith("#search")) {
			Search(msg,roomlist);			
		}*/
		
	}

	//@@���� Ŭ���̾�Ʈ���� String�� �Ѱ��ִ� �Լ�
	private void sendToClientString(String line) {
		ObjectOutputStream temp = this.getOutput();
		try {
=======
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862

}


