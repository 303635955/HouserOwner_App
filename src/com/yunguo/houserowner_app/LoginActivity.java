package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.RegExpValidator;
import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
	private String str = "";
	private String id = "";
	private MyDialogUtils mydialog;
	private boolean flas = true;
	private TextView login_foundpaw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		
		init();
		
		sharedPreferences = getSharedPreferences("login_info",MODE_PRIVATE);
		if(sharedPreferences.getBoolean("AUTO_ISCHECK", false)){
			String user = sharedPreferences.getString("userName", "");
	 	    String psw = sharedPreferences.getString("password", "");
	 	    Map<String, String> loginmap = new HashMap<String, String>();
			loginmap.put("user", user);
			loginmap.put("psw", psw);
			JSONObject js= new JSONObject(loginmap);
			str = js.toString();
			flas = false;
	 	 //��ʾ�ȴ���
			mydialog.show();
	 	    new Thread(thrad).start();
		}
	}
	
	public void init(){
		mRegisterBut = (Button) findViewById(R.id.RegisterBut);
		mEditViewUser = (EditText) findViewById(R.id.register_user);
		mEditViewPassword = (EditText) findViewById(R.id.register_password);
		mCheckBox = (CheckBox) findViewById(R.id.login_box);
		login_foundpaw = (TextView) findViewById(R.id.login_foundpaw);
		
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("��¼�С���");
		
		mRegisterBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InputCheckout();
				if(form.validate()){
					mydialog.show();
					username = mEditViewUser.getText().toString();
					userpsw =mEditViewPassword.getText().toString();
					Map<String, String> loginmap = new HashMap<String, String>();
					loginmap.put("user", username);
					loginmap.put("psw", userpsw);
					JSONObject js= new JSONObject(loginmap);
					str = js.toString();
					flas = true;
					new Thread(thrad).start();
				}else{
					Toast.makeText(getApplicationContext(), "��������", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		login_foundpaw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,UpUserPasswordActivity.class);
				startActivity(intent);
			}
		});
	}	
	
	/**
	 * ����У��
	 */
	public void InputCheckout(){
		form = new Form();
		
		NotEmptyValidator notNull= new NotEmptyValidator(this);	//�ǿ���֤
		
		Validate telValidate1 = new Validate(mEditViewUser);
		telValidate1.addValidator(notNull);//�ǿ���֤
		
		Validate telValidate2 = new Validate(mEditViewPassword);
		telValidate2.addValidator(notNull);//�ǿ���֤
		
		form.addValidates(telValidate1);
		form.addValidates(telValidate2);
	}
	
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			//�رյȴ���
			if(mydialog!=null){
				mydialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "��¼ʧ�ܣ���������", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "��½�ɹ�", Toast.LENGTH_SHORT).show();
				SetUpRent.getSetUpRent().setOwnerId(id);	//�����û�Id
				
				if(flas){
					saveAccount(username,userpsw,id);
				}
				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
				intent.putExtra("testIntent",id.toString());
				startActivity(intent);
				finish();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "��¼ʧ�ܣ��˺Ż��������", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	private Thread thrad = new Thread(){
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			String url="http://120.25.65.125:8118/HouseMobileApp/HouseOwnerLogin";
			@SuppressWarnings("static-access")
			String res = httppost.PostStringToUrl(url, str);
			try {
				JSONObject josn = new JSONObject(res);
				id = josn.get("ret").toString();
				String msg = josn.get("msg").toString();
				if(msg.equals("��¼�ɹ�")){
					handler.sendEmptyMessage(1);
				}else if(msg.equals("�û������������")){
					handler.sendEmptyMessage(2);
				}else{
					handler.sendEmptyMessage(0);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(0);
			}
		};
	};
	
	
	/**
	 * ���˴ε�½���˻���Ϣ�洢����
	 * */
	@SuppressWarnings("unused")
	private void saveAccount(String username,String password,String Id) {
		
		if(flas){
			// ��ȡeditor
			Editor editor = sharedPreferences.edit();
			// ��������
			editor.putString("userName", username);
			editor.putString("password", password);
			editor.putString("OwenrId", Id);
			editor.putBoolean("AUTO_ISCHECK", true); 
			// �ύ�����ļ���
			editor.commit();
		}
	}
	
	
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
		 if(keyCode == KeyEvent.KEYCODE_BACK){
			 AlertDialog.Builder builder = new AlertDialog.Builder(
					 LoginActivity.this);

				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("�˳����");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								System.exit(0);
							}
						});
				builder.setNegativeButton("ȡ��", null);
				builder.create().show();
				return true;
			} else {
				return super.onKeyDown(keyCode, event);
			}
	 }
	
	
}
