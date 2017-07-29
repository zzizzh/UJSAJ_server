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
public class Server extends Thread// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Å¬ï¿½ï¿½ï¿½ï¿½
=======
import ProblemDomain.Posts;

public class Server extends Thread// £¦¼­¹ö¿ÀÇÂÅ¬·¡½º
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
			System.out.println("ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Ù¸ï¿½ï¿½Ï´ï¿½.");

			while (true) {
<<<<<<< HEAD

				Socket sock = server.accept(); // ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½

				EchoThread echothread = new EchoThread(sock, clientNumber++); // Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®
																				// ï¿½ï¿½ï¿½ï¿½
																				// ï¿½ï¿½
																				// ï¿½ï¿½ï¿½ï¿½Ç¸ï¿½
				// echoThread ï¿½ï¿½ï¿½ï¿½
				echothread.start(); // run()ï¿½Þ¼Òµï¿½ ï¿½ï¿½ï¿½ï¿½

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
class EchoThread extends Thread { // ï¿½ï¿½Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®ï¿½ï¿½ ï¿½ï¿½Æ¼ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
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
		activeCount++; // ï¿½Ø´ï¿½ Å¬ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
		this.clientNumber = clientNumber;
		Server.getEchoThreadList();
	} // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
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
	public void run() { // start() ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ È£ï¿½ï¿½
		try { // I/O ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
			InetAddress inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress() + " ï¿½Îºï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ï¿ï¿½ï¿½ï¿½ï¿½Ï´ï¿½.");
			serverOutputStream = new ObjectOutputStream(sock.getOutputStream());// ï¿½ï¿½Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®ï¿½ï¿½	ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½   objectï¿½ï¿½Æ®ï¿½ï¿½
			in = sock.getInputStream();// ï¿½ï¿½Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®ï¿½Îºï¿½ï¿½ï¿½ ï¿½Þ´ï¿½ strignï¿½ï¿½Æ®ï¿½ï¿½
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
=======
	public void run() { 
		try { 
			InetAddress inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress() + " ·ÎºÎÅÍ Á¢¼ÓÇÏ¿´½À´Ï´Ù.");
			serverOutputStream = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			serverConsole = new ServerConsole(serverOutputStream);
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862

			Object temp;
			String line = null;

<<<<<<< HEAD
			while ((line = br.readLine()) != null) {
				// ï¿½ï¿½ï¿½ Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®ï¿½ï¿½ ï¿½ï¿½ï¿½Â´ï¿½ï¿½ï¿½ È®ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ ï¿½Ö³ï¿½?
				System.out.println("Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Û¹ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ú¿ï¿½ : " + line);
				handleMeg(line);
			}

			br.close();
			sock.close();
			activeCount--;
			clientList = Server.getEchoThreadList();
			for (int i = 0; i < clientList.size(); i++)// ï¿½ï¿½Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®ï¿½ï¿½ Å»ï¿½ï¿½ï¿½ï¿½ ï¿½Ø´ï¿½ Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®Ã£ï¿½Æ¼ï¿½ ï¿½ï¿½ï¿½ï¿½
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
					System.out.println("-----Å¬¶óÀÌ¾ðÆ®·Î ºÎÅÍ Àü¼Û¹ÞÀº ¹®ÀÚ¿­ : " + line);
					serverConsole.handleMeg(line);
				}
				else if(temp instanceof Posts)
				{
					System.out.println("-----Å¬¶óÀÌ¾ðÆ®·Î ºÎÅÍ Àü¼Û¹ÞÀº µ¥ÀÌÅÍ : " + temp.toString());
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
	public void handleMeg(String msg)// ï¿½ï¿½ Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ô·Â¹ï¿½ï¿½ï¿½ ï¿½ï¿½É¾î¸¦ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ Ã³ï¿½ï¿½ï¿½Ï´ï¿½ ï¿½Þ¼Òµï¿½
	// ï¿½ï¿½Ä¿ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½î¼­ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ . ex #book"È¸ï¿½Ç½ï¿½30"ï¿½ë±¸ï¿½Ï±ï¿½"ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½"01049497193
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

	//@@ï¿½ï¿½ï¿½ï¿½ Å¬ï¿½ï¿½ï¿½Ì¾ï¿½Æ®ï¿½ï¿½ï¿½ï¿½ Stringï¿½ï¿½ ï¿½Ñ°ï¿½ï¿½Ö´ï¿½ ï¿½Ô¼ï¿½
	private void sendToClientString(String line) {
		ObjectOutputStream temp = this.getOutput();
		try {
=======
>>>>>>> 3840e6fae761c98a469aa8bf144fa980f91ce862

}


