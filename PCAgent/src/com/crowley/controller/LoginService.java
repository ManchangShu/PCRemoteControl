package com.crowley.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.crowley.util.Constants;

public class LoginService extends Thread{
	ServerSocket server;
	BufferedReader br;
	String message;
	boolean stopService = false;
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(Constants.SERVICE_PORT);
			System.out.println("server start listening on port " + Constants.SERVICE_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while(!stopService) {
			try {
				Socket s = server.accept();
				System.out.println("A client comes in..." + s.getRemoteSocketAddress());
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				while((message = br.readLine()) != null) {
					MessageHandler.handleMessage(message);
				}
				System.out.println("A client comes out..." + s.getRemoteSocketAddress());
			
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("server stop listening.. crash...");
			}

		}
		
	}
	
	public void stopService() {
		try {
			stopService = true;
			server.close();
		} catch (IOException e) {
			System.out.println("server stop listening");
			e.printStackTrace();
		}
	}
	
	
}
