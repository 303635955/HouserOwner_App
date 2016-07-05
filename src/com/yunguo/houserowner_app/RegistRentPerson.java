package com.yunguo.houserowner_app;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.IDCardReader.utility.DoubleDatePickerDialog;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.RegExpValidator;
import com.yunguo.Bean.SetUpRent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

	private Map<String, String> s = null;
	String Sextext = "��", nation = "��";
	String[] mItems, mItems1;

	private Form form; // ����У��
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_person);
		final Calendar c = Calendar.getInstance();
		// �����ͼ
		Intent intent = getIntent();
		// �õ����ݼ�
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			s = (Map<String, String>) bundle.get("LoginedIpInfoS");
		} else {

		}
		init();
		// ����ֵ
		setText();
		// ��ӵ���¼�
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

		// ��ʼ���ؼ�
		Sex = (Spinner) findViewById(R.id.Spinner_sex);
		// ��������Դ
		mItems = getResources().getStringArray(R.array.spinnersex);
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> sex_Adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems);
		// �� Adapter���ؼ�
		Sex.setAdapter(sex_Adapter);

		// ��ʼ���ؼ�
		Nationnal = (Spinner) findViewById(R.id.Spinner_nationnal);
		// ��������Դ
		mItems1 = getResources().getStringArray(R.array.spinnernationnal);
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> nation_Adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems1);
		// �� Adapter���ؼ�
		Nationnal.setAdapter(nation_Adapter1);
	}

	public void setclick() {
		carddate.setOnFocusChangeListener(new OnFocusChangeListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "�˴������",Toast.LENGTH_SHORT).show();
				if(hasFocus){
					new DoubleDatePickerDialog(RegistRentPerson.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
						@Override
						// ���һ��false��ʾ����ʾ���ڣ����Ҫ��ʾ���ڣ�������������true���߲�������
						public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
								int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
								int endDayOfMonth) {
							String textstartString = String.format("%d-%d-%d", startYear,
									startMonthOfYear + 1, startDayOfMonth);
							carddate.setText(textstartString);
							String textendString = String.format("%d-%d-%d", endYear,
									endMonthOfYear + 1, endDayOfMonth);
							date.setText(textendString);
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
				}
			}
		});
		
		date.setOnFocusChangeListener(new OnFocusChangeListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "�˴������",Toast.LENGTH_SHORT).show();
				// ���һ��false��ʾ����ʾ���ڣ����Ҫ��ʾ���ڣ�������������true���߲�������
				if(hasFocus){
					new DoubleDatePickerDialog(RegistRentPerson.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
						@Override
						// ���һ��false��ʾ����ʾ���ڣ����Ҫ��ʾ���ڣ�������������true���߲�������
						public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
								int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
								int endDayOfMonth) {
							String textstartString = String.format("%d-%d-%d", startYear,
									startMonthOfYear + 1, startDayOfMonth);
							carddate.setText(textstartString);
							String textendString = String.format("%d-%d-%d", endYear,
									endMonthOfYear + 1, endDayOfMonth);
							date.setText(textendString);
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
				}
			}
		});
		/*carddate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});*/
		Sex.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				// ��ȡѡ��ֵ
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
				if(person_cardid.getText().toString().isEmpty()){
					String no =person_cardid.getText().toString();
					String birth = no.substring(6,10)+"-"+no.substring(10,12)+"-"+no.substring(12,14);
					s.put("Birthday", birth);
				}
				setsex(Sextext);
				setnation(nation);
				SetUpRent.getSetUpRent().setRentmap(s);
				Intent intent = new Intent(getApplicationContext(),
						RentOwnerActivity.class);
				startActivity(intent);
			}
		});

		// ���ذ�ť
		btn_back.setOnClickListener(mGoBack);

	}

	/**
	 * ����У��
	 */
	public void InputCheckout() {
		form = new Form();

		NotEmptyValidator notNull = new NotEmptyValidator(this); // �ǿ���֤
		RegExpValidator IdCarIs = new RegExpValidator(this);
		IdCarIs.setPattern("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])"); // ���֤��֤

		Validate telValidate1 = new Validate(person_name);
		telValidate1.addValidator(notNull);// �ǿ���֤

		Validate telValidate2 = new Validate(person_cardid);
		telValidate2.addValidator(notNull);// �ǿ���֤
		telValidate2.addValidator(IdCarIs);// ���֤��֤

		form.addValidates(telValidate1);
		form.addValidates(telValidate2);
	}

	public void setText() {
		if (s != null) {
			if ((!s.get("date").equals("")) || s.get("date") != null) {
				dataControl(s.get("date"));
				carddate.setText(s.get("IdCardBeginTime"));
				date.setText(s.get("IdCardEndTime"));
			}
			person_name.setText(s.get("Name"));
			person_cardid.setText(s.get("IdCardNo"));
			office.setText(s.get("IdCardAuthority"));
			RentAdress.setText(s.get("IdCardLocation"));
			// ����Ĭ��ѡ��
			if (s.get("Sex") != null && !s.get("Sex").equals("") && !(s.get("Sex").isEmpty())) {
				if (getselect(s.get("Sex"), mItems) != 200) {
					Sex.setSelection(getselect(s.get("Sex"), mItems), true);
				} else {

				}
			}
			if (s.get("Nation") != null  &&  !s.get("Nation").equals("")  &&  !(s.get("Nation").isEmpty()) ) {
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
			if (str.equals(data[i])) {
				break;
			}
		}
		if(str != data[58]){
			i=0;
		}
		return i;
	}

	// �ı�map���ֵ����
	/**
	 * 
	 * @param �Իش������ݽ��д���󴫸�ҳ��
	 *            yzq 2016.6
	 */
	@SuppressWarnings("unused")
	public void setsex(String sex) {
		if (sex == null) {
			s.put("Gender", "1");
		} else {
			if (sex.equals("��")) {
				s.put("Gender", "1");
			} else if (sex.equals("Ů")) {
				s.put("Gender", "2");
			}else {
				s.put("Gender", "1");
			}
		}
	}

	public void dataControl(String sur) {
		String begin, end, newbegin, newend;
		if (sur == null || sur.isEmpty()) {
			s.put("IdCardBeginTime", "");
			s.put("IdCardEndTime", "");
		} else {
			begin = sur.substring(0, sur.indexOf("-"));
			newbegin = begin.substring(0, 4) + "-" + begin.substring(4, 6)
					+ "-" + begin.substring(6, begin.length());
			end = sur.substring(sur.indexOf("-") + 1, sur.length());
			newend = end.substring(0, 4) + "-" + end.substring(4, 6) + "-"
					+ end.substring(6, end.length());
			s.put("IdCardBeginTime", newbegin);
			s.put("IdCardEndTime", newend);
		}
	}

	public void setnation(String nation) {
		String[] str = { "��", "�ɹ�", "��", "��", "ά���", "��", "��", "׳", "����", "����",
				"��", "��", "��", "��", "����", "����", "������", "��", "��", "����", "��",
				"�", "��ɽ", "����", "ˮ", "����", "����", "����", "�¶�����", "��", "���Ӷ�",
				"����", "Ǽ", "����", "����", "ë��", "����", "����", "����", "����", "������",
				"ŭ", "���α��", "����˹", "���¿�", "�°�", "����", "ԣ��", "��", "������", "����",
				"���״�", "����", "�Ű�", "�����", "��ŵ", "����", "���Ѫͳ", "����" };
		if (nation == null) {
			s.put("Nation", "0");
		} else {
			int i;
			for ( i = 0; i < str.length; i++) {
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
			if(i != str.length){
				s.put("Nation", "0");
			}
		}
	}
}
