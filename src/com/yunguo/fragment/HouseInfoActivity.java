package com.yunguo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunguo.Bean.HouseBean;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.QQListView;
import com.yunguo.houserowner.adpter.ExpandInfoAdapter;
import com.yunguo.houserowner.adpter.HouseAdapter;
import com.yunguo.houserowner.adpter.QQListAdapter;
import com.yunguo.houserowner_app.R;

public class HouseInfoActivity extends Activity {
	private String masg = ""; // 提示消息
	public final static String NAME = "房间名:";
	public final static String Floor = "楼层:";
	public final static String Number = "数量:";
	public final static String Use = "用途:";
	public final static String Owner = "户主:";
	public final static String PHONE = "电话:";
	public final static String Adress = "地址:";

	private QQListView listView;
	private List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
    private List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.houseinfo_main);
		findView();
		addItemByValue();
		initListView();
	}

	public void findView() {
		listView = (QQListView) findViewById(R.id.home_expandableListView);
	}

	public void initListView() {
		listView.setHeaderView(getLayoutInflater().inflate(R.layout.group_header,listView,false));
	        
		QQListAdapter adapter = new QQListAdapter(   
                this,listView, groups, R.layout.group, new String[] { "HouseName" },   
                new int[] { R.id.HouseName }, childs, R.layout.child,   
                new String[] { "HouseId","HouseName","Floor","Number","Usage","Householder","Tel","Address"}, 
                new int[] { R.id.HouseId,R.id.HouseName,R.id.Floor,R.id.Number,R.id.Usage,R.id.Householder,R.id.Tel,R.id.Address});     
		listView.setAdapter(adapter);
	}

	
	public void addItemByValue() {
		
		for (int i = 0; i < 10; i++) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("HouseName", "保利新天地"+i);
			groups.add(map);
		}
		
		for (int i = 0; i < 10; i++) {
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			Map<String,String> map = new HashMap<String,String>();
			map.put("HouseId", "编号："+"0218"+i);
			map.put("HouseName", "房屋："+"保利新天地"+i);
			map.put("Floor", "楼层："+1+i+"楼");
			map.put("Number","数量："+i+1+"间" );
			map.put("Usage", "用途：居住");
			map.put("Householder","户主：张赛");
			map.put("Tel", "电话：17780602344");
			map.put("Address","地址：成都市百草路保利国际");
			list.add(map);
			childs.add(list);
		}
	}

	/**
	 * 刷新界面
	 *//*
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			animaition.stop();
			switch (msg.what) {
			case 1:
				List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
				listView.setVisibility(View.VISIBLE);
				masg = "查询成功！";
				initListView(houseinfoList);
				setListViewOnChildClickListener();
				// 注册长按选项弹出莱单
				registerForContextMenu(listView);
				loadlinear.setVisibility(View.GONE);
				Toast.makeText(getContext(), "查询成功！", Toast.LENGTH_SHORT)
						.show();
				break;
			case 0:
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
		};
	};

	*//**
	 * 请求网络数据
	 *//*
	public void getHouseMessage(final String pramr, final Handler handler) {
		animaition.start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				String url = "http://192.168.1.144:8118/HouseMobileApp/HouseListView";
				Map<String, String> houseid = new HashMap<String, String>();
				houseid.put("ownerId", "1544");
				JSONObject js = new JSONObject(houseid);
				String str = js.toString();
				String res = httppost.PostStringToUrl(url, str);
				if(res == null){
					handler.sendEmptyMessage(2);
				}
				houseinfoList = getHouseInfo(res);
				try {
					JSONObject josn = new JSONObject(res);
					String msg = josn.get("Message").toString();
					if(msg.equals("查询成功")){
						handler.sendEmptyMessage(1);
					}else {
						handler.sendEmptyMessage(0);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public List<Map<String, String>> getHouseInfo(String res) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONObject jsonObject2 = new JSONObject(res);
			codestr = (String) jsonObject2.get("Message");
			JSONArray jsonArray = jsonObject2.getJSONArray("houses");
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
				map.put("Id", jsonObjectSon.getString("Id"));
				map.put("Name", jsonObjectSon.getString("Name"));
				map.put("Address", jsonObjectSon.getString("Address"));
				map.put("Useage", jsonObjectSon.getString("Useage"));
				map.put("RoomCount", jsonObjectSon.getString("RoomCount"));
				map.put("FloorCount", jsonObjectSon.getString("FloorCount"));
				map.put("OwnerName", jsonObjectSon.getString("OwnerName"));
				map.put("HouseOwnerTel", jsonObjectSon.getString("TelNo"));
				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}*/
}
