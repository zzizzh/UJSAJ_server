package PhysicalArchitecture;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import DB.DBManager;
import DB.*;
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

				Socket sock = server.accept(); // ���� ���

				EchoThread echothread = new EchoThread(sock, clientNumber++); // Ŭ���̾�Ʈ
																				// ����
																				// ��
																				// ����Ǹ�
				// echoThread ����
				echothread.start(); // run()�޼ҵ� ����

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

class EchoThread extends Thread { // ��Ŭ���̾�Ʈ�� ��Ƽ������ ����
	private ArrayList<EchoThread> clientList;
	static int activeCount = 0;

	private Socket sock;
	private int clientNumber;

	ObjectOutputStream serverOutputStream;
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

	public int getClientNumber() {
		return clientNumber;
	}

	public ObjectOutputStream getOutput() {
		return serverOutputStream;
	}

	public void run() { // start() ���� �� ȣ��
		try { // I/O �� ��� ����
			InetAddress inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress() + " �κ��� �����Ͽ����ϴ�.");
			serverOutputStream = new ObjectOutputStream(sock.getOutputStream());// ��Ŭ���̾�Ʈ��	������   object��Ʈ��
			in = sock.getInputStream();// ��Ŭ���̾�Ʈ�κ��� �޴� strign��Ʈ��
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String line = null;

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
			{
				if (clientNumber == clientList.get(i).getClientNumber())
					clientList.remove(i);
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

			temp.writeObject(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
