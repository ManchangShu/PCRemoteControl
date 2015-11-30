package com.crowley.util;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class ScreenUtil {

	private Robot robot;
	
	public ScreenUtil() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void leftButtonClick() {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void rightButtonClick() {
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
	
	public void mouseMove(int xOffset, int yOffset) {
		Point curPos = MouseInfo.getPointerInfo().getLocation();
		robot.mouseMove((int)curPos.getX() + xOffset, (int)curPos.getY() + yOffset);
	}
	
}
