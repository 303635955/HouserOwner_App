package com.yunguo.houserowner_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.fragment.PersonInfoActivity;
import com.yunguo.houserowner.adpter.PersonAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OwnerMessageActivity extends Activity {
	private Button endUser;
	private ImageView backimg;
	private String masg;
	private ImageView gifimg;
	private AnimationDrawable animaition;
	private LinearLayout loadlinear;
	private TextView showtext;
	
	private Button upTelbut;
	
	private TextView Id,Name,Age,Gender,TelNo,IdCardNo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.owenrmessage_activity);
		findView();
		initListView();
		setOnClick();
		animaition.start();
		new Thread(thread).start();
	}
	
	public void findView(){
		endUser = (Button) findViewById(R.id.endUser);
		loadlinear = (LinearLayout) findViewById(R.id.loadlinear);
		gifimg = (ImageView) findViewById(R.id.gifimg);
		showtext = (TextView) findViewById(R.id.showtext);
		backimg = (ImageView) findViewById(R.id.backimg);
		
		Id = (TextView) findViewById(R.id.Id);
		Name = (TextView) findViewById(R.id.Name);
		Age = (TextView) findViewById(R.id.Age);
		Gender = (TextView) findViewById(R.id.Gender);
		TelNo = (TextView) findViewById(R.id.TelNo);
		IdCardNo = (TextView) findViewById(R.id.IdCardNo);
		
		upTelbut = (Button) findViewById(R.id.upTelbut);
	}
	
	public void initListView() {
		gifimg.setBackgroundResource(R.anim.gifload);
		animaition = (AnimationDrawable) gifimg.getBackground();
		animaition.setOneShot(false);
	}
	public void setOnClick(){
		endUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(OwnerMessageActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("注销登录？");
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								SharedPreferences sharedPreferences = getSharedPreferences("login_info",MODE_PRIVATE);
								sharedPreferences.edit().clear().commit();
								Intent intent = new Intent(OwnerMessageActivity.this,LoginActivity.class);
								startActivity(intent);
								finish();
							}
						});
				builder.setNegativeButton("取消", null);
				builder.create().show();
			}
		});
		
		/**
		 * 更换手机
		 */
		upTelbut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		backimg.setOnClickListener(new OnClickListener() {
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
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(android.os.Message msg) {
			loadlinear.setVisibility(View.GONE);
			animaition.stop();
			switch (msg.what) {
			case 0:
				masg = "查询成功！";
				setData((Map<String, String>)msg.obj);
				break;
			case 1: 
				masg = "获取信息失败，请检查网络连接！";
				gifimg.setBackgroundResource(R.drawable.gou);
				showtext.setText(masg);
				break;
			case 2:
				masg = "查询失败，请检查网络！";
				gifimg.setBackgroundResource(R.drawable.gou);
				showtext.setText(masg);
				break;
			}
			/**
			 * 停止刷新
			 */
			Toast.makeText(OwnerMessageActivity.this, masg, Toast.LENGTH_SHORT).show();
		};
	};
	
	private void setData(Map<String,String> map){
		Id.setText(map.get("Id"));
		Name.setText(map.get("Name"));
		Age.setText(map.get("Age"));
		
		String sex = map.get("Gender");
		if(sex.equals("1")){
			sex = "男";
		}else{
			sex = "女";
		}
		Gender.setText(sex);
		TelNo.setText( map.get("TelNo"));
		IdCardNo.setText( map.get("IdCardNo"));
	}
	
	private Thread thread = new Thread(){
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			String url = "http://120.25.65.125:8118/HouseMobileApp/LandlordInfo";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("OwnerId", SetUpRent.getSetUpRent().getOwnerId());
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
					mes.obj = getHouseInfo(res);
					handler.sendMessage(mes);
				}else{
					handler.sendEmptyMessage(1);
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(2);
			}
			
		};
	};


	public Map<String, String> getHouseInfo(String res) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject jsonObject2 = new JSONObject(res);
			JSONObject jsonObjectSon = jsonObject2.getJSONObject("house");
			map.put("Id", jsonObjectSon.getString("Id"));
			map.put("Name", jsonObjectSon.getString("Name"));
			map.put("Age", jsonObjectSon.getString("Age"));
			map.put("Gender", jsonObjectSon.getString("Gender"));
			map.put("TelNo", jsonObjectSon.getString("TelNo"));
			map.put("IdCardNo", jsonObjectSon.getString("IdCardNo"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
}
