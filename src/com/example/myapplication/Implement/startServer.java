package com.example.myapplication.Implement;

import com.example.myapplication.PhysicalArchitecture.Server;

public class startServer {
	public startServer()
	{	
		Server serverstart=new Server(11114);
		serverstart.start();
	}
	
	public static void main(String[] args) {

		startServer server = new startServer();
	}
}