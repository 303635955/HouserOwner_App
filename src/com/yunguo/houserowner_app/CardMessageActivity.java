package com.yunguo.houserowner_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.MyDialogUtils;
import com.yunguo.fragment.CancellationActivity;
import com.yunguo.fragment.PersonInfoActivity;
import com.yunguo.fragment.PersonInfoTenantInfoActivity;
import com.yunguo.houserowner.adpter.CarMessageAdapter;
import com.yunguo.houserowner.adpter.PersonAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class CardMessageActivity extends Activity{
	
	private String UserId = "";
	private String type = ""; 
	
	private String masg = ""; // 提示消息
	private Boolean fla = true; //记录上拉下拉
	private PullToRefreshListView listView;
	private List<Map<String, String>> CarNoList = new ArrayList<Map<String, String>>();
	private CarMessageAdapter adapter;
	private ImageView gifimg;
	private AnimationDrawable animaition;
	private LinearLayout loadlinear;
	private TextView showtext;
	private ImageView backimg;
	private MyDialogUtils mydialog;
	private String cardid = "";
	
	private String CarNo;
	private String HouseId = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.carmessage_activity);
		
		UserId = getIntent().getStringExtra("UserId");
		type = getIntent().getStringExtra("type");
		
		findView();
		initListView();
		setOnClick();
		animaition.start();
		new Thread(thread).start();
		Toast.makeText(getApplicationContext(), "请选择卡号", Toast.LENGTH_SHORT).show();
	}
	
	public void findView() {
		listView = (PullToRefreshListView) findViewById(R.id.home_expandableListView);
		listView.setVisibility(View.GONE);
		listView.setMode(Mode.BOTH);
		
		loadlinear = (LinearLayout) findViewById(R.id.loadlinear);
		gifimg = (ImageView) findViewById(R.id.gifimg);
		showtext = (TextView) findViewById(R.id.showtext);
		backimg = (ImageView) findViewById(R.id.backimg);
		
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("请求中……");
	}

	public void initListView() {
		adapter = new CarMessageAdapter(CarNoList, this);
		listView.setAdapter(adapter);
		
		gifimg.setBackgroundResource(R.anim.gifload);
		animaition = (AnimationDrawable) gifimg.getBackground();
		animaition.setOneShot(false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setOnClick(){
		listView.setOnRefreshListener(new OnRefreshListener2() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				new Thread(thread).start();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				new Thread(thread).start();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String,String> map = (Map<String, String>) adapter.getItem(position-1);
				if(type.equals("权限")){
					Intent intent = new Intent(CardMessageActivity.this,AddPermissionActivity.class);
					intent.putExtra("CardNo", map.get("CardNo"));
					startActivity(intent);
				}else if(type.equals("挂失")){
					CarNo = map.get("CardNo");
					AlertDialog.Builder builder = new AlertDialog.Builder(CardMessageActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("确认挂失？");
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
				}else if(type.equals("补办")){
					Intent intent = new Intent(CardMessageActivity.this,RegisterCardActivity.class);
					intent.putExtra("CardNo", map.get("CardNo"));
					intent.putExtra("UserId", CardMessageActivity.this.getIntent().getStringExtra("UserId"));
					intent.putExtra("HouseId",CardMessageActivity.this.getIntent().getStringExtra("HouseId"));
					startActivity(intent);
				}else if(type.equals("续期")){
					Intent intent = new Intent(CardMessageActivity.this,RenewalActivity.class);
					intent.putExtra("CardNo", map.get("CardNo"));
					intent.putExtra("DoorId", CardMessageActivity.this.getIntent().getStringExtra("DoorId"));
					intent.putExtra("HouseId",CardMessageActivity.this.getIntent().getStringExtra("HouseId") );
					intent.putExtra("enTime",map.get("EndTime"));
					startActivity(intent);
				}else if(type.equals("注销")){
					cardid = map.get("CardNo");
					new AlertDialog.Builder(CardMessageActivity.this).setTitle("注销卡号").setMessage("确认注销？")
					.setNegativeButton("取消", new  DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.setPositiveButton("确定", new  DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int which) {
							new Thread(thread3).start();
						}
					}).show();
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
				listView.setVisibility(View.VISIBLE);
				if(fla){
					CarNoList.clear();
				}
				@SuppressWarnings("unchecked")
				List<Map<String,String>> list = (List<Map<String,String>>)msg.obj;
				CarNoList.addAll(list);
				adapter.notifyDataSetChanged();
				listView.setAdapter(adapter);
				masg = "查询成功！";
				break;
			case 1: 
				masg = "还没有门卡额！";
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
			listView.onRefreshComplete();
			Toast.makeText(CardMessageActivity.this, masg, Toast.LENGTH_SHORT).show();
		};
	};
	
	
	/**
	 * 刷新界面
	 */
	Handler handler2 = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if(mydialog != null){
				mydialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				masg = (String) msg.obj;
				listView.setVisibility(View.GONE);
				loadlinear.setVisibility(View.VISIBLE);
				animaition.start();
				new Thread(thread).start();
				break;
			case 1: 
				masg = (String) msg.obj;
				break;
			case 2:
				masg = "操作失败，卿检查网络！";
				break;
			}
			Toast.makeText(CardMessageActivity.this, masg, Toast.LENGTH_SHORT).show();
		};
	};
	
	
	Handler handler3 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				masg = "注销成功！";
				break;
			case 1:
				masg = "注销失败，请检查网络！";
				break;
			case 2:
				masg = "注销失败，请检查网络！";
				break;
			}
			Toast.makeText(CardMessageActivity.this, masg, Toast.LENGTH_SHORT).show();
		};
	};
	
	
	/**
	 * 门卡挂失
	 */
	private Thread thread2 = new Thread(){
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			String url = "http://120.25.65.125:8118/HouseMobileApp/IcCardGuashi";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("CardId", CarNo);
			JSONObject js = new JSONObject(houseid);
			String str = js.toString();
			String res = HTTPUtil.PostStringToUrl(url, str);
			if(res.equals("") || res == null){
				handler2.sendEmptyMessage(2);
				return;
			}
			JSONObject jsonObject2 = null;
			Message mes = new Message();
			try {
				jsonObject2 = new JSONObject(res);
				String ret = jsonObject2.get("ret")+"";
				if(ret.equals("1")){
					mes.what = 0;
					mes.obj = jsonObject2.get("msg");
				}else if(ret.equals("0")){
					mes.what = 1;
					mes.obj = jsonObject2.get("msg");
				}else{
					mes.what = 2;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				mes.what = 2;
			}
			handler2.sendMessage(mes);
		};
	};
	
	
	/**
	 * 查询门卡
	 */
	private Thread thread = new Thread(){
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			String url = "http://120.25.65.125:8118/HouseMobileApp/GetCardlistByTenaId";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("TenaId", UserId);
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
					List<Map<String, String>> tmpdata = getHouseInfo(res);
					if(tmpdata == null || tmpdata.equals("") || tmpdata.size() <= 0){
						handler.sendEmptyMessage(1);
						return;
					}else{
						mes.obj = tmpdata;
						
					}
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
	
	
	private Thread thread3  = new Thread(){
		@Override
		public void run() {
			String url = "http://120.25.65.125:8118/HouseMobileApp/IcCardXiaoka";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("CardId",cardid);
			JSONObject js = new JSONObject(houseid);
			String str = js.toString();
			String res = HTTPUtil.PostStringToUrl(url, str);
			if(res.equals("") || res == null){
				handler3.sendEmptyMessage(2);
				return;
			}
			JSONObject jsonObject2 = null;
			try {
				jsonObject2 = new JSONObject(res);
				String ret = jsonObject2.get("ret")+"";
				if(ret.equals("1")){
					handler3.sendEmptyMessage(0);
					return;
				}else{
					handler3.sendEmptyMessage(1);
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler3.sendEmptyMessage(2);
			}
		}
	};


	public List<Map<String, String>> getHouseInfo(String res) {
	 List<Map<String, String>> tmpdata = new ArrayList<Map<String, String>>();
		try {
			JSONObject jsonObject2 = new JSONObject(res);
			JSONArray jsonArray = jsonObject2.getJSONArray("houses");
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
				map.put("CardNo", jsonObjectSon.getString("CardNo"));
				map.put("BeginTime", jsonObjectSon.getString("BeginTime"));
				map.put("EndTime", jsonObjectSon.getString("EndTime"));
				tmpdata.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tmpdata;
	}
}
