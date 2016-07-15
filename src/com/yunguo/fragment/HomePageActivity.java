package com.yunguo.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.yunguo.houserowner_app.AddPersonActivity;
import com.yunguo.houserowner_app.IDCardCode;
import com.yunguo.houserowner_app.LoginActivity;
import com.yunguo.houserowner_app.MainActivity;
import com.yunguo.houserowner_app.OpenDoorActivity;
import com.yunguo.houserowner_app.OwnerMessageActivity;
import com.yunguo.houserowner_app.R;
import com.yunguo.houserowner_app.RegistRentPerson;
import com.yunguo.houserowner_app.RemoteOpenDoorActivity;

public class HomePageActivity extends Activity implements OnClickListener{
	

	private Button myhouse;
	private Button mytenant;
	private Button OpenDoor;
	private Button addtenant;
	private ImageView usermsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		usermsg = (ImageView) findViewById(R.id.usermsg);
	}
	
	
	public void setOnClick(){
		myhouse.setOnClickListener(this);
		mytenant.setOnClickListener(this);
		OpenDoor.setOnClickListener(this);
		addtenant.setOnClickListener(this);
		
		
		usermsg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myhouse:
			Intent intent = new Intent(HomePageActivity.this,HouseInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.mytenant:
			Intent intent2 = new Intent(HomePageActivity.this,PersonInfoActivity.class);
			startActivity(intent2);
			break;
		case R.id.OpenDoor:
			Intent intent3 = new Intent(HomePageActivity.this,OpenDoorActivity.class);
			startActivity(intent3);
			break;
		case R.id.addtenant:
			Intent intentadd = new Intent(HomePageActivity.this,AddPersonActivity.class);
			startActivity(intentadd);
			break;
		case R.id.usermsg:
			Intent intent4 = new Intent(HomePageActivity.this,OwnerMessageActivity.class);
			startActivity(intent4);
		break;
		}
	}
}
