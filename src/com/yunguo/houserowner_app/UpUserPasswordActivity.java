package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.yunguo.Bean.SetUpRent;
import com.yunguo.ImageLoderUtils.BPUtil;
import com.yunguo.Util.HTTPUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpUserPasswordActivity extends Activity{
	private ImageView authcode;
	private Button backbut;
	private Button nexdbut;
	private EditText textcode;
	private BPUtil bPUtil;
	private EditText register_user;
	private Form form;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.upuserpassword);
		initView();
		setOnClick();
	}
	
	public void initView(){
		
		bPUtil = BPUtil.getInstance();
		authcode = (ImageView) findViewById(R.id.authcode);
		authcode.setImageBitmap(BPUtil.getInstance().createBitmap());
		
		backbut = (Button) findViewById(R.id.backbut);
		nexdbut = (Button) findViewById(R.id.nexdbut);
		textcode = (EditText) findViewById(R.id.textcode);
		register_user = (EditText) findViewById(R.id.register_user);
	}
	

	/**
	 * 输入校验
	 */
	public boolean InputCheckout(){
		form = new Form();
		
		NotEmptyValidator notNull= new NotEmptyValidator(this);	//非空验证
		
		Validate telValidate1 = new Validate(register_user);
		telValidate1.addValidator(notNull);//非空验证
		
		Validate telValidate2 = new Validate(textcode);
		telValidate2.addValidator(notNull);//非空验证
		
		form.addValidates(telValidate1);
		form.addValidates(telValidate2);
		
		return form.validate();
	}
	
	public void setOnClick(){
		authcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//刷新验证码
				authcode.setImageBitmap(bPUtil.createBitmap());
			}
		});
		
		nexdbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(InputCheckout()){
					if(bPUtil.getCode().equalsIgnoreCase(textcode.getText()+"")){
						new Thread(thread).start();
					}else{
						Toast.makeText(UpUserPasswordActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
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
	
	private Handler handler = new Handler(){
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(UpUserPasswordActivity.this, "成功咯", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(UpUserPasswordActivity.this, "没有此账号", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(UpUserPasswordActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	
	private Thread thread = new Thread(){
		@Override
		public void run() {
			String url = "http://192.168.1.164:8118/HouseMobileApp/CheckedIsUser";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("IcCardNo",register_user.getText()+"");
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
					handler.sendEmptyMessage(0);
				}else{
					handler.sendEmptyMessage(1);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(2);
			}
		};
	};
	
}
