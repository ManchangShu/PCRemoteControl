package com.crowley.model;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;


public class NetworkModule extends Thread{
	private static boolean stopService;
	private static boolean loginSuccess;
	private String ip;
	private static Socket s;
	BufferedWriter bw;
	
	public NetworkModule(String ip) {
		stopService = false;
		loginSuccess = false;
		this.ip = ip;
	}
	
	@Override
	public void run() {
		try {
			s = new Socket();
			InetAddress inetAddress = InetAddress.getByName(ip);
			s.connect(new InetSocketAddress(inetAddress, Constants.SERVICE_PORT), Constants.SOCKET_TIMEOUT);
			s.setKeepAlive(true);
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			System.out.println("begin write.. ...");
			loginSuccess = true;
			while(!stopService) {
				String message = MessageQueue.getMessage();
				System.out.println("send message:" + message);
				bw.write(message + "\n");
				bw.flush();
			}
			System.out.println("exit ...");
			bw.close();
			s.close();
		} catch (Exception e) {
			System.out.println("exception occured:" + e.getMessage());
			e.printStackTrace();
		} 
	}
	
	public static boolean loginSuccess() {
		return loginSuccess;
	}
	
	public static void stopService() {
		stopService = true;
	}
	
	
}
