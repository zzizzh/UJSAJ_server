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

				Socket sock = server.accept(); // 접속 대기

				EchoThread echothread = new EchoThread(sock, clientNumber++); // 클라이언트
																				// 접속
																				// 시
																				// 실행되며
				// echoThread 생성
				echothread.start(); // run()메소드 실행

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

class EchoThread extends Thread { // ＆클라이언트간 멀티쓰레드 구현
	private ArrayList<EchoThread> clientList;
	static int activeCount = 0;

	private Socket sock;
	private int clientNumber;

	private DBManager dbManager;
	
	ObjectOutputStream serverOutputStream;
	OutputStream out;
	InputStream in;
	PrintWriter pw;
	BufferedReader br;

	public EchoThread(Socket sock, int clientNumber) {
		this.sock = sock;
		activeCount++; // 해당 클레스 생성 시 값 증가
		this.clientNumber = clientNumber;
		Server.getEchoThreadList();
	} // 생성자

	public int getClientNumber() {
		return clientNumber;
	}

	public ObjectOutputStream getOutput() {
		return serverOutputStream;
	}

	public void run() { // start() 실행 시 호출
		try { // I/O 등 기능 구현
			InetAddress inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress() + " 로부터 접속하였습니다.");
			serverOutputStream = new ObjectOutputStream(sock.getOutputStream());// ＆클라이언트로	보내는   object스트림
			in = sock.getInputStream();// ＆클라이언트로부터 받는 strign스트림
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String line = null;

			while ((line = br.readLine()) != null) {
				// 어느 클라이언트가 보냈는지 확인 할 수 있나?
				System.out.println("클라이언트로 부터 전송받은 문자열 : " + line);
				handleMeg(line);
			}

			br.close();
			sock.close();
			activeCount--;
			clientList = Server.getEchoThreadList();
			for (int i = 0; i < clientList.size(); i++)// ＆클라이언트가 탈락시 해당 클라이언트찾아서 삭제
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

	public void handleMeg(String msg)// ＆ 클라이언트로 부터 입력받을 명령어를 서버에서 처리하는 메소드
	// 토커나이즈로 끊어서 구현할 것 . ex #book"회의실30"대구북구"노혜성"01049497193
	{
		StringTokenizer token = new StringTokenizer(msg, "#");
		
		if (msg.startsWith("#login")) {
			String pw = dbManager.getPWByID(token.nextToken());
			if (pw.equals(token.nextToken()))
				sendToClientString("#success");
			else
				sendToClientString("#false");
		}
		else if (msg.startsWith("#regist")) {
			if (dbManager.getPWByID(token.nextToken()) != null)
				sendToClientString("#success");
			else 
				sendToClientString("#false");
			
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

	//@@현재 클라이언트에게 String을 넘겨주는 함수
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
