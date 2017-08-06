package Implement;

import java.io.InputStream;
import java.util.Scanner;

import ProblemDomain.Posts;
import test.Client;

public class startClient {
	Client clientstart;
	
	public startClient()
	{	
		clientstart =new Client("127.0.0.1", 11114);
	}

	protected Client getClient(){
		return clientstart;
	}
	
	public static void main(String[] args) {

		startClient client = new startClient();
		Scanner scan = new Scanner(System.in);
		
		String str;
		
		while(true){
			str = scan.nextLine();
			
			client.getClient().sendToServer(str);
			
			if(str.compareTo("#post") == 0)
				client.getClient().sendToServer(new Posts());
		}
	}
}
