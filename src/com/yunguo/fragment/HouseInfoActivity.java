package com.yunguo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.houserowner.adpter.MyHouseInfoExpandableListAdapter;
import com.yunguo.houserowner_app.R;

public class HouseInfoActivity extends Activity {
	private String masg = ""; // 提示消息
	private Boolean fla = true; //记录上拉下拉
	private PullToRefreshExpandableListView listView;
	private List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
	private MyHouseInfoExpandableListAdapter adapter;
	private ImageView gifimg;
	private AnimationDrawable animaition;
	private LinearLayout loadlinear;
	private TextView showtext;
	private ImageView backimg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.houseinfo_main);
		findView();
		initListView();
		setOnClick();
		animaition.start();
		new Thread(thread).start();
	}

	public void findView() {
		listView = (PullToRefreshExpandableListView) findViewById(R.id.home_expandableListView);
		listView.setVisibility(View.GONE);
		listView.getRefreshableView().setGroupIndicator(null);  
		listView.getRefreshableView().setDivider(null);  
		listView.getRefreshableView().setSelector(android.R.color.transparent);  
		listView.setMode(Mode.BOTH);
		
		loadlinear = (LinearLayout) findViewById(R.id.loadlinear);
		gifimg = (ImageView) findViewById(R.id.gifimg);
		showtext = (TextView) findViewById(R.id.showtext);
		backimg = (ImageView) findViewById(R.id.backimg);
	}

	public void initListView() {
		adapter = new MyHouseInfoExpandableListAdapter(groups, this);
		listView.getRefreshableView().setAdapter(adapter);
		
		gifimg.setBackgroundResource(R.anim.gifload);
		animaition = (AnimationDrawable) gifimg.getBackground();
		animaition.setOneShot(false);
	}
	
	public void setOnClick(){
		
		listView.setOnRefreshListener(new OnRefreshListener2<ExpandableListView>() {
		@Override
		public void onPullDownToRefresh(
				PullToRefreshBase<ExpandableListView> refreshView) {
			new Thread(thread).start();
		}

		@Override
		public void onPullUpToRefresh(
				PullToRefreshBase<ExpandableListView> refreshView) {
			new Thread(thread).start();
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
					groups.clear();
				}
				@SuppressWarnings("unchecked")
				List<Map<String,String>> list = (List<Map<String,String>>)msg.obj;
				groups.addAll(list);
				adapter.notifyDataSetChanged();
				listView.getRefreshableView().setAdapter(adapter);
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
			Toast.makeText(HouseInfoActivity.this, masg, Toast.LENGTH_SHORT).show();
		};
	};
	
	
	
	
	private Thread thread = new Thread(){
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String url = "http://120.25.65.125:8118/HouseMobileApp/HouseListView";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("ownerId", "1544");
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
					if(getHouseInfo(res).equals("") || getHouseInfo(res) == null){
						handler.sendEmptyMessage(1);
						return;
					}
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


	public List<Map<String, String>> getHouseInfo(String res) {
	 List<Map<String, String>> tmpdata = new ArrayList<Map<String, String>>();
		try {
			JSONObject jsonObject2 = new JSONObject(res);
			JSONArray jsonArray = jsonObject2.getJSONArray("houses");
			if(jsonArray.equals("") || jsonArray == null){
				return null;
			}
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
				map.put("HouseId", jsonObjectSon.getString("Id"));
				map.put("HouseName", jsonObjectSon.getString("Name"));
				map.put("Address", jsonObjectSon.getString("Address"));
				map.put("Usage", jsonObjectSon.getString("Useage"));
				map.put("Number", jsonObjectSon.getString("RoomCount"));
				map.put("Floor", jsonObjectSon.getString("FloorCount"));
				map.put("Householder", jsonObjectSon.getString("OwnerName"));
				map.put("Tel", jsonObjectSon.getString("TelNo"));
				tmpdata.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tmpdata;
	}

}
