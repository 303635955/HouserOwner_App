package com.yunguo.houserowner_app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.yunguo.ImageLoderUtils.BPUtil;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpUserPasswordActivity extends Activity{
	private ImageView authcode;
	private Button backbut;
	private Button nexdbut;
	private EditText textcode;
	private BPUtil bPUtil;
	private EditText register_user;
	private Form form;
	private String IdCardNo;
	
	private MyDialogUtils mydialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.upuserpassword);
		initView();
		setOnClick();
	}
	
	public void initView(){
		
		bPUtil = BPUtil.getInstance();
		authcode = (ImageView) findViewById(R.id.authcode);
		authcode.setImageBitmap(BPUtil.getInstance().createBitmap());
		
		backbut = (Button) findViewById(R.id.backbut);
		nexdbut = (Button) findViewById(R.id.nexdbut);
		textcode = (EditText) findViewById(R.id.textcode);
		register_user = (EditText) findViewById(R.id.register_user);
		
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("请求中……");
	}
	

	/**
	 * 输入校验
	 */
	public boolean InputCheckout(){
		form = new Form();
		
		NotEmptyValidator notNull= new NotEmptyValidator(this);	//非空验证
		
		Validate telValidate1 = new Validate(register_user);
		telValidate1.addValidator(notNull);//非空验证
		
		Validate telValidate2 = new Validate(textcode);
		telValidate2.addValidator(notNull);//非空验证
		
		form.addValidates(telValidate1);
		form.addValidates(telValidate2);
		
		return form.validate();
	}
	
	public void setOnClick(){
		authcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//刷新验证码
				authcode.setImageBitmap(bPUtil.createBitmap());
			}
		});
		
		nexdbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(InputCheckout()){
					if(bPUtil.getCode().equalsIgnoreCase(textcode.getText()+"")){
						IdCardNo = register_user.getText()+"";
						mydialog.show();
						new Thread(thread).start();
					}else{
						//刷新验证码
						authcode.setImageBitmap(bPUtil.createBitmap());
						Toast.makeText(UpUserPasswordActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		backbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private Handler handler = new Handler(){
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			
			if(mydialog != null){
				mydialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				Intent intent = new Intent(UpUserPasswordActivity.this,MSNActivity.class);
				@SuppressWarnings("unchecked")
				Map<String,String> map = (Map<String,String>) msg.obj;
				Bundle bundle = new Bundle();
				bundle.putSerializable("userdate", (Serializable)map);
				intent.putExtra("bundle", bundle);
				startActivity(intent);
				finish();
				break;
			case 1:
				Toast.makeText(UpUserPasswordActivity.this, msg.obj+"", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(UpUserPasswordActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
				finish();
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
			String url = "http://192.168.1.151:8118/HouseMobileApp/FindPw";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("IdCardNo",IdCardNo);
			JSONObject js = new JSONObject(houseid);
			String str = js.toString();
			String res = HTTPUtil.PostStringToUrl(url, str);
			
			Message mesg = new Message();
			if(res.equals("") || res == null){
				handler.sendEmptyMessage(2);
				return;
			}
			JSONObject jsonObject2 = null;
			try {
				jsonObject2 = new JSONObject(res);
				String ret = jsonObject2.get("ret")+"";
				if(ret.equals("1")){
					Map<String,String> map = new HashMap<String,String>();
					map.put("IdCardNo",IdCardNo);
					map.put("Ownerid",jsonObject2.getString("ownerid"));
					map.put("OldPw",jsonObject2.getString("msg"));
					mesg.obj = map;
					mesg.what = 0;
				}else{
					mesg.obj = jsonObject2.get("msg");
					mesg.what = 1;
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(2);
			}
			handler.handleMessage(mesg);
		};
	};
	
}
