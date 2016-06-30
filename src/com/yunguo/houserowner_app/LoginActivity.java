package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.yunguo.Util.HTTPUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText mEditViewUser, mEditViewPassword;
	private ImageButton mClearUserBut, mClearPasswordBut;
	private Button mRegisterBut, mLoginBut;
	private CheckBox mCheckBox;
	private SharedPreferences preferences;
	private SQLiteDatabase db;
	private Cursor cur;
	private HTTPUtil httppost; 
	String str = "";
	Object id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		
		init();
	}
	
	public void init(){
		mRegisterBut = (Button) findViewById(R.id.RegisterBut);
		mEditViewUser = (EditText) findViewById(R.id.register_user);
		mEditViewPassword = (EditText) findViewById(R.id.register_password);
		mCheckBox = (CheckBox) findViewById(R.id.login_box);
		
		mRegisterBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = mEditViewUser.getText().toString();
				String userpsw =mEditViewPassword.getText().toString();
				Map<String, String> loginmap = new HashMap<String, String>();
				loginmap.put("user", username);
				loginmap.put("psw", userpsw);
				JSONObject js= new JSONObject(loginmap);
				str = js.toString();
				if(username !=null && userpsw != null){
					thrad.start();
				}
			}
		});
	}	
	
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "数据传输失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
				intent.putExtra("testIntent",id.toString());
				startActivity(intent);
				break;
			}
		};
	};
	
	private Thread thrad = new Thread(){
		@Override
		public void run() {
			String url="http://192.168.1.144:8118/HouseMobileApp/HouseOwnerLogin";
			String res = httppost.PostStringToUrl(url, str);
			try {
				JSONObject josn = new JSONObject(res);
				id = josn.get("ret").toString();
				String msg = josn.get("msg").toString();
				if(msg.equals("登录成功")){
					handler.sendEmptyMessage(1);
				}else {
					handler.sendEmptyMessage(0);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		};
	};
}
