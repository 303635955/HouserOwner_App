package com.yunguo.houserowner_app;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.IDCardReader.utility.DoubleDatePickerDialog;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.RegExpValidator;
import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.HTTPUtil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class RentOwnerActivity extends Activity {
	private Map<String, String> house = new HashMap<String, String>();
	
	private EditText person_housename;
	private EditText person_houseid;
	private EditText person_housecardid;
	private EditText person_begintime;
	private EditText person_endtime;
	private EditText person_call;

	private Button btn,btn_back;
	
	private ProgressDialog progressDialog;

	String Sextext, nation;
	String[] mItems,mItems1; 
	
	
	
	private Form form;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personinfo_view);

		findview();
		//添加点击事件
		setclick();
		//new Thread(thread).start();
	}

	public void findview() {
		person_housename = (EditText) findViewById(R.id.person_housename_text);
		person_houseid = (EditText) findViewById(R.id.person_houseid_text);
		person_housecardid = (EditText) findViewById(R.id.person_housedoorid_text);
		person_begintime = (EditText) findViewById(R.id.person_begintime_text);
		person_endtime = (EditText) findViewById(R.id.person_endtime_text);
		person_call = (EditText) findViewById(R.id.call);
		
		btn_back = (Button) findViewById(R.id.house_info_one);
		btn = (Button) findViewById(R.id.house_info_two);
	}
	
	

	/**
	 * 输入校验
	 */
	public void InputCheckout(){
		form = new Form();
		
		NotEmptyValidator notNull= new NotEmptyValidator(this);	//非空验证
		RegExpValidator IdCarIs = new RegExpValidator(this);
		IdCarIs.setPattern("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");	//身份证验证
		
		Validate telValidate1 = new Validate(person_housename);
		telValidate1.addValidator(notNull);//非空验证
		
		Validate telValidate2 = new Validate(person_houseid);
		telValidate2.addValidator(notNull);//非空验证
		
		Validate telValidate3 = new Validate(person_housecardid);
		telValidate3.addValidator(notNull);//非空验证
		
		Validate telValidate4 = new Validate(person_begintime);
		telValidate4.addValidator(notNull);//非空验证
		
		Validate telValidate5 = new Validate(person_endtime);
		telValidate5.addValidator(notNull);//非空验证
		
		form.addValidates(telValidate1);
		form.addValidates(telValidate2);
		form.addValidates(telValidate3);
		form.addValidates(telValidate4);
		form.addValidates(telValidate5);
	}

	public void setclick() {
		person_begintime.setOnFocusChangeListener(new OnFocusChangeListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "此处被点击",Toast.LENGTH_SHORT).show();
				if(hasFocus){
					// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
					new DoubleDatePickerDialog(RentOwnerActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
								int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
								int endDayOfMonth) {
							String textstartString = String.format("%d-%d-%d", startYear,
									startMonthOfYear + 1, startDayOfMonth);
							person_begintime.setText(textstartString);
							String textendString = String.format("%d-%d-%d", endYear,
									endMonthOfYear + 1, endDayOfMonth);
							person_endtime.setText(textendString);
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
				}
			}
		});
		
		person_endtime.setOnFocusChangeListener(new OnFocusChangeListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "此处被点击",Toast.LENGTH_SHORT).show();
				if(hasFocus){
					// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
					new DoubleDatePickerDialog(RentOwnerActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
								int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
								int endDayOfMonth) {
							String textstartString = String.format("%d-%d-%d", startYear,
									startMonthOfYear + 1, startDayOfMonth);
							person_begintime.setText(textstartString);
							String textendString = String.format("%d-%d-%d", endYear,
									endMonthOfYear + 1, endDayOfMonth);
							person_endtime.setText(textendString);
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
				}
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InputCheckout();
				if(form.validate()){
					house.put("HouseName", person_housename.getText().toString());
					house.put("RoomNo", person_houseid.getText().toString());
					house.put("CardNoStr", person_housecardid.getText().toString());
					house.put("beginTime", person_begintime.getText().toString());
					house.put("endTime", person_endtime.getText().toString());
					house.put("TelNo", person_call.getText().toString());
					SetUpRent.getSetUpRent().setHousemap(house);
					Intent intent = new Intent(getApplicationContext(),
							RentOwnerOtherActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(), "输入有误额", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn_back.setOnClickListener(new OnClickListener() {
			
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
				person_housecardid.setText("");
				Toast.makeText(getApplicationContext(), "获取成功", Toast.LENGTH_SHORT).show();
				break;
			}
			
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
		map.put("HouseId", SetUpRent.getSetUpRent().getHouseId());
		try {
			JSONObject js = new JSONObject(map.toString());
			String res = HTTPUtil.PostStringToUrl("http://120.25.65.125:8118/HouseMobileApp/GetHouseLastSwipeCardNo", js.toString());
			JSONObject JsonObject = new JSONObject(res);
			String ret = JsonObject.get("ret")+"";
			if(ret.equals("0") || ret.equals("") || ret == null){
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
