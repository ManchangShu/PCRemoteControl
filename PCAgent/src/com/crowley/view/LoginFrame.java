package com.crowley.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.crowley.controller.LoginService;
import com.crowley.util.Constants;
import com.crowley.util.NetworkUtil;

public class LoginFrame extends JFrame {
	private JLabel ipAddress;
	private JLabel ipHint;
	private JLabel authorInfo;
	private LoginService service;
	private static final long serialVersionUID = -8325533949922000258L;
	
	public final int SCREEN_HEIGHT;
	public final int SCREEN_WIDTH;
	public final int FRAME_HEIGHT = 600;
	public final int FRAME_WIDTH = 400;
	

	public static void main(String[] args) {
		LoginService service = new LoginService();
		//service.setDaemon(true);//when the main thread exit, login service exit.
		service.start();
		new LoginFrame(service);
	}
	
	public LoginFrame(LoginService service) {
		this.service = service;
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
		
		//set IP address on the main board
		this.setIPHint();
		this.setIPAddress();
		
		//set author info on the bottom of JFrame
		this.setAuthorInfo();
		
		//
		this.addWindowListener(new WindowCloseListener());
		
		//set frame properties
		this.setResizable(false);
		this.setTitle(Constants.MAIN_FRAME_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
	}
	


	private void setIPHint() {
		ipHint = new JLabel();
		ipHint.setSize(FRAME_WIDTH, 50);
		ipHint.setLocation(0, 20);
		ipHint.setFont(new Font("Monospaced", Font.BOLD, 20));
		ipHint.setText(Constants.INPUT_IP_HINT);
		ipHint.setHorizontalAlignment(JLabel.CENTER);
		this.add(ipHint);
	}
	
	private void setIPAddress() {
		ipAddress = new JLabel();
		ipAddress.setSize(FRAME_WIDTH, 50);
		ipAddress.setLocation(0, 80);
		ipAddress.setFont(new Font("Monospaced", Font.BOLD, 28));
		ipAddress.setText(NetworkUtil.getIPAddress());
		ipAddress.setHorizontalAlignment(JLabel.CENTER);
		this.add(ipAddress);
	}
	
	private void setAuthorInfo() {
		authorInfo = new JLabel();
		authorInfo.setSize(FRAME_WIDTH, 50);
		authorInfo.setLocation(88, FRAME_HEIGHT - 80);
		authorInfo.setFont(new Font("ËÎÌו", Font.BOLD, 15));
		authorInfo.setText(Constants.AUTHOR_INFO);
		authorInfo.setHorizontalAlignment(JLabel.CENTER);
		this.add(authorInfo);
	}
	
	private final class WindowCloseListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			LoginFrame.this.service.stopService();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			
		}
		
	}
	
	
}
