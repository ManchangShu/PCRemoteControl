package com.crowley.pccontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	EditText etIPAddress;
	Button btLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		etIPAddress = (EditText) findViewById(R.id.ip_address);
		btLogin = (Button) findViewById(R.id.login);
		
		
	}
	public void login(View view) {
		//ÅÐ¶ÏIPÊÇ·ñÕýÈ· 0-255
		String IPPattern = "";
		Intent intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//new LoginService().start();
		
	}

}
