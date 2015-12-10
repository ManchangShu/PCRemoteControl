package com.crowley.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Persistence {

	public static void saveIP(Context context, String ip) {
		SharedPreferences preferences = context.getSharedPreferences("IP", Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString("ip", ip);
		edit.commit();
	}
	
	public static String getIP(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("IP", Context.MODE_PRIVATE);
		return preferences.getString("ip", "");
	}
	
}
