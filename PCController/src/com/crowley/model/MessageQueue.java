package com.crowley.model;

import java.util.LinkedList;

public class MessageQueue {
	
	private static LinkedList<String> messageQueue = new LinkedList<String>();
	private static Object lock = new Object();
	
	public static String getMessage() {
		String message = null;
		synchronized (lock) {
			
			while(messageQueue.isEmpty()) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			message = messageQueue.pollFirst();
		}
		return message;
	}
	
	public static void pushMessage(String message) {
		synchronized (lock) {
			messageQueue.offerLast(message);
			lock.notifyAll();
		}
		
	}
	
	public static void clearQueue() {
		synchronized (lock) {
			messageQueue.clear();
		}
	}
}
