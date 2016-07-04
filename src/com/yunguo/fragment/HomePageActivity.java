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
import com.yunguo.houserowner_app.R;
import com.yunguo.houserowner_app.RegistRentPerson;

public class HomePageActivity extends Activity implements OnClickListener{
	

	private Button myhouse;
	private Button mytenant;
	private Button OpenDoor;
	private Button addtenant;
	private ImageView backimg;
	
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
		backimg = (ImageView) findViewById(R.id.backimg);
	}
	
	
	public void setOnClick(){
		myhouse.setOnClickListener(this);
		mytenant.setOnClickListener(this);
		OpenDoor.setOnClickListener(this);
		addtenant.setOnClickListener(this);
		
		
		backimg.setOnClickListener(this);
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
		
			break;
		case R.id.addtenant:
			Intent intentadd = new Intent(HomePageActivity.this,AddPersonActivity.class);
			startActivity(intentadd);
			break;
		case R.id.backimg:
			AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("注销登录？");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							SharedPreferences sharedPreferences = getSharedPreferences("login_info",MODE_PRIVATE);
							sharedPreferences.edit().clear().commit();
							Intent intent = new Intent(HomePageActivity.this,LoginActivity.class);
							startActivity(intent);
							finish();
						}
					});
			builder.setNegativeButton("取消", null);
			builder.create().show();
		break;
	
		}
	}
}
