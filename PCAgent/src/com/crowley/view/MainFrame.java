package com.crowley.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.crowley.util.Constants;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	public final int SCREEN_HEIGHT;
	public final int SCREEN_WIDTH;
	public final int FRAME_HEIGHT = 600;
	public final int FRAME_WIDTH = 400;
	
	public MainFrame() {
		//get screen resolution
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_HEIGHT = dim.height;
		SCREEN_WIDTH = dim.width;
		
		//calculate and set location
		int x = (SCREEN_WIDTH / 2) - (FRAME_WIDTH / 2);
		int y = (SCREEN_HEIGHT / 2) - (FRAME_HEIGHT / 2);
		this.setLocation(x, y);
		
		//set size
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		
		
		//set frame properties
		this.setResizable(false);
		this.setTitle(Constants.MAIN_FRAME_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
	}
	
}
