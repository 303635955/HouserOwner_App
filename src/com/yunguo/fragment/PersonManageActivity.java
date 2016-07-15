package com.yunguo.fragment;

import java.util.Map;

import com.yunguo.Bean.SetUpRent;
import com.yunguo.houserowner_app.CardMessageActivity;
import com.yunguo.houserowner_app.R;
import com.yunguo.houserowner_app.RenewalActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonManageActivity extends Activity{
	
	private TextView DoorId,UserName,Age,Sex,Birthday,Tel,inTime,enTime;
	private ImageView backimg;
	private Map<String,String> map = null;
	private Button openpermissions;
	private Button cardreport;
	private Button registerCard;
	private Button myhouse;
	private Button mytenant;
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
		inTime = (TextView) findViewById(R.id.inTime);
		enTime = (TextView) findViewById(R.id.enTime);
		openpermissions = (Button) findViewById(R.id.openpermissions);
		
		cardreport = (Button) findViewById(R.id.cardreport);
		registerCard = (Button) findViewById(R.id.registerCard);
		myhouse = (Button) findViewById(R.id.myhouse);
		mytenant= (Button) findViewById(R.id.mytenant);
		
		backimg = (ImageView) findViewById(R.id.backimg);
	}
	
	public void setData(){
		DoorId.setText(map.get("DoorId"));
		String username = map.get("UserName");
		UserName.setText(username);
		
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
		inTime.setText(map.get("CheckInTime"));
		enTime.setText(map.get("CheckOutTime"));
	}
	
	public void setOnClick(){
		backimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		openpermissions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","权限");
				intent.putExtra("UserId",map.get("UserId"));
				startActivity(intent);
			}
		});
		
		cardreport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","挂失");
				intent.putExtra("UserId",map.get("UserId"));
				startActivity(intent);
			}
		});
		
		registerCard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","补办");
				intent.putExtra("UserId",map.get("UserId"));
				intent.putExtra("HouseId",SetUpRent.getSetUpRent().getHouseId());
				startActivity(intent);
			}
		});
		
		myhouse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","续期");
				intent.putExtra("HouseId",SetUpRent.getSetUpRent().getHouseId());
				intent.putExtra("UserId",map.get("UserId"));
				intent.putExtra("DoorId",map.get("DoorId"));
				intent.putExtra("enTime",map.get("CheckOutTime"));
				startActivity(intent);
			}
		});
		
		mytenant.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","注销");
				intent.putExtra("UserId",map.get("UserId"));
				startActivity(intent);
			}
		});
	}
}
