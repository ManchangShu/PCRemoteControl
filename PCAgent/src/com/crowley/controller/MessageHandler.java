package com.crowley.controller;

import com.crowley.util.Constants;
import com.crowley.util.ScreenUtil;


public class MessageHandler {
	private static ScreenUtil screenUtil = new ScreenUtil();
	
	public synchronized static void handleMessage(String message) {
		if(message == null || message.equals("")) {
			return;
		}
		System.out.println("message:" + message);
		String datas[] = message.split("\\|");

		int messageType = Integer.parseInt(datas[0]);
		
		switch (messageType) {
		case Constants.MESSAGE_TYPE_MOUSE_MOVE:
			screenUtil.mouseMove(Integer.parseInt(datas[1]), Integer.parseInt(datas[2]));
			break;
		case Constants.MESSAGE_TYPE_MOUSE_LEFT_CLICK:
			screenUtil.leftButtonClick();
			break;
		case Constants.MESSAGE_TYPE_MOUSE_RIGHT_CLICK:
			screenUtil.rightButtonClick();
			break;
		case Constants.MESSAGE_TYPE_APP_EXIT:
			System.out.println("Android client quit.");
			//TODO
			break;
			
		default:
			break;
		}
		
	}
	
}
