package com.crowley.pccontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends Activity {
	EditText etIPAddress;
	ImageView ivLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		etIPAddress = (EditText) findViewById(R.id.ip_address);
		ivLogin = (ImageView) findViewById(R.id.image_login);
		
		ivLogin.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					ivLogin.setImageDrawable(getResources().getDrawable(R.drawable.login_login_press));
					break;
					
				case MotionEvent.ACTION_UP:
					ivLogin.setImageDrawable(getResources().getDrawable(R.drawable.login_login));
				default:
					break;
				}
				return true;
			}
		});
		
		
	}
	@SuppressWarnings("deprecation")
	public void login(View view) {
		//ÅÐ¶ÏIPÊÇ·ñÕýÈ· 0-255
		String IPPattern = "";
		Intent intent = new Intent(this, MainActivity.class);
		//this.startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//new LoginService().start();
		
	}

}
