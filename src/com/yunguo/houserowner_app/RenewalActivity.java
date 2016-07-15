package com.yunguo.houserowner_app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;
import com.yunguo.Util.OloneDatePikerDialog;
import com.yunguo.houserowner_app.R;

public class  RenewalActivity extends Activity{
	
	private EditText Renewal_card_text;
	
	private EditText Renewal_date_text;
	
	private Button rewal_sure;
	
	private Button rewal_cancle;
	
	private MyDialogUtils mydialog;
	
	private String mesag = "";
	
	private String enTime = "";
	
	int YEAR = 0;
	int MONTH = 0;
	int DATE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.renewal);
		
		enTime = getIntent().getStringExtra("enTime");
		String[] tem = enTime.split("/");
		YEAR = Integer.parseInt(tem[0]);
		MONTH = Integer.parseInt(tem[1]);
		DATE = Integer.parseInt(tem[2].split(" ")[0]);
		
		initView();
		setOnClick();
	}
	
	public void initView(){
		Renewal_date_text = (EditText) findViewById(R.id.Renewal_date_text);
		Renewal_card_text = (EditText) findViewById(R.id.Renewal_card_text);
		Renewal_card_text.setText(RenewalActivity.this.getIntent().getStringExtra("CardNo"));
		Renewal_date_text.setText(enTime);
		
		rewal_sure = (Button) findViewById(R.id.rewal_sure);
		rewal_cancle = (Button) findViewById(R.id.rewal_cancle);
		
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("请求中……");
	}
	
	
	public void setOnClick(){
		Renewal_date_text.setOnFocusChangeListener(new OnFocusChangeListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
				new OloneDatePikerDialog(RenewalActivity.this, 0,
						new OloneDatePikerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker startDatePicker, int startYear,
									int startMonthOfYear, int startDayOfMonth) {
								String textstartString = String.format(
										"%d-%d-%d", startYear,
										startMonthOfYear + 1,
										startDayOfMonth);
								Renewal_date_text.setText(textstartString);
							}
					},YEAR, MONTH-1, DATE, false).show();
				}
			}
		});
		
		rewal_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mydialog.show();
				new Thread(thread).start();
			}
		});
		
		rewal_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	/**
	 * 刷新界面
	 */
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if(mydialog != null){
				mydialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				mesag = (String) msg.obj;
				finish();
				break;
			case 1:
				mesag = (String) msg.obj;
				break;
			case 2:
				mesag = "操作失败，卿检查网络连接!";
				break;
			}
			Toast.makeText(RenewalActivity.this, mesag, Toast.LENGTH_SHORT).show();
		};
	};
	
	private Thread thread = new Thread( ){
		@Override
		public void run() {
			String url = "http://120.25.65.125:8118/HouseMobileApp/IcCardxuqi";
			Map<String,String> map = new HashMap<String, String>();
			map.put("CardId", Renewal_card_text.getText()+"");
			map.put("endTime", Renewal_date_text.getText()+"");
			JSONObject json = null;
			Message message = new Message();
			try {
				json = new JSONObject(map);
				String res = HTTPUtil.PostStringToUrl(url, json.toString());
				JSONObject rejson = new JSONObject(res);
				String ret = rejson.get("ret")+"";
				if(ret.equals("1")){
					message.what = 0;
					message.obj = rejson.get("msg");
				}else if(ret.equals("0")){
					message.what = 1;
					message.obj = rejson.get("msg");
				}else{
					message.what = 2;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				message.what = 2;
			}
			
			handler.sendMessage(message);
		};
	};
	
}
