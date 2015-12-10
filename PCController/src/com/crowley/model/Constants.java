package com.crowley.model;

public class Constants {

	public static final int SERVICE_PORT = 6666;
	public static final int MESSAGE_TYPE_APP_EXIT = 0;
	public static final int MESSAGE_TYPE_MOUSE_MOVE= 1;
	public static final int MESSAGE_TYPE_MOUSE_LEFT_CLICK= 2;
	public static final int MESSAGE_TYPE_MOUSE_RIGHT_CLICK= 3;
	public static final long MAX_LEFT_CLICK_TIME = 100;
	public static final long MIN_RIGHT_CLICK_TIME = 200;
	public static final long MAX_RIGHT_CLICK_TIME = 800;
	public static final long MAX_RIGHT_CLICK_OFFSET = 8;
	public static final String MESSAGE_SEPARATOR = "|";
	public static final int SOCKET_TIMEOUT= 500;
	
}
