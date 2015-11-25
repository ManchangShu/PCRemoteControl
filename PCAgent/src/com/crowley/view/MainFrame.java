package com.crowley.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8325533949922000258L;
	public final int SCREEN_HEIGHT;
	public final int SCREEN_WIDTH;
	public final int MAIN_FRAME_HEIGHT = 600;
	public final int MAIN_FRAME_WIDTH = 400;
	public final String MAIN_FRAME_TITLE = "DesktopController";
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
	public MainFrame() {
		//get screen resolution
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_HEIGHT = dim.height;
		SCREEN_WIDTH = dim.width;
		
		//calculate and set location
		int x = (SCREEN_WIDTH / 2) - (MAIN_FRAME_WIDTH / 2);
		int y = (SCREEN_HEIGHT / 2) - (MAIN_FRAME_HEIGHT / 2);
		this.setLocation(x, y);
		
		//set size
		this.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		
		this.setResizable(false);
		this.setTitle(MAIN_FRAME_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
