package com.yunguo.houserowner_app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegistRentPerson extends Activity {
	private EditText person_name;
	private EditText person_cardid;
	private EditText person_housename;
	private EditText person_houseid;
	private EditText person_housecardid;
	private EditText person_begintime;
	private EditText person_endtime;
	
	private Button personinfo;
	private Button personinfo_other;
	
	private Spinner sex;
	private Spinner nation;
	private EditText person_office;
	private EditText person_cardtaketime;
	private EditText person_cardtime;
	private EditText person_homeadress;
	
	private Spinner Focus_attention;
	private Spinner Spinner_birth;
	private Spinner Spinner_contraception;
	private Spinner Spinner_plan;
	private Spinner Spinner_children;
	private Spinner Spinner_disability;
	private EditText person_warning;
	private EditText person_plan_number;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_person);
	}
	
	public void init(){
		person_name = (EditText) findViewById(R.id.person_name_text);
		person_cardid = (EditText) findViewById(R.id.person_cardid_text);
		person_housename = (EditText) findViewById(R.id.person_housename_text);
		person_houseid = (EditText) findViewById(R.id.person_houseid_text);
		person_housecardid = (EditText) findViewById(R.id.person_housedoorid_text);
		person_begintime = (EditText) findViewById(R.id.person_begintime_text);
		person_endtime = (EditText) findViewById(R.id.person_endtime_text);
		
		person_cardid = (EditText) findViewById(R.id.person_cardid_text);
		person_cardid = (EditText) findViewById(R.id.person_cardid_text);
		person_cardid = (EditText) findViewById(R.id.person_cardid_text);
		person_cardid = (EditText) findViewById(R.id.person_cardid_text);
		person_cardid = (EditText) findViewById(R.id.person_cardid_text);
		person_cardid = (EditText) findViewById(R.id.person_cardid_text);
	}
}
