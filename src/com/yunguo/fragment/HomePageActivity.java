package com.yunguo.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yunguo.houserowner_app.R;

public class HomePageActivity extends Activity implements OnClickListener{
	

	private Button myhouse;
	private Button mytenant;
	private Button OpenDoor;
	private Button addtenant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediaplay);
		findView();
		setOnClick();
	}
	
	public void  findView(){
		myhouse = (Button) findViewById(R.id.myhouse);
		mytenant = (Button) findViewById(R.id.mytenant);
		OpenDoor = (Button) findViewById(R.id.OpenDoor);
		addtenant = (Button) findViewById(R.id.addtenant);
	}
	
	
	public void setOnClick(){
		myhouse.setOnClickListener(this);
		mytenant.setOnClickListener(this);
		OpenDoor.setOnClickListener(this);
		addtenant.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myhouse:
			Intent intent = new Intent(HomePageActivity.this,HouseInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.mytenant:
				
			break;
		case R.id.OpenDoor:
		
			break;
		case R.id.addtenant:
		
			break;
	
		}
	}
}
