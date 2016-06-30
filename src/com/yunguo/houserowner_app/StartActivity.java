package com.yunguo.houserowner_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;


public class StartActivity extends Activity{
	  private Handler handler=new Handler();
	    protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	setContentView(R.layout.activity_start);
	    	
	    	handler.postDelayed(new Runnable(){
				public void run() {
					startActivity(new Intent(StartActivity.this,FirstActivity.class));
					finish();	
				}
	    		
	    	},3000);
	    }
}
