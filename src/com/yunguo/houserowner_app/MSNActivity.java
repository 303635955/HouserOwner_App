package com.yunguo.houserowner_app;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.yunguo.Util.CodeUtils;
import com.yunguo.Util.CountDownTimerUtils;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.broadcast.SMSBroadcastReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MSNActivity extends Activity{
	
	private CountDownTimerUtils mCountDownTimerUtils;
	private Button codebut;
	private Button backbut;
	private Button nexdbut;
	private EditText inputcode;
	private SMSBroadcastReceiver mSMSBroadcastReceiver;
	private Form form;
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.msn_ativity);
		initView();
		mCountDownTimerUtils = new CountDownTimerUtils(codebut, 60000, 1000);
		mCountDownTimerUtils.start();
		new Thread(thread).start();
		setOnClick();
	}
	
	public void initView(){
		codebut = (Button) findViewById(R.id.codebut);
		backbut = (Button) findViewById(R.id.backbut);
		nexdbut = (Button) findViewById(R.id.nexdbut);
		inputcode = (EditText) findViewById(R.id.inputcode);
	}
	
	public void setOnClick(){
		codebut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCountDownTimerUtils.start();
				new Thread(thread).start();
			}
		});
		
		nexdbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(InputCheckout()){
					String codetmp = CodeUtils.getcode();
					if(inputcode.getText().toString().equals(codetmp)){
						Intent intent = new Intent(MSNActivity.this,UpDatepasswordActivity.class);
						intent.putExtra("bundle",MSNActivity.this.getIntent().getBundleExtra("bundle"));
						startActivity(intent);
						finish();
					}else{
						Toast.makeText(MSNActivity.this, "��֤�����", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
		
		backbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	/**
	 * ����У��
	 */
	public boolean InputCheckout(){
		form = new Form();
		NotEmptyValidator notNull= new NotEmptyValidator(this);	//�ǿ���֤
		Validate telValidate1 = new Validate(inputcode);
		telValidate1.addValidator(notNull);//�ǿ���֤
		
		form.addValidates(telValidate1);
		
		return form.validate();
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		 //���ɹ㲥����
        mSMSBroadcastReceiver = new SMSBroadcastReceiver();

        //ʵ����������������Ҫ���˵Ĺ㲥
        IntentFilter intentFilter = new IntentFilter(ACTION);
        intentFilter.setPriority(Integer.MAX_VALUE);
        //ע��㲥
        this.registerReceiver(mSMSBroadcastReceiver, intentFilter);

        mSMSBroadcastReceiver.setOnReceivedMessageListener(new SMSBroadcastReceiver.MessageListener() {
            @Override
            public void onReceived(String message) {
            	inputcode.setText(message);

            }
        });
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(MSNActivity.this, "���ͳɹ�", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(MSNActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(MSNActivity.this, "����ʧ��,��������", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	private Thread thread = new Thread(){
		@Override
		public void run() {
			try {
				String str = HTTPUtil.batchSend("http://222.73.117.169:80/msg/HttpBatchSendSM", "N9001627", "Ps346d97", "17780602344", "���ƹ��Ƽ���������֤��Ϊ:"+CodeUtils.createCode()+"", true, null);
				if(str.equals("")){
					handler.sendEmptyMessage(2);
				}else{
					String status = str.substring(str.indexOf(","),str.length());
					String ret = status.charAt(1)+""; 
					if(ret.equals("0")){
						handler.sendEmptyMessage(0);
					}else{
						handler.sendEmptyMessage(1);
					}
				}
				System.out.println(str);
			} catch (Exception e) {
				e.printStackTrace();
				handler.sendEmptyMessage(2);
			}
		};
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		 //ע�����ż����㲥
        this.unregisterReceiver(mSMSBroadcastReceiver);
	}
}
