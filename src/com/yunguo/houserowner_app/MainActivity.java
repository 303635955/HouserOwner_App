package com.yunguo.houserowner_app;

import com.yunguo.fragment.HomePageActivity;
import com.yunguo.fragment.HouseInfoActivity;
import com.yunguo.fragment.PersonInfoFragment;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
    private RadioGroup radioGroup;
    private LinearLayout centerlinear;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	    
	    findView();
	    setAdapter();
	}
	
	@SuppressWarnings("deprecation")
	public void findView(){
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		centerlinear = (LinearLayout) findViewById(R.id.centerlinear);
		//设置初始界面
		centerlinear.addView(getLocalActivityManager().startActivity(
				"v1",
				new Intent(MainActivity.this, HomePageActivity.class).putExtra("values", "0")
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
	}
	
	public void setAdapter(){
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	            @SuppressWarnings("deprecation")
				@Override
	            public void onCheckedChanged(RadioGroup group, int checkedId) {
	            	centerlinear.removeAllViews();
	            	switch (checkedId) {
					case R.id.radio0:
						centerlinear.addView(getLocalActivityManager().startActivity(
								"v1",
								new Intent(MainActivity.this, HomePageActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView());
						break;
					case R.id.radio1:
						centerlinear.addView(getLocalActivityManager().startActivity(
								"v1",
								new Intent(MainActivity.this, HouseInfoActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView());
						break;
					case R.id.radio2:
						centerlinear.addView(getLocalActivityManager().startActivity(
								"v1",
								new Intent(MainActivity.this, PersonInfoFragment.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView());
						break;
	            	}
	                
	            }
	        });
	}
	

}
