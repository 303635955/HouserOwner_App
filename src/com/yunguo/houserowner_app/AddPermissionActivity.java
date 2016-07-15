package com.yunguo.houserowner_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;
import com.yunguo.houserowner.adpter.AddPermissionAdapter;

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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddPermissionActivity extends Activity{
	
	private PullToRefreshGridView gridview;
	private AddPermissionAdapter openDoorAdapter;
	private List<Map<String,String>> houselist = new ArrayList<Map<String,String>>();
	private List<String> locksnlist = new ArrayList<String>();
	
	private boolean isChice[];
	private TextView openpermission;
	private ImageView gifimg;
	private AnimationDrawable animaition;
	private LinearLayout loadlinear;
	private TextView showtext;
	private ImageView backimg;
	private boolean fla = true;
	private String masg = "";
	private String msgstr = "";
	private MyDialogUtils mydialog;
	private String SerialNo = "";
	private String CardNo = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addpermission_activity);
		CardNo = getIntent().getStringExtra("CardNo");
		findView();
		setAdapter();
		setOnClick();
		animaition.start();
		new Thread(thread).start();
	}
	
	public void findView(){
		gridview = (PullToRefreshGridView) findViewById(R.id.gridview);
		openpermission = (TextView) findViewById(R.id.openpermission);
		
		loadlinear = (LinearLayout) findViewById(R.id.loadlinear);
		gifimg = (ImageView) findViewById(R.id.gifimg);
		showtext = (TextView) findViewById(R.id.showtext);
		backimg = (ImageView) findViewById(R.id.backimg);
	}
	
	public void setAdapter(){
		openDoorAdapter = new AddPermissionAdapter(houselist,getApplicationContext());
		gridview.setAdapter(openDoorAdapter);
		gridview.setMode(Mode.BOTH);
		gridview.setVisibility(View.GONE);
		
		gifimg.setBackgroundResource(R.anim.gifload);
		animaition = (AnimationDrawable) gifimg.getBackground();
		animaition.setOneShot(false);
		
		
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("请求中……");
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setOnClick(){
		gridview.setOnRefreshListener(new OnRefreshListener2() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				fla = true;
				new Thread(thread).start();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				fla = true;
				new Thread(thread).start();
			}
		});
		
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openDoorAdapter.chiceState(position);
			}
		});
		
		openpermission.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isChice = openDoorAdapter.getchiceState();
				for (int i = 0; i < isChice.length; i++) {
					if(isChice[i]){
						locksnlist.add(houselist.get(i).get("SerialNo"));
					}
				}
				if(locksnlist.size() > 0){
					AlertDialog.Builder builder = new AlertDialog.Builder(AddPermissionActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("确认开通权限？");
					builder.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									mydialog.show();
									new Thread(thread2).start();
								}
							});
					builder.setNegativeButton("取消", null);
					builder.create().show();
				}else{
					Toast.makeText(AddPermissionActivity.this,"请选择开通权限的锁", Toast.LENGTH_SHORT).show();
				}
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
		@Override
		public void handleMessage(android.os.Message msg) {
			animaition.stop();
			switch (msg.what) {
			case 0:
				loadlinear.setVisibility(View.GONE);
				gridview.setVisibility(View.VISIBLE);
				if(fla){
					houselist.clear();
				}
				@SuppressWarnings("unchecked")
				List<Map<String,String>> list = (List<Map<String,String>>)msg.obj;
				houselist.addAll(list);
				openDoorAdapter.notifyDataSetChanged();
				gridview.setAdapter(openDoorAdapter);
				masg = "查询成功！";
				break;
			case 1: 
				masg = "此房屋没有锁！";
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
			gridview.onRefreshComplete();
			Toast.makeText(AddPermissionActivity.this, masg, Toast.LENGTH_SHORT).show();
		};
	};
	
	
	private Handler handler2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				msgstr = (String) msg.obj;
				break;
			case 1:
				msgstr = (String) msg.obj;
				break;
			case 2:
				msgstr = "权限添加失败，请检查网络！";
				break;
			}
			locksnlist.clear();
			mydialog.dismiss();
			Toast.makeText(getApplicationContext(), msgstr, Toast.LENGTH_SHORT).show();
		};
	};
	
	
	private Thread thread = new Thread(){
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String url = "http://120.25.65.125:8118/Client1/QueryLockers";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("page","1");
			houseid.put("rows", "10");
			houseid.put("HouseId", SetUpRent.getSetUpRent().getHouseId());
			
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
	
	
	private Thread thread2 = new Thread(){
    	@Override
    	public void run() {
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		Message msg = new Message();
			String url = "http://120.25.65.125:8118/HouseMobileApp/CardSetCri";
			Map<String, Object> houseid = new HashMap<String, Object>();
			houseid.put("CardNo",CardNo);
			houseid.put("LockSn",new JSONArray(locksnlist).toString());
			
			JSONObject js = new JSONObject(houseid);
			String str = js.toString();
			String res = HTTPUtil.PostStringToUrl(url, str);
			JSONObject jsonObject2 = null;
			try {
				jsonObject2 = new JSONObject(res);
				String ret = jsonObject2.get("ret")+"";
				if(ret.equals("1")){
					msg.what = 0;
					msg.obj = jsonObject2.get("msg");
				}else if(ret.equals("0")){
					msg.what = 1;
					msg.obj = jsonObject2.get("msg");
				}else{
					msg.what = 2;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				msg.what = 2;
			}
			handler2.sendMessage(msg);
			
    	};
    };


	public List<Map<String, String>> getHouseInfo(String res) {
	 List<Map<String, String>> tmpdata = new ArrayList<Map<String, String>>();
		try {
			JSONObject jsonObject2 = new JSONObject(res);
			JSONArray jsonArray = jsonObject2.getJSONArray("lockers");
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
				map.put("SerialNo", jsonObjectSon.getString("SerialNo"));
				map.put("SerialName", jsonObjectSon.getString("Name"));
				tmpdata.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tmpdata;
	}
}
