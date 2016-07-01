package com.yunguo.houserowner_app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RentOwnerActivity extends Activity {
	private Map<String, String> s = new HashMap<String, String>();
	
	private EditText person_housename;
	private EditText person_houseid;
	private EditText person_housecardid;
	private EditText person_begintime;
	private EditText person_endtime;

	private Spinner Sex, Nationnal;
	private EditText Office, CardDate, Adress, Term;
	private Button btn,btn_back;

	String Sextext, nation;
	String[] mItems,mItems1; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personinfo_view);
		// 获得意图
		Intent intent = getIntent();
		// 得到数据集
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			s = (Map<String, String>) bundle.get("LoginedIpInfoS");
		} else {

		}
		
		findview();
		//添加点击事件
		setclick();
	}

	public void findview() {
		person_housename = (EditText) findViewById(R.id.person_housename_text);
		person_houseid = (EditText) findViewById(R.id.person_houseid_text);
		person_housecardid = (EditText) findViewById(R.id.person_housedoorid_text);
		person_begintime = (EditText) findViewById(R.id.person_begintime_text);
		person_endtime = (EditText) findViewById(R.id.person_endtime_text);

		btn_back = (Button) findViewById(R.id.regist_info_one);
		btn = (Button) findViewById(R.id.regist_info_two);
	}

	public void setclick() {
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				s.put("HouseName", person_housename.getText().toString());
				s.put("HouseID", person_houseid.getText().toString());
				s.put("HouseDoorCard", person_housecardid.getText().toString());
				s.put("HouseBeginTime", person_begintime.getText().toString());
				s.put("HouseEndTime", person_endtime.getText().toString());
				Intent intent = new Intent(getApplicationContext(),
						RentOwnerOtherActivity.class);
				Bundle extras = new Bundle();
				extras.putSerializable("LoginedIpInfoS", (Serializable) s);// /java.util.HashMap.
				intent.putExtras(extras);
				startActivity(intent);
				finish();
			}
		});
		
		btn_back.setOnClickListener(registGoBack);
	}
	
	public OnClickListener registGoBack = new OnClickListener() {

		public void onClick(View v) {
			finish();
		}
	};
	
}
