package com.yunguo.houserowner_app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistRentPerson extends Activity {
	private EditText person_name;
	private EditText person_cardid;
	private Button btn, btn_back;
	private Spinner Sex, Nationnal;
	private EditText carddate, RentAdress, date, office;

	private Map<String, String> s = new HashMap<String, String>();
	String Sextext, nation;
	String[] mItems, mItems1;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_person);
		// 获得意图
		Intent intent = getIntent();
		// 得到数据集
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			s = (Map<String, String>) bundle.get("LoginedIpInfoS");
		} else {

		}
		init();
		// 设置值
		setText();
		// 添加点击事件
		setclick();
	}

	public void init() {
		person_name = (EditText) findViewById(R.id.person_name_text);
		person_cardid = (EditText) findViewById(R.id.person_cardid_text);
		office = (EditText) findViewById(R.id.office);
		carddate = (EditText) findViewById(R.id.carddate);
		date = (EditText) findViewById(R.id.date);
		RentAdress = (EditText) findViewById(R.id.RentAdress);

		btn = (Button) findViewById(R.id.regist_info_two);
		btn_back = (Button) findViewById(R.id.regist_info_one);

		// 初始化控件
		Sex = (Spinner) findViewById(R.id.Spinner_sex);
		// 建立数据源
		mItems = getResources().getStringArray(R.array.spinnersex);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> sex_Adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems);
		// 绑定 Adapter到控件
		Sex.setAdapter(sex_Adapter);

		// 初始化控件
		Nationnal = (Spinner) findViewById(R.id.Spinner_nationnal);
		// 建立数据源
		mItems1 = getResources().getStringArray(R.array.spinnernationnal);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> nation_Adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems1);
		// 绑定 Adapter到控件
		Nationnal.setAdapter(nation_Adapter1);
	}

	public void setclick() {
		
		Sex.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				// 获取选中值
				Spinner spinner = (Spinner) adapterView;
				Sextext = (String) spinner.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(), Sextext, 3000).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		Nationnal.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Spinner spinner = (Spinner) parent;
				nation = (String) spinner.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(), nation, 3000).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				s.put("Name", person_name.getText().toString());
				s.put("IdCardNo", person_cardid.getText().toString());
				s.put("IdCardAuthority", office.getText().toString());
				s.put("carddate", carddate.getText().toString());
				if (Sex != null) {
					s.put("Sex", Sextext);
				}
				if (nation != null) {
					s.put("Nation", nation);
				}
				// carddate, RentAdress, date, office
				s.put("date", date.getText().toString());
				s.put("IdCardLocation", RentAdress.getText().toString());
				Intent intent = new Intent(getApplicationContext(),
						RentOwnerActivity.class);
				Bundle extras = new Bundle();
				extras.putSerializable("LoginedIpInfoS", (Serializable) s);// /java.util.HashMap.
				intent.putExtras(extras);
				startActivity(intent);
			}
		});
		
		//返回按钮
		btn_back.setOnClickListener(mGoBack);
		
	}

	public void setText() {
		if (s != null) {
			person_name.setText(s.get("Name"));
			person_cardid.setText(s.get("IdCardNo"));
			office.setText(s.get("IdCardAuthority"));
			carddate.setText(s.get("date"));
			RentAdress.setText(s.get("IdCardLocation"));
			// 设置默认选中
			if (s.get("Sex") != null) {
				if (getselect(s.get("Sex"), mItems) != 200) {
					Sex.setSelection(getselect(s.get("Sex"), mItems), true);
				} else {

				}
			}
			if (s.get("Nation") != null) {
				if (getselect(s.get("Nation"), mItems1) != 200) {
					Nationnal.setSelection(getselect(s.get("Nation"), mItems1),
							true);
				} else {

				}
			}
		}
	}
	
	public OnClickListener mGoBack = new OnClickListener() {

		public void onClick(View v) {
			finish();
		}
	};
	
	public int getselect(String str, String[] data) {
		int i;
		for (i = 0; i < data.length; i++) {
			if (str == data[i]) {
				break;
			}
		}
		if (i == data.length) {
			return i = 200;
		}
		return i;
	}
}
