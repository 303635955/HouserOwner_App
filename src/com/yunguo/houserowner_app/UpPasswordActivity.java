package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpPasswordActivity extends Activity {
	
	private EditText UserPasswd,NewUserPasswd,NewUserPasswd2;
	private Button sub_userpasswd;
	private String OwenrId,UserPassword,NewUserPassword,NewUserPassword2,IdCardNo;
	private String messge;
	private Form form;
	private MyDialogUtils mydialog;
	private ImageView backimg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uppassword);
		Init();
		setOnClick();
	}
	private void Init(){
		UserPasswd = (EditText) findViewById(R.id.UserPasswd);
		NewUserPasswd = (EditText) findViewById(R.id.NewUserPasswd);
		NewUserPasswd2 = (EditText) findViewById(R.id.NewUserPasswd2);
		sub_userpasswd = (Button) findViewById(R.id.sub_userpasswd);
		backimg = (ImageView) findViewById(R.id.backimg);
		
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("请求中……");
	}
	
	
	public void setOnClick(){
		sub_userpasswd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validateform()){
					OwenrId = UpPasswordActivity.this.getIntent().getStringExtra("OwnerId");
					IdCardNo = UpPasswordActivity.this.getIntent().getStringExtra("IdCardNo");
					UserPassword = UserPasswd.getText().toString();
					NewUserPassword = NewUserPasswd.getText().toString();
					NewUserPassword2 = NewUserPasswd2.getText().toString();
					
					if(NewUserPassword.equals(NewUserPassword2)){
						//显示等待框
						mydialog.show();
						new Thread(thread).start();
					}else{
						Toast.makeText(UpPasswordActivity.this,"两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		backimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	public Boolean validateform(){
		
		form = new Form();
		/**
		 * 非空验证
		 */
		NotEmptyValidator notNull= new NotEmptyValidator(this);
		
		/*RegExpValidator IdCarIs = new RegExpValidator(this,R.string.validator_passwd);
		IdCarIs.setStrvalue(NewUserPasswd.getText().toString());*/
		
		Validate telValidate = new Validate(UserPasswd);
		telValidate.addValidator(notNull);//非空验证
		
		Validate telValidate2 = new Validate(NewUserPasswd);
		telValidate2.addValidator(notNull);//非空验证
		
		Validate telValidate3 = new Validate(NewUserPasswd2);
		telValidate3.addValidator(notNull);//非空验证
		//telValidate3.addValidator(IdCarIs);
		
		form.addValidates(telValidate);
		form.addValidates(telValidate2);
		form.addValidates(telValidate3);
		
		return form.validate();
	}
	
	 private Handler handler = new Handler(){
		 @Override
		 public void handleMessage(android.os.Message msg) {
			 if(mydialog != null){
				 mydialog.dismiss();
			 }
			 switch (msg.what) {
				case 0:
					Toast.makeText(getApplicationContext(),messge, Toast.LENGTH_SHORT).show();
					SharedPreferences sharedPreferences = getSharedPreferences("login_info",MODE_PRIVATE);
					sharedPreferences.edit().clear().commit();//清空用户缓存
					AlertDialog.Builder builder = new AlertDialog.Builder(
							UpPasswordActivity.this);

					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("修改成功，请从新登录！");
					builder.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Intent intent = new Intent(UpPasswordActivity.this,LoginActivity.class);
									startActivity(intent);
									finish();
								}
							});
					builder.create().show();
					break;
				case 1:
					Toast.makeText(getApplicationContext(),messge+",请检查原密码是否正确或网络是否打开", Toast.LENGTH_SHORT).show();
				break;
			}
		 };
	 };
	
	private Thread thread = new Thread(){
		@Override
		public void run() {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 Map<String,String> map = new HashMap<String,String>();
			 map.put("Ownerid", OwenrId);
			 map.put("IdCardNo", IdCardNo);
			 map.put("OldPw", UserPassword);
			 map.put("NewPw",NewUserPassword);
			 JSONObject jsonObject = new JSONObject(map);
			 String ret = HTTPUtil.PostStringToUrl("http://192.168.1.151:8118/HouseMobileApp/UpdateOwnerPasswd",jsonObject.toString());
			 JSONObject jsonObject2;
			try {
				jsonObject2 = new JSONObject(ret);
				messge = jsonObject2.getString("msg");
				String retmsg = jsonObject2.getString("ret");
				if(retmsg.equals("1")){
					handler.sendEmptyMessage(0);
				}else{
					handler.sendEmptyMessage(1);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			 
		};
	};
	
}
