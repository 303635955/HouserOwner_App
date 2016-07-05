package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.HTTPUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class RentOwnerOtherActivity extends Activity {
	private Map<String, String> Other = new HashMap<String, String>();

	private Spinner Focus_attention, Spinner_birth, Spinner_contraception,
			Spinner_plan, Spinner_children, Spinner_disability;
	private EditText atttion, plan_number;
	private Button btn;
	String attention, birth, plan, contraception, childrenold, disability;
	private HTTPUtil httpsigin;
	String massage;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personinfo_other_view);

		findview();

		Setlisten();
	}

	public void findview() {
		atttion = (EditText) findViewById(R.id.warning);
		plan_number = (EditText) findViewById(R.id.Spinner_plan_number);
		btn = (Button) findViewById(R.id.regist_info_one);

		// 初始化控件
		Focus_attention = (Spinner) findViewById(R.id.Spinner_Focus_attention);
		// 建立数据源
		String[] mItems2 = getResources().getStringArray(R.array.spinnerbecareful);
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
						contraception = (String) spinner
								.getItemAtPosition(position);
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
						childrenold = (String) spinner
								.getItemAtPosition(position);
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
						disability = (String) spinner
								.getItemAtPosition(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				attion(attention);
				brith(birth);
				Contraception(contraception);
				PlannedBirthType(plan);
				SmallChild(childrenold);
				Disability(disability);
				Other.put("PlannedBirthCardNo", atttion.getText().toString());
				Other.put("NoSwipeAlarmInterval ", plan_number.getText().toString());
				SetUpRent.getSetUpRent().setAttionmap(Other);
				progressDialog = ProgressDialog.show(RentOwnerOtherActivity.this, "请稍等", "提交中...", true);
				new Thread(thrad).start();
			}
		});
	}
	

	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(progressDialog != null){
				progressDialog.dismiss();
			}
			switch (msg.what) {
				case 0:
					Toast.makeText(getApplicationContext(), massage, Toast.LENGTH_SHORT).show();
				break;
				case 1:
					Toast.makeText(getApplicationContext(),massage, Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(RentOwnerOtherActivity.this,MainActivity.class);
					startActivity(intent);
					break;
			}
		};
	};
	
	private Thread thrad = new Thread(){
		@Override
		public void run() {
			httpsigin = new HTTPUtil();
			Map<String,String> postmap = new HashMap<String,String>();
			postmap.putAll(SetUpRent.getSetUpRent().getRentmap());
			postmap.putAll(SetUpRent.getSetUpRent().getHousemap());
			postmap.putAll(SetUpRent.getSetUpRent().getAttionmap());
			postmap.put("HouseId",SetUpRent.getSetUpRent().getHouseId());
			JSONObject js= new JSONObject(postmap);
			String postdate = js.toString();
			//String ret = httpsigin.PostStringToUrl("http://120.25.65.125:8118/HouseMobileApp/addPerson2",js.toString());
			String ret = httpsigin.PostStringToUrl("http://192.168.1.147:8118/HouseMobileApp/addPerson2",js.toString());
			JSONObject jsonObject2 = null;
			try {
				jsonObject2 = new JSONObject(ret);
				String s=jsonObject2.get("ret")+"";
				massage = jsonObject2.get("msg") +"";
				if(s.equals("1")){
					handler.sendEmptyMessage(1);
				}else {
					handler.sendEmptyMessage(0);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				handler.sendEmptyMessage(0);
			}
		};
	};
	//	设置传入map的值
	public void attion(String attion) {
		if (attion.equals("不关注")) {
			Other.put("Level", "0");
		} else if (attion.equals("重点人员")) {
			Other.put("Level", "1");
		} else if (attion.equals("孤寡老人")) {
			Other.put("Level", "2");
		}
	}

	public void Disability(String attion) {
		if (attion.equals("无")) {
			Other.put("Disability", "0");
		} else if (attion.equals("有")) {
			Other.put("Disability", "1");
		}
	}

	public void Contraception(String attion) {
		if (attion.equals("无")) {
			Other.put("Contraception ", "0");
		} else if (attion.equals("结扎")) {
			Other.put("Contraception ", "1");
		}
	}

	public void PlannedBirthType(String attion) {
		if (attion.equals("无")) {
			Other.put("PlannedBirthType", "0");
		} else if (attion.equals("国家证")) {
			Other.put("PlannedBirthType", "1");
		} else if (attion.equals("地区证")) {
			Other.put("PlannedBirthType", "2");
		}
	}

	public void SmallChild(String attion) {
		if (attion.equals("无")) {
			Other.put("SmallChild", "0");
		} else if (attion.equals("有")) {
			Other.put("SmallChild", "1");
		}
	}

	public void brith(String brith) {
		if (brith.equals("无孩")) {
			Other.put("Children", "0");
		} else if (brith.equals("一孩")) {
			Other.put("Children", "1");
		} else if (brith.equals("二孩")) {
			Other.put("Children", "2");
		} else if (brith.equals("多孩")) {
			Other.put("Children", "3");
		}
	}
}
