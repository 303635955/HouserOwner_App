package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.RegExpValidator;
import com.yunguo.Util.HTTPUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	private Button mRegisterBut, mLoginBut;
	private CheckBox mCheckBox;
	private SQLiteDatabase db;
	private HTTPUtil httppost; 
	private String username,userpsw;
	private SharedPreferences sharedPreferences;
	private ProgressDialog progressDialog;
	private Form form;
	String str = "";
	Object id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		sharedPreferences = getSharedPreferences("login_info",MODE_PRIVATE);
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
				InputCheckout();
				if(form.validate()){
					progressDialog = ProgressDialog.show(LoginActivity.this, "请稍等", "正在登录...", true);
					username = mEditViewUser.getText().toString();
					userpsw =mEditViewPassword.getText().toString();
					Map<String, String> loginmap = new HashMap<String, String>();
					loginmap.put("user", username);
					loginmap.put("psw", userpsw);
					JSONObject js= new JSONObject(loginmap);
					str = js.toString();
					new Thread(thrad).start();
				}else{
					Toast.makeText(getApplicationContext(), "输入有误", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}	
	
	/**
	 * 输入校验
	 */
	public void InputCheckout(){
		form = new Form();
		
		NotEmptyValidator notNull= new NotEmptyValidator(this);	//非空验证
		
		Validate telValidate1 = new Validate(mEditViewUser);
		telValidate1.addValidator(notNull);//非空验证
		
		Validate telValidate2 = new Validate(mEditViewPassword);
		telValidate2.addValidator(notNull);//非空验证
		
		form.addValidates(telValidate1);
		form.addValidates(telValidate2);
	}
	
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			//关闭等待窗
			if(progressDialog!=null){
				progressDialog.dismiss();
			}
			
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "登录失败，请检查网络", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
				saveAccount(username,userpsw);
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
			String url="http://120.25.65.125:8118/HouseMobileApp/HouseOwnerLogin";
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
	
	
	/**
	 * 将此次登陆的账户信息存储下来
	 * */
	@SuppressWarnings("unused")
	private void saveAccount(String username,String password) {
		    // 获取editor
		    Editor editor = sharedPreferences.edit();
		    // 存入数据
		    editor.putString("userName", username);
		    editor.putString("password", password);
		    editor.putBoolean("AUTO_ISCHECK", true); 
		    // 提交存入文件中
		    editor.commit();
	}
}
