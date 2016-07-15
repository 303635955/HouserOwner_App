package com.yunguo.fragment;

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
import com.yunguo.fragment.PersonInfoActivity;
import com.yunguo.fragment.PersonInfoTenantInfoActivity;
import com.yunguo.houserowner.adpter.CarMessageAdapter;
import com.yunguo.houserowner.adpter.PersonAdapter;
import com.yunguo.houserowner_app.R;

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

public class CancellationActivity extends Activity{
	
	private String UserId = "";
	private String cardid = "";
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.carmessage_activity);
		
		UserId = getIntent().getStringExtra("UserId");
		
		findView();
		initListView();
		setOnClick();
		animaition.start();
		new Thread(thread).start();
		Toast.makeText(getApplicationContext(), "请选择开卡卡号", Toast.LENGTH_SHORT).show();
	}
	
	public void findView() {
		listView = (PullToRefreshListView) findViewById(R.id.home_expandableListView);
		listView.setVisibility(View.GONE);
		listView.setMode(Mode.BOTH);
		
		loadlinear = (LinearLayout) findViewById(R.id.loadlinear);
		gifimg = (ImageView) findViewById(R.id.gifimg);
		showtext = (TextView) findViewById(R.id.showtext);
		backimg = (ImageView) findViewById(R.id.backimg);
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
				Toast.makeText(getApplicationContext(),map+"", Toast.LENGTH_SHORT).show();
				cardid = map.get("CardNo").toString();
				new AlertDialog.Builder(CancellationActivity.this).setTitle("注销卡号").setMessage("确认注销？")
				.setNegativeButton("取消", new  DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.setPositiveButton("确定", new  DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which) {
						new Thread(thread2).start();
					}
				}).show();
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
				masg = "您还没有房屋！";
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
			Toast.makeText(CancellationActivity.this, masg, Toast.LENGTH_SHORT).show();
		};
	};
	
	
	
	
	private Thread thread = new Thread(){
		@Override
		public void run() {
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
					if(tmpdata == null || tmpdata.equals("")){
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


	public List<Map<String, String>> getHouseInfo(String res) {
	 List<Map<String, String>> tmpdata = new ArrayList<Map<String, String>>();
		try {
			JSONObject jsonObject2 = new JSONObject(res);
			JSONArray jsonArray = jsonObject2.getJSONArray("houses");
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
				map.put("CardNo", jsonObjectSon.getString("CardNo"));
				tmpdata.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tmpdata;
	}
	
	private Handler handler2 = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(),"注销失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(),"注销成功", Toast.LENGTH_SHORT).show();
				new Thread(thread).start();
				break;
			}
		};
	};
	
	private Thread thread2  = new Thread(){
		@Override
		public void run() {
			String url = "http://120.25.65.125:8118/HouseMobileApp/IcCardXiaoka";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("CardId",cardid);
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
					if(tmpdata == null || tmpdata.equals("")){
						handler2.sendEmptyMessage(1);
						return;
					}else{
						mes.obj = tmpdata;
					}
					handler2.sendMessage(mes);
				}else{
					handler2.sendEmptyMessage(1);
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler2.sendEmptyMessage(2);
			}
		}
	};
}
