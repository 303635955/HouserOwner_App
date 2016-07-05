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

		// ��ʼ���ؼ�
		Focus_attention = (Spinner) findViewById(R.id.Spinner_Focus_attention);
		// ��������Դ
		String[] mItems2 = getResources().getStringArray(R.array.spinnerbecareful);
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> Focus_attention_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems2);
		// �� Adapter���ؼ�
		Focus_attention.setAdapter(Focus_attention_Adapter);

		// ��ʼ���ؼ�
		Spinner_birth = (Spinner) findViewById(R.id.Spinner_birth);
		// ��������Դ
		String[] mItems3 = getResources().getStringArray(R.array.spinnerchild);
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> Spinner_birth_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems3);
		// �� Adapter���ؼ�
		Spinner_birth.setAdapter(Spinner_birth_Adapter);

		// ��ʼ���ؼ�
		Spinner_contraception = (Spinner) findViewById(R.id.Spinner_contraception);
		// ��������Դ
		String[] mItems4 = getResources().getStringArray(R.array.spinnermove);
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> Spinner_contraception_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems4);
		// �� Adapter���ؼ�
		Spinner_contraception.setAdapter(Spinner_contraception_Adapter);

		// ��ʼ���ؼ�
		Spinner_plan = (Spinner) findViewById(R.id.Spinner_plan);
		// ��������Դ
		String[] mItems5 = getResources().getStringArray(
				R.array.spinnerchildcard);
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> Spinner_plan_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems5);
		// �� Adapter���ؼ�
		Spinner_plan.setAdapter(Spinner_plan_Adapter);

		// ��ʼ���ؼ�
		Spinner_children = (Spinner) findViewById(R.id.Spinner_children);
		// ��������Դ
		String[] mItems6 = getResources().getStringArray(
				R.array.spinnerchildold);
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> Spinner_children_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems6);
		// �� Adapter���ؼ�
		Spinner_children.setAdapter(Spinner_children_Adapter);

		// ��ʼ���ؼ�
		Spinner_disability = (Spinner) findViewById(R.id.Spinner_disability);
		// ��������Դ
		String[] mItems7 = getResources().getStringArray(
				R.array.spinnerdisability);
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> Spinner_disability_Adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, mItems7);
		// �� Adapter���ؼ�
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
				// ��ȡѡ��ֵ
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
				progressDialog = ProgressDialog.show(RentOwnerOtherActivity.this, "���Ե�", "�ύ��...", true);
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
	//	���ô���map��ֵ
	public void attion(String attion) {
		if (attion.equals("����ע")) {
			Other.put("Level", "0");
		} else if (attion.equals("�ص���Ա")) {
			Other.put("Level", "1");
		} else if (attion.equals("�¹�����")) {
			Other.put("Level", "2");
		}
	}

	public void Disability(String attion) {
		if (attion.equals("��")) {
			Other.put("Disability", "0");
		} else if (attion.equals("��")) {
			Other.put("Disability", "1");
		}
	}

	public void Contraception(String attion) {
		if (attion.equals("��")) {
			Other.put("Contraception ", "0");
		} else if (attion.equals("����")) {
			Other.put("Contraception ", "1");
		}
	}

	public void PlannedBirthType(String attion) {
		if (attion.equals("��")) {
			Other.put("PlannedBirthType", "0");
		} else if (attion.equals("����֤")) {
			Other.put("PlannedBirthType", "1");
		} else if (attion.equals("����֤")) {
			Other.put("PlannedBirthType", "2");
		}
	}

	public void SmallChild(String attion) {
		if (attion.equals("��")) {
			Other.put("SmallChild", "0");
		} else if (attion.equals("��")) {
			Other.put("SmallChild", "1");
		}
	}

	public void brith(String brith) {
		if (brith.equals("�޺�")) {
			Other.put("Children", "0");
		} else if (brith.equals("һ��")) {
			Other.put("Children", "1");
		} else if (brith.equals("����")) {
			Other.put("Children", "2");
		} else if (brith.equals("�ຢ")) {
			Other.put("Children", "3");
		}
	}
}
