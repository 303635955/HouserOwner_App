package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpDatepasswordActivity extends Activity{
	
	private Button nexdbut;
	private EditText inputpw;
	
	private String Ownerid;
	private String IdCardNo;
	private String OldPw;
	private String NewPw;
	
	private MyDialogUtils mydialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatepassword_activity);
		
		Bundle bundle = getIntent().getBundleExtra("bundle");
		@SuppressWarnings("unchecked")
		Map<String,String> map = (Map<String,String>) bundle.get("userdate");
		Ownerid = map.get("Ownerid");
		IdCardNo = map.get("IdCardNo");
		OldPw = map.get("OldPw");
		
		initView();
		
		setOnClick();
	}
	
	private void initView(){
		nexdbut = (Button) findViewById(R.id.nexdbut);
		inputpw = (EditText) findViewById(R.id.inputpw);
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("请求中……");
	}
	
	private void setOnClick(){
		nexdbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NewPw = inputpw.getText()+"";
				mydialog.show();
				new Thread(thread).start();
			}
		});
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(mydialog != null){
				mydialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				Toast.makeText(UpDatepasswordActivity.this, msg.obj+"", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(UpDatepasswordActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			case 1:
				String messag = msg.obj+"";
				Toast.makeText(UpDatepasswordActivity.this, messag, Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(UpDatepasswordActivity.this,"请求失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
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
			String url = "http://192.168.1.151:8118/HouseMobileApp/UpdateOwnerPasswd";
			Map<String, String> map = new HashMap<String, String>();
			map.put("Ownerid",Ownerid);
			map.put("IdCardNo",IdCardNo);
			map.put("OldPw",OldPw);
			map.put("NewPw",NewPw);
			JSONObject js = new JSONObject(map);
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
					mesg.obj = jsonObject2.get("msg");
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
