package com.yunguo.fragment;

import java.util.Map;

import com.yunguo.houserowner_app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonManageActivity extends Activity{
	
	private TextView DoorId,UserName,Age,Sex,Birthday,Tel;
	private ImageView backimg;
	private Map<String,String> map = null;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personmanage_activity);
		
		/**
		 * 获取数据
		 */
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		map = (Map<String, String>) bundle.get("person");
		
		findView();
		setData();
		setOnClick();
	}
	
	public void findView(){
		DoorId = (TextView) findViewById(R.id.DoorId);
		UserName = (TextView) findViewById(R.id.UserName);
		Age = (TextView) findViewById(R.id.Age);
		Sex = (TextView) findViewById(R.id.Sex);
		Birthday = (TextView) findViewById(R.id.Birthday);
		Tel = (TextView) findViewById(R.id.Tel);
		
		backimg = (ImageView) findViewById(R.id.backimg);
	}
	
	public void setData(){
		DoorId.setText(map.get("DoorId"));
		UserName.setText(map.get("UserName"));
		
		String age = map.get("Sex");
		if(age.equals("1")){
			age = "男";
		}else{
			age = "女";
		}
		
		Age.setText(map.get("Age"));
		Sex.setText(age);
		Birthday.setText(map.get("Birthday"));
		Tel.setText(map.get("Tel"));
	}
	
	public void setOnClick(){
		backimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
