package com.yunguo.houserowner_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;

public class LoadingActivity extends Activity {
    private ProgressBar mpProgressBar;
    private Handler mHandler=new Handler();
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.activity_loading);
    	
    	 mpProgressBar=(ProgressBar) findViewById(R.id.loading_progressbar);
    	 
    	 mHandler.postDelayed(new Runnable(){
			public void run() {
			    startActivity( new Intent(LoadingActivity.this,MainActivity.class));	
			    finish();
			}
    		 
    	 }, 3000);
    }
}
