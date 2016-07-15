package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.yunguo.Util.GetViersion;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.UpAPPVersion;
import com.yunguo.fragment.HomePageActivity;
import com.yunguo.fragment.HouseInfoActivity;
import com.yunguo.fragment.PersonInfoActivity;

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
    private RadioGroup radioGroup;
    private LinearLayout centerlinear;
    private String upAppUrl = "";
    private String AppVersion = "";
    private ProgressDialog m_progressDlg;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		m_progressDlg =  new ProgressDialog(this);
		m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确     
		m_progressDlg.setIndeterminate(false);
		new Thread(thread).start();
	    findView();
	    setAdapter();
	}
	
	@SuppressWarnings("deprecation")
	public void findView(){
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		centerlinear = (LinearLayout) findViewById(R.id.centerlinear);
		//设置初始界面
		centerlinear.addView(getLocalActivityManager().startActivity(
				"v1",
				new Intent(MainActivity.this, HomePageActivity.class).putExtra("values", "0")
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
	}
	
	public void setAdapter(){
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	            @SuppressWarnings("deprecation")
				@Override
	            public void onCheckedChanged(RadioGroup group, int checkedId) {
	            	centerlinear.removeAllViews();
	            	switch (checkedId) {
					case R.id.radio0:
						centerlinear.addView(getLocalActivityManager().startActivity(
								"v1",
								new Intent(MainActivity.this, HomePageActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView());
						break;
					case R.id.radio1:
						centerlinear.addView(getLocalActivityManager().startActivity(
								"v1",
								new Intent(MainActivity.this, HouseInfoActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView());
						break;
					case R.id.radio2:
						centerlinear.addView(getLocalActivityManager().startActivity(
								"v1",
								new Intent(MainActivity.this, PersonInfoActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView());
						break;
	            	}
	                
	            }
	        });
	}
	/**
	 * 更新版本
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				 AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("已检查更新，是否现在更新？");
					builder.setPositiveButton("更新",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									 m_progressDlg.setTitle("正在下载");  
			                         m_progressDlg.setMessage("请稍候...");
									UpAPPVersion upapp = new UpAPPVersion(m_progressDlg, upAppUrl, AppVersion, MainActivity.this);
			                         upapp.downFile();
								}
							});
					builder.setNegativeButton("暂不更新", 
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							System.exit(0);
						}
					});
					builder.setCancelable(false);
					AlertDialog alertDialog = builder.create();
					alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener(){
						@Override
						public boolean onKey(DialogInterface dialog,
								int keyCode, KeyEvent event) {
							if(keyCode == KeyEvent.KEYCODE_SEARCH){
								return true;
							}else{
								return true;
							}
						}
						
					});
					
					alertDialog.show();
				break;
			}
		};
	};
	
	/**
	 * 检查版本更新
	 */
	private Thread thread = new Thread(){
		@Override
		public void run() {
			String version = GetViersion.getVerName(MainActivity.this);
			Map<String,String> map = new  HashMap<String,String>();
			map.put("type", "HouseOwner");
			map.put("version",version);
			try {
				JSONObject jsonObject = new JSONObject(map.toString());
				String ret = HTTPUtil.PostStringToUrl("http://120.25.65.125:8118/Client1/AppUpdate",jsonObject.toString());
				JSONObject jsonObject2 =new JSONObject(ret);
				String versionCode = (String) jsonObject2.get("match").toString();
				if(versionCode.equals("0")){
					upAppUrl = (String) jsonObject2.get("url");
					AppVersion = (String) jsonObject2.get("version");
					handler.sendEmptyMessage(0);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		};
	};
	
	
	
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
		 if(keyCode == KeyEvent.KEYCODE_BACK){
			 AlertDialog.Builder builder = new AlertDialog.Builder(
					 MainActivity.this);

				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("退出软件");
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								System.exit(0);
							}
						});
				builder.setNegativeButton("取消", null);
				builder.create().show();
				return true;
			} else {
				return super.onKeyDown(keyCode, event);
			}
	 }
	

}
