package com.crowley.model;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import com.crowley.pccontroller.MainActivity;


public class LoginService extends Thread{
	boolean stopService = false;
	Socket s;
	BufferedWriter bw;
	
	//TODO add accurate control of stop service
	@Override
	public void run() {
		try {
			System.out.println("new socket ...D");
			System.out.println("is ip reachable? " + InetAddress.getByName("192.168.1.102").isReachable(200));
			
			s = new Socket("192.168.1.102", Constants.SERVICE_PORT);
			System.out.println("new socked finished ...");
			s.setKeepAlive(true);
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			System.out.println("begin write.. ...");
			
			while(!stopService) {
				String message = MainActivity.messageQueue.getMessage();
				System.out.println("send message:" + message);
				bw.write(message + "\n");
				bw.flush();
			}
			
			bw.flush();
			System.out.println("closing ...");
			bw.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	
}
