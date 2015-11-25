package com.crowley.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkUtil {

	/**
	 * Get local PC IP address
	 * @return IP address
	 */
	public static String getIPAddress() {
		String ip = null;
		BufferedReader br = null;
		try {
			Process process = Runtime.getRuntime().exec("netstat -rn");
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while((line = br.readLine()) != null) {
				if(line.contains(" 0.0.0.0 ") || line.contains(" default ")) {
					System.out.println(line.trim());
					ip = (line.trim().split("\\s+"))[3];
					System.out.println(ip);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ip;
	}
	
}
