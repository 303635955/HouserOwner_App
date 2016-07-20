package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;

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
import android.widget.TextView;
import android.widget.Toast;

public class UpTelActivity extends Activity{
	private Button postbut;
	private Button backbut;
	private Intent intent;
	
	private TextView oldTelNo;
	private EditText newTelNo;
	
	private MyDialogUtils mydialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.uptel_activity);
		
		intent = getIntent();
		initView();
		setOnClick();
	}
	
	public void initView(){
		postbut = (Button) findViewById(R.id.postbut);
		backbut = (Button) findViewById(R.id.backbut);
		oldTelNo = (TextView) findViewById(R.id.oldTelNo);
		oldTelNo.setText(intent.getStringExtra("TelNo"));
		newTelNo = (EditText) findViewById(R.id.newTelNo);
		
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("请求中……");
	}
	
	public void setOnClick(){
		postbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mydialog.show();
				new Thread(thread).start();
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
		@Override
		public void handleMessage(Message msg) {
			if(mydialog != null){
				mydialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj+"", Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), msg.obj+"", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "提交失败，请检查网络！", Toast.LENGTH_SHORT).show();
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
				e1.printStackTrace();
			}
			String url = "http://192.168.1.151:8118/HouseMobileApp/UpdateTelno";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("Ownerid", intent.getStringExtra("OwnerId"));
			houseid.put("IdCardNo", intent.getStringExtra("IdCardNo"));
			houseid.put("TelNo", newTelNo.getText()+"");
			JSONObject js = new JSONObject(houseid);
			String str = js.toString();
			String res = HTTPUtil.PostStringToUrl(url, str);
			if(res.equals("") || res == null){
				handler.sendEmptyMessage(2);
				return;
			}
			JSONObject jsonObject2 = null;
			Message mes = new Message();
			try {
				jsonObject2 = new JSONObject(res);
				String ret = jsonObject2.get("ret")+"";
				if(ret.equals("1")){
					mes.what = 0;
					mes.obj = jsonObject2.get("msg");
				}else{
					mes.what = 1;
					mes.obj = jsonObject2.get("msg");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(2);
			}
			handler.sendMessage(mes);
			
		};
	};
}
