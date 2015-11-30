package com.crowley.model;

import java.util.LinkedList;

public class MessageQueue {
	
	private LinkedList<String> messageQueue = new LinkedList<String>();
	
	
	public synchronized String getMessage() {
		//System.out.println("get message ...");
		String message = null;
		while(messageQueue.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		message = messageQueue.pollFirst();
		return message;
	}
	
	public synchronized void setMessage(String message) {
		//System.out.println("set message ...");
		messageQueue.offerLast(message);
		this.notifyAll();
	}
	
	public synchronized void clearQueue() {
		messageQueue.clear();
	}
}
