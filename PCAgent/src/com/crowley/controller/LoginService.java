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
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(Constants.SERVICE_PORT);
			System.out.println("server started on port " + Constants.SERVICE_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while(true) {
			try {
				Socket s = server.accept();
				System.out.println("A client comes in...");
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				while((message = br.readLine()) != null) {
					System.out.println("message:" + message);
				}
				System.out.println("A client comes out...");
			
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}
	
	
}
