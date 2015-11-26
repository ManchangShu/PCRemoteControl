package com.crowley.test;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.crowley.util.Constants;

public class AndroidClientSimulator {

	public static void main(String[] args) {
		try {
			Socket s = new Socket("127.0.0.1", Constants.SERVICE_PORT);
			s.setKeepAlive(true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			bw.write("I am Crowley~ 1\n");
			bw.write("I am Crowley~ 2\n");
			bw.write("I am Crowley~ 3  ÓÐÃ»ÓÐÂÒÂë£¿\n");
			bw.flush();
			Thread.sleep(10000);
			System.out.println("closing ...");
			bw.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
