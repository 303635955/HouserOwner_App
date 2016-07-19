package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.HTTPUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UpTelActivity extends Activity{
	private Button postbut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uptel_activity);
	}
	
	public void initView(){
		postbut = (Button) findViewById(R.id.postbut);
	}
	
	public void setOnClick(){
		postbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			
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
			houseid.put("Ownerid", SetUpRent.getSetUpRent().getOwnerId());
			houseid.put("Ownerid", SetUpRent.getSetUpRent().getOwnerId());
			houseid.put("Ownerid", SetUpRent.getSetUpRent().getOwnerId());
			houseid.put("Ownerid", SetUpRent.getSetUpRent().getOwnerId());
			JSONObject js = new JSONObject(houseid);
			String str = js.toString();
			String res = HTTPUtil.PostStringToUrl(url, str);
			if(res.equals("") || res == null){
				handler.sendEmptyMessage(2);
				return;
			}
			JSONObject jsonObject2 = null;
			try {
				jsonObject2 = new JSONObject(res);
				String ret = jsonObject2.get("ret")+"";
				if(ret.equals("1")){
					Message mes = new Message();
					mes.what = 0;
					handler.sendMessage(mes);
				}else{
					handler.sendEmptyMessage(1);
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(2);
			}
			
		};
	};
}
