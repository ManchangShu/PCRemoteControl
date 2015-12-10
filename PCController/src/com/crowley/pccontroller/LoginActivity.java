package com.crowley.pccontroller;

import java.util.concurrent.TimeUnit;

import com.crowley.model.Constants;
import com.crowley.model.NetworkModule;
import com.crowley.model.Persistence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText etIPAddress;
	ImageView ivLogin;
	CheckBox cbRemIP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		etIPAddress = (EditText) findViewById(R.id.ip_address);
		ivLogin = (ImageView) findViewById(R.id.image_login);
		cbRemIP = (CheckBox) findViewById(R.id.remember_ip);
		etIPAddress.setText(Persistence.getIP(this));
	}
	
	public void login(View view) {
		ivLogin.setEnabled(false);
		String ipAddress = etIPAddress.getText().toString();
		if(!checkLogin(ipAddress)) {
			return;
		}
		rememberIpAddress(ipAddress);
		new NetworkModule(ipAddress).start();
		
		try {
			TimeUnit.MILLISECONDS.sleep(Constants.SOCKET_TIMEOUT + 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(!NetworkModule.loginSuccess()) {
			Toast.makeText(this, R.string.socket_timeout, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, R.string.socket_login_successful, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
		}
		
		ivLogin.setEnabled(true);
		
	}
	
	//check the correctness of IP address
	private boolean checkLogin(String ipAddress) {
		String iPPattern = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
				+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
				+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
				+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
		
		if(!ipAddress.matches(iPPattern)) {
			Toast.makeText(this, R.string.ip_wrong_hint, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	private void rememberIpAddress(String ipAddress) {
		boolean checked = cbRemIP.isChecked();
		if(checked) {
			Persistence.saveIP(this, ipAddress);
		} else {
			Persistence.saveIP(this, "");
		}
	}
	
}
