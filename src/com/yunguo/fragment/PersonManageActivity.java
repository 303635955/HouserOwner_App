package com.yunguo.fragment;

import java.util.Calendar;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.IDCardReader.utility.DoubleDatePickerDialog;
import com.IDCardReader.utility.OloneDatePikerDialog;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.houserowner_app.CardMessageActivity;
import com.yunguo.houserowner_app.IDCardCode;
import com.yunguo.houserowner_app.R;
import com.yunguo.houserowner_app.RegistRentPerson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonManageActivity extends Activity{
	
	private TextView DoorId,UserName,Age,Sex,Birthday,Tel;
	private ImageView backimg;
	private Map<String,String> map = null;
	private Button openpermissions;
	private Button myhouse,mytenant,OpenDoor,addtenant;
	
	private String userid;
	private ProgressDialog pd;
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
		
		openpermissions = (Button) findViewById(R.id.openpermissions);
		myhouse= (Button) findViewById(R.id.myhouse);
		mytenant= (Button) findViewById(R.id.mytenant);
		OpenDoor= (Button) findViewById(R.id.OpenDoor);
		addtenant= (Button) findViewById(R.id.addtenant);
		
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
	
	private Thread thread = new Thread( ){
		@Override
		public void run() {
			String url = "http://120.25.65.125:8118/HouseMobileApp/RentalInfo";
			JSONObject json;
			try {
				json = new JSONObject(userid);
				String res = HTTPUtil.PostStringToUrl(url, json.toString());
				JSONObject rejson = new JSONObject(res);
				String back = rejson.get("ret")+"";
				if(back.equals("")){
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	
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
				intent.putExtra("UserId",map.get("UserId"));
				startActivity(intent);
			}
		});
		
		myhouse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,RenewalActivity.class);
				intent.putExtra("UserId",map.get("UserId"));
				startActivity(intent);
			}
		});
		
		mytenant.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,CancellationActivity.class);
				intent.putExtra("UserId",map.get("UserId"));
				startActivity(intent);
			}
		});
	}
}
