package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class RentOwnerOtherActivity extends Activity {
	private Map<String, String> s = new HashMap<String, String>();

	private Spinner Focus_attention, Spinner_birth, Spinner_contraception,
			Spinner_plan, Spinner_children, Spinner_disability;
	private EditText atttion,plan_number;
	String attention,birth, plan,contraception, childrenold, disability;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personinfo_other_view);
		// 获得意图
		Intent intent = getIntent();
		// 得到数据集
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			s = (Map<String, String>) bundle.get("LoginedIpInfoS");
		} else {

		}

		findview();
	}

	public void findview() {
		atttion = (EditText) findViewById(R.id.warning);
		plan_number = (EditText) findViewById(R.id.Spinner_plan_number);

		// 初始化控件
		Focus_attention = (Spinner) findViewById(R.id.Spinner_Focus_attention);
		// 建立数据源
		String[] mItems2 = getResources().getStringArray(R.array.spinnerchild);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> Focus_attention_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems2);
		// 绑定 Adapter到控件
		Focus_attention.setAdapter(Focus_attention_Adapter);

		// 初始化控件
		Spinner_birth = (Spinner) findViewById(R.id.Spinner_birth);
		// 建立数据源
		String[] mItems3 = getResources().getStringArray(R.array.spinnerchild);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> Spinner_birth_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems3);
		// 绑定 Adapter到控件
		Spinner_birth.setAdapter(Spinner_birth_Adapter);

		// 初始化控件
		Spinner_contraception = (Spinner) findViewById(R.id.Spinner_contraception);
		// 建立数据源
		String[] mItems4 = getResources().getStringArray(R.array.spinnermove);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> Spinner_contraception_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems4);
		// 绑定 Adapter到控件
		Spinner_contraception.setAdapter(Spinner_contraception_Adapter);

		// 初始化控件
		Spinner_plan = (Spinner) findViewById(R.id.Spinner_plan);
		// 建立数据源
		String[] mItems5 = getResources().getStringArray(
				R.array.spinnerchildcard);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> Spinner_plan_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems5);
		// 绑定 Adapter到控件
		Spinner_plan.setAdapter(Spinner_plan_Adapter);

		// 初始化控件
		Spinner_children = (Spinner) findViewById(R.id.Spinner_children);
		// 建立数据源
		String[] mItems6 = getResources().getStringArray(
				R.array.spinnerchildold);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> Spinner_children_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems6);
		// 绑定 Adapter到控件
		Spinner_children.setAdapter(Spinner_children_Adapter);

		// 初始化控件
		Spinner_disability = (Spinner) findViewById(R.id.Spinner_disability);
		// 建立数据源
		String[] mItems7 = getResources().getStringArray(
				R.array.spinnerdisability);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> Spinner_disability_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems7);
		// 绑定 Adapter到控件
		Spinner_disability.setAdapter(Spinner_disability_Adapter);
	}

	public void Setlisten() {
		Focus_attention.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Spinner spinner = (Spinner) parent;
				attention = (String) spinner.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(), attention, 3000).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		// ,Spinner_birth,Spinner_contraception,Spinner_plan,Spinner_children,Spinner_disability
		Spinner_birth.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				// 获取选中值
				Spinner spinner = (Spinner) adapterView;
				birth = (String) spinner.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(), birth, 3000).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		Spinner_contraception
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Spinner spinner = (Spinner) parent;
						contraception = (String) spinner.getItemAtPosition(position);
						Toast.makeText(getApplicationContext(), contraception, 3000)
								.show();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		Spinner_plan.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinner = (Spinner) parent;
				plan = (String) spinner.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(), plan, 3000).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		Spinner_children
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Spinner spinner = (Spinner) parent;
						childrenold = (String) spinner.getItemAtPosition(position);
						Toast.makeText(getApplicationContext(), childrenold, 3000)
								.show();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		Spinner_disability
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Spinner spinner = (Spinner) parent;
						disability = (String) spinner.getItemAtPosition(position);
						Toast.makeText(getApplicationContext(), disability, 3000)
								.show();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
	}
	
	
}
