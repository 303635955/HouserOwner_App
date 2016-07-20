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
import android.content.Intent;
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

import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.DoubleDatePickerDialog;
import com.yunguo.Util.FromUtil;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;
import com.yunguo.Util.OloneDatePikerDialog;
import com.yunguo.houserowner_app.R;

public class  AddRentCardActivity extends Activity{
	
	private EditText Renewal_card_text;
	private EditText Renewal_date_text;
	private EditText Renewal_begindate_text;
	
	private Button rewal_sure;
	private Button rewal_cancle;
	private Button getcardid;
	
	private MyDialogUtils mydialog;
	
	private String mesag  ="";
	private String enTime ="";
	private String userid ="";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.addrentcard);
		
		userid = getIntent().getStringExtra("UserId");
		
		initView();
		setOnClick();
	}
	
	public void initView(){
		Renewal_begindate_text = (EditText) findViewById(R.id.add_begindate_text);
		Renewal_date_text = (EditText) findViewById(R.id.add_date_text);
		Renewal_card_text = (EditText) findViewById(R.id.add_card_text);
		
		rewal_sure = (Button) findViewById(R.id.add_sure);
		rewal_cancle = (Button) findViewById(R.id.add_cancle);
		getcardid = (Button) findViewById(R.id.addgetdoorid);
		
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
					new DoubleDatePickerDialog(AddRentCardActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
								int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
								int endDayOfMonth) {
							String textstartString = String.format("%d-%d-%d", startYear,
									startMonthOfYear + 1, startDayOfMonth);
							Renewal_begindate_text.setText(textstartString);
							String textendString = String.format("%d-%d-%d", endYear,
									endMonthOfYear + 1, endDayOfMonth);
							Renewal_date_text.setText(textendString);
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
				}
			}
		});
		
		Renewal_begindate_text.setOnFocusChangeListener(new OnFocusChangeListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					new DoubleDatePickerDialog(AddRentCardActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
								int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
								int endDayOfMonth) {
							String textstartString = String.format("%d-%d-%d", startYear,
									startMonthOfYear + 1, startDayOfMonth);
							Renewal_begindate_text.setText(textstartString);
							String textendString = String.format("%d-%d-%d", endYear,
									endMonthOfYear + 1, endDayOfMonth);
							Renewal_date_text.setText(textendString);
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
				}
			}
		});
		
		rewal_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mydialog.show();
				if(FromUtil.date(Renewal_begindate_text.getText().toString(), Renewal_date_text.getText().toString())){
					new Thread(thread).start();
				}else {
					Toast.makeText(getApplicationContext(), "输入有误,请重新输入....", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		rewal_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		getcardid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//showtext.setVisibility(View.VISIBLE);
				Renewal_card_text.setText("");
				Renewal_card_text.setHint("获取中……");
				new Thread(thread2).start();//获取卡号
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
				mesag = "操作失败，请检查网络连接!";
				break;
			}
			Toast.makeText(AddRentCardActivity.this, mesag, Toast.LENGTH_SHORT).show();
		};
	};
	
	private Thread thread = new Thread( ){
		@Override
		public void run() {
			String url = "http://192.168.1.151:8118/HouseMobileApp/AddPersonCard";
			Map<String,String> map = new HashMap<String, String>();
			map.put("PersonId", userid);
			map.put("CardNo", Renewal_card_text.getText()+"");
			map.put("BeginTime", Renewal_begindate_text.getText()+"");
			map.put("EndTime", Renewal_date_text.getText()+"");
			JSONObject json = null;
			Message message = new Message();
			try {
				json = new JSONObject(map);
				String res = HTTPUtil.PostStringToUrl(url, json.toString());
				if(res.equals("") || res == null){
					message.what = 2;
					return;
				}
				JSONObject rejson = new JSONObject(res);
				String ret = rejson.get("ret")+"";
				if(ret.equals("1")){
					message.what = 0;
					message.obj = rejson.get("msg");
				}else if(ret.equals("-1")){
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
	
	
	private Thread thread2 = new Thread( ){
		@Override
		public void run() {
			PostCarNo();
		};
	};
	
	public void PostCarNo(){
		Message message = new Message();
		Map<String,String> map = new HashMap<String,String>();
		map.put("HouseId", SetUpRent.getSetUpRent().getHouseId());
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
