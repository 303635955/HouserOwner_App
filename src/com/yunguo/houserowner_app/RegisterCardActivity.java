package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterCardActivity extends Activity{
	
	private Button putcarid;
	private String CardNo;
	private TextView oldCarId;
	private EditText newCardId;
	private Button postbut;
	private Button backbut;
	private Button getcard;
	private MyDialogUtils mydialog;
	
	private String UserId;
	private String HouseId = "";
	
	private String msag = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registercard_activity);
		 CardNo = getIntent().getStringExtra("CardNo");
		 UserId = getIntent().getStringExtra("UserId");
		 HouseId = getIntent().getStringExtra("HouseId");
		 findView();
		 setOnClick();
	}
	
	private void findView(){
		putcarid = (Button) findViewById(R.id.putcarid);
		oldCarId = (TextView) findViewById(R.id.oldCarId);
		oldCarId.setText(CardNo);
		
		newCardId = (EditText) findViewById(R.id.newCardId);
		postbut = (Button) findViewById(R.id.postbut);
		backbut = (Button) findViewById(R.id.backbut);
		getcard = (Button) findViewById(R.id.getcard);
		
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("请求中……");
	}
	
	private void setOnClick(){
		getcard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				newCardId.setHint("获取中……");
				new Thread(thread).start();
			}
		});
		
		putcarid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		postbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mydialog.show();
				new Thread(thread2).start();
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
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				newCardId.setText((String)msg.obj);
				Toast.makeText(getApplicationContext(), "获取成功", Toast.LENGTH_SHORT).show();
				break;
			}
			
		};
	};
	
	
	private Handler handler2 = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			if(mydialog != null){
				mydialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				msag = (String) msg.obj;
				break;
			case 1:
				msag = (String) msg.obj;
				newCardId.setText((String)msg.obj);
				finish();
				break;
			case 2:
				msag = "操作失败，卿检查网络！";
				break;
				}
			Toast.makeText(getApplicationContext(), msag, Toast.LENGTH_SHORT).show();
		};
	};
	
	
	
	private Thread thread2 = new Thread(){
		@Override
		public void run() {
			Message message = new Message();
			Map<String,String> map = new HashMap<String,String>();
			map.put("tenantId",UserId);
			map.put("oldCardId",oldCarId.getText()+"");
			map.put("newCardId",newCardId.getText()+"");
			try {
				JSONObject js = new JSONObject(map.toString());
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String res = HTTPUtil.PostStringToUrl("http://120.25.65.125:8118/HouseMobileApp/IcCardBuban", js.toString());
				JSONObject JsonObject = new JSONObject(res);
				String ret = JsonObject.get("ret")+"";
				if(ret.equals("0")){
					message.what = 0;
					message.obj = JsonObject.get("msg");
					
				}else if(ret.equals("1")){
					message.what = 1;
					message.obj = JsonObject.get("msg");;
				}else{
					message.what = 2;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				message.what = 2;
			}
			handler2.sendMessage(message);
		};
	};
	
	
	
	private Thread thread = new Thread(){
		@Override
		public void run() {
			PostCarNo();
		};
	};
	
	
	public void PostCarNo(){
		Message message = new Message();
		Map<String,String> map = new HashMap<String,String>();
		map.put("HouseId",HouseId);
		try {
			JSONObject js = new JSONObject(map.toString());
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String res = HTTPUtil.PostStringToUrl("http://120.25.65.125:8118/HouseMobileApp/GetHouseLastSwipeCardNo", js.toString());
			JSONObject JsonObject = new JSONObject(res);
			String ret = JsonObject.get("ret")+"";
			if(ret.equals("0") || ret.equals("") || ret == null){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				PostCarNo();
			}else if(ret.equals("1")){
				String CarNo = JsonObject.getString("CardNo");
				message.what = 1;
				message.obj = CarNo;
				handler.sendMessage(message);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			PostCarNo();
		}
	}
}
