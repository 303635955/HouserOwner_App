package com.yunguo.houserowner_app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.RegExpValidator;
import com.yunguo.Bean.SetUpRent;

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

	private Map<String, String> s=null;
	String Sextext = "男", nation = "汉";
	String[] mItems, mItems1;
	
	
	private Form form; 	//输入校验

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
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				s = new HashMap<String, String>();
				String str = person_name.getText().toString();
				s.put("Name", person_name.getText().toString());
				s.put("IdCardNo", person_cardid.getText().toString());
				s.put("IdCardAuthority", office.getText().toString());
				s.put("Gender", Sextext);
				s.put("Nation", nation);
				// carddate, RentAdress, date, office
				s.put("IdCardLocation", RentAdress.getText().toString());
				s.put("IdCardBeginTime", carddate.getText().toString());
				s.put("IdCardEndTime", date.getText().toString());
				setsex(Sextext);
				setnation(nation);
				SetUpRent.getSetUpRent().setRentmap(s);
				Intent intent = new Intent(getApplicationContext(),
						RentOwnerActivity.class);
				startActivity(intent);
			}
		});
		
		//返回按钮
		btn_back.setOnClickListener(mGoBack);
		
	}
	
	/**
	 * 输入校验
	 */
	public void InputCheckout(){
		form = new Form();
		
		NotEmptyValidator notNull= new NotEmptyValidator(this);	//非空验证
		RegExpValidator IdCarIs = new RegExpValidator(this);
		IdCarIs.setPattern("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");	//身份证验证
		
		Validate telValidate1 = new Validate(person_name);
		telValidate1.addValidator(notNull);//非空验证
		
		Validate telValidate2 = new Validate(person_cardid);
		telValidate2.addValidator(notNull);//非空验证
		telValidate2.addValidator(IdCarIs);//身份证验证
		
		form.addValidates(telValidate1);
		form.addValidates(telValidate2);
	}

	public void setText() {
		if (s != null) {
			if ((!s.get("date").equals("")) || s.get("date") != null) {
				dataControl(s.get("date"));
			}
			person_name.setText(s.get("Name"));
			person_cardid.setText(s.get("IdCardNo"));
			office.setText(s.get("IdCardAuthority"));
			RentAdress.setText(s.get("IdCardLocation"));
			// 设置默认选中
			if (s.get("Sex") != null || s.get("Sex").equals("")) {
				if (getselect(s.get("Sex"), mItems) != 200) {
					Sex.setSelection(getselect(s.get("Sex"), mItems), true);
				} else {

				}
			}
			if (s.get("Nation") != null || s.get("Nation").equals("")) {
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
	
	//改变map里的值设置
	/**
	 * 
	 * @param 对回传的数据进行处理后传给页面
	 *            yzq 2016.6
	 */
	@SuppressWarnings("unused")
	
	public void setsex(String sex){
		if (sex == null ) {
			s.put("Gender", "1");
		}else{
			if (sex.equals("男")) {
				s.put("Gender", "1");
			} else if (sex.equals("女")) {
				s.put("Gender", "2");
			}
		}
	}
	
	public void dataControl(String sur) {
		String begin, end, newbegin, newend;
		if(sur == null || sur.isEmpty()){
			s.put("IdCardBeginTime", "");
			s.put("IdCardEndTime", "");
		}else{
			begin = sur.substring(0, sur.indexOf("-"));
			newbegin = begin.substring(0, 4) + "-" + begin.substring(4, 6) + "-"
					+ begin.substring(6, begin.length());
			end = sur.substring(sur.indexOf("-") + 1, sur.length());
			newend = end.substring(0, 4) + "-" + end.substring(4, 6) + "-"
					+ end.substring(6, end.length());
			s.put("IdCardBeginTime", newbegin);
			s.put("IdCardEndTime", newend);
		}
	}
	
	public void setnation(String nation){
		String[] str = { "汉", "蒙古", "回", "藏", "维吾尔", "苗", "彝", "壮", "布依", "朝鲜",
				"满", "侗", "瑶", "白", "土家", "哈尼", "哈萨克", "傣", "黎", "傈僳", "佤",
				"畲", "高山", "拉祜", "水", "东乡", "纳西", "景颇", "柯尔克孜", "土", "达斡尔",
				"仫佬", "羌", "布朗", "撒拉", "毛南", "仡佬", "锡伯", "阿昌", "普米", "塔吉克",
				"怒", "乌孜别克", "俄罗斯", "鄂温克", "德昂", "保安", "裕固", "京", "塔塔尔", "独龙",
				"鄂伦春", "赫哲", "门巴", "珞巴族", "基诺", "穿青", "外国血统", "其它" };
		if (nation ==null) {
			s.put("Nation", "1");
		}else{
			for (int i = 0; i < str.length; i++) {
				if (str[i].equals(nation)) {
					if (i == 57) {
						s.put("Nation", "59");
						break;
					}
					if (i == 58) {
						s.put("Nation", "98");
						break;
					}
					if (i == 59) {
						s.put("Nation", "99");
						break;
					}
					s.put("Nation", "" + (++i));
				}
			}
		}
	}
}
