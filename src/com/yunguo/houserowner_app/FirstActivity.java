package com.yunguo.houserowner_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class FirstActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(isFirstStart(this)){
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(50);
						Intent it=new Intent(FirstActivity.this,LoginActivity.class);
						startActivity(it);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();	
		}else{
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(50);
						Intent it=new Intent(FirstActivity.this,LoadingActivity.class);
						startActivity(it);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();	
		}
		
		FirstActivity.this.finish();
	}
	
	public static boolean isFirstStart(Context context) {
	    SharedPreferences preferences = context.getSharedPreferences(
	            "SHARE_APP_TAG", 0);
	    Boolean isFirst = preferences.getBoolean("FIRSTStart", true);
	    if (isFirst) {// µÚÒ»´Î
	        preferences.edit().putBoolean("FIRSTStart", false).commit();
	        return true;
	    } else {
	        return false;
	    }
	}
}
