package com.yunguo.fragment;

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

import com.IDCardReader.utility.OloneDatePikerDialog;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.houserowner_app.CardMessageActivity;
import com.yunguo.houserowner_app.R;

public class  RenewalActivity extends Activity{
	private EditText Renewal_date;
	private Spinner Renewal_name;
	private String name;
	private Button sure_btn,cancle_btn;
	private String UserId = "";
	private String map;
	private String[] mItems;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.renewal);
		
		/**
		 * 获取数据
		 */
		UserId = getIntent().getStringExtra("UserId");
		
		new Thread(thread2).start();
		//initView();
		//setclick();
	}
	
	public void initView(){
		Renewal_date =(EditText) findViewById(R.id.Renewal_date_text);
		
		// 初始化控件
		Renewal_name =(Spinner) findViewById(R.id.Renewal_card_text);
		// 建立数据源
		mItems = getResources().getStringArray(R.array.spinnernationnal);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> nation_Adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems);
		// 绑定 Adapter到控件
		Renewal_name.setAdapter(nation_Adapter1);
		
		sure_btn= (Button) findViewById(R.id.rewal_sure);
		cancle_btn= (Button) findViewById(R.id.rewal_cancle);
	}
	
	
	public void setclick(){
		Renewal_name.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Spinner spinner = (Spinner) parent;
				Renewal_name = (Spinner) spinner.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		
		Renewal_date.setOnFocusChangeListener(new OnFocusChangeListener() {
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
									Renewal_date.setText(textstartString);
								}
							},c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
				}
			}
		});
		
		sure_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		cancle_btn.setOnClickListener(new OnClickListener() {
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
			switch (msg.what) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			}
			/**
			 * 停止刷新
			 */
			Toast.makeText(RenewalActivity.this, msg+"", Toast.LENGTH_SHORT).show();
		};
	};
	
	private Thread thread = new Thread( ){
		@Override
		public void run() {
			String url = "http://120.25.65.125:8118/HouseMobileApp/IcCardxuqi";
			JSONObject json;
			try {
				json = new JSONObject(map);
				String res = HTTPUtil.PostStringToUrl(url, json.toString());
				JSONObject rejson = new JSONObject(res);
				String back = rejson.get("ret")+"";
				if(back.equals("")){
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	
	private Handler handler2 = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				List<Map<String,String>> list = (List<Map<String,String>>)msg.obj;
				mItems =  new String[list.size()];
				for(int i =0 ;i<list.size();i++){
					String no = list.get(i).get("CardNo");
					mItems[i] = no;
				}
				Toast.makeText(getApplicationContext(), mItems+"", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "您还没有门卡", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "网络连接失败",  Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	
	private Thread thread2 = new Thread(){
		@Override
		public void run() {
			String url = "http://120.25.65.125:8118/HouseMobileApp/GetCardlistByTenaId";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("TenaId", UserId);
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
					List<Map<String, String>> tmpdata = getHouseInfo(res);
					if(tmpdata == null || tmpdata.equals("")){
						handler2.sendEmptyMessage(1);
						return;
					}else{
						mes.obj = tmpdata;
					}
					handler2.sendMessage(mes);
				}else{
					handler2.sendEmptyMessage(1);
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler2.sendEmptyMessage(2);
			}
		};
	};
	
	public List<Map<String, String>> getHouseInfo(String res) {
		 List<Map<String, String>> tmpdata = new ArrayList<Map<String, String>>();
			try {
				JSONObject jsonObject2 = new JSONObject(res);
				JSONArray jsonArray = jsonObject2.getJSONArray("houses");
				for (int i = 0; i < jsonArray.length(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
					map.put("CardNo", jsonObjectSon.getString("CardNo"));
					tmpdata.add(map);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
			return tmpdata;
		}
	
	
}
