package com.yunguo.houserowner_app;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;


public class StartActivity extends Activity{
	
	private SharedPreferences sharedPreferences;
	
	  private Handler handler=new Handler();
	    protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	setContentView(R.layout.activity_start);
	    	
	    	handler.postDelayed(new Runnable(){
				public void run() {
					if(isFirstStart(StartActivity.this)){
						Intent it=new Intent(StartActivity.this,LoginActivity.class);
						startActivity(it);
					}else{
						Intent it=new Intent(StartActivity.this,MainActivity.class);
						startActivity(it);
					}
					StartActivity.this.finish();
				}
	    	},3000);
	    }
	    
	    
    public  boolean isFirstStart(Context context) {
		sharedPreferences = context.getSharedPreferences("login_info",MODE_PRIVATE);
		
		if(sharedPreferences.getBoolean("AUTO_ISCHECK", false)){
			return false;
		}
		return true;
	}
}
