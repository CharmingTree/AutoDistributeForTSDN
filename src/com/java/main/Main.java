package com.java.main;

import java.io.IOException;
import java.net.ServerSocket;

import com.java.UI.MainFrame;

public class Main {

	//private ServerSocket serverSocket;
	
	public void process(int port)
	{

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket serverSocket = new ServerSocket(2011);
			new MainFrame();
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		
		
	}

}
