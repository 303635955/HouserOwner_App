package com.yunguo.fragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.houserowner.adpter.MyHouseInfoExpandableListAdapter;
import com.yunguo.houserowner.adpter.MyPersonInfoExpandableListAdapter;
import com.yunguo.houserowner_app.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonInfoTenantInfoActivity extends Activity{
	
	private String HouseId = "";//房屋ID
	private String masg = ""; // 提示消息
	private Boolean fla = true; //记录上拉下拉
	private PullToRefreshExpandableListView listView;
	private List<String> groups = new ArrayList<String>();
	private List<List<Map<String,String>>> child = new ArrayList<List<Map<String,String>>>();
	private MyPersonInfoExpandableListAdapter adapter;
	private ImageView gifimg;
	private AnimationDrawable animaition;
	private LinearLayout loadlinear;
	private TextView showtext;
	
	private ImageView backimg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.houseinfotenant_activity);
		
		Intent intent = getIntent();
		HouseId = intent.getStringExtra("HouseId");
		
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
		adapter = new MyPersonInfoExpandableListAdapter(groups,child,this);
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
		
		listView.getRefreshableView().setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				@SuppressWarnings("unchecked")
				Map<String,String> map = (Map<String, String>) adapter.getChild(groupPosition, childPosition);
				Intent intent = new Intent(PersonInfoTenantInfoActivity.this,PersonManageActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("person", (Serializable) map);
				intent.putExtras(bundle);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), map.get("UserName"), Toast.LENGTH_SHORT).show();
				return false;
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
				List<List> list = (List<List>)msg.obj;
				groups.addAll(list.get(0));
				child.addAll(list.get(1));
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
			Toast.makeText(PersonInfoTenantInfoActivity.this, masg, Toast.LENGTH_SHORT).show();
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
			String url = "http://120.25.65.125:8118/HouseMobileApp/RentalInfo";
			Map<String, String> houseid = new HashMap<String, String>();
			houseid.put("HouseId", HouseId);
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
					List<List> list = getHouseInfo(res);
					if(list.equals("") || list == null){
						handler.sendEmptyMessage(1);
						return;
					}
					Message mes = new Message();
					mes.what = 0;
					mes.obj = list;
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


	@SuppressLint("SimpleDateFormat")
	public List<List> getHouseInfo(String res) {
	 List<List> tmpdata = new ArrayList<List>();
	 List<List<Map<String,String>>> tmplist1 = new ArrayList<List<Map<String,String>>>();
	 List<String> tmpdatalist = new ArrayList<String>();;
	 List<Map<String, String>> tmplist = null;
		try {
			JSONObject jsonObject2 = new JSONObject(res);
			JSONObject jsonObject3 = jsonObject2.getJSONObject("houses");
				@SuppressWarnings("rawtypes")
				Iterator it = jsonObject3.keys(); 
				while (it.hasNext()) {
					tmplist = new ArrayList<Map<String,String>>();
	                String key = (String) it.next();  
	                JSONArray array = jsonObject3.getJSONArray(key);  
	                for(int j=0;j<array.length();j++){ 
	                	Map<String, String> map = new HashMap<String,String>();
	                    JSONObject jsonobject = (JSONObject) array.opt(j);
	                    map.put("DoorId",jsonobject.getString("RoomNo"));
	                    map.put("UserName",jsonobject.getString("Name"));
	                    map.put("IdCardNo",jsonobject.getString("IdCardNo"));
	                    map.put("Sex",jsonobject.getString("Gender"));
	                    map.put("Age",jsonobject.getString("Age"));
	                    map.put("Tel",jsonobject.getString("TelNo"));
	                    map.put("Birthday",jsonobject.getString("Birthday"));
	                    map.put("IdCardAuthority",jsonobject.getString("IdCardAuthority"));
	                    map.put("IdCardBeginTime",jsonobject.getString("IdCardBeginTime"));
	                    map.put("IdCardEndTime",jsonobject.getString("IdCardEndTime"));
	                    map.put("IdCardLocation",jsonobject.getString("IdCardLocation"));
	                    tmplist.add(map);
	                }
	                tmplist1.add(tmplist);
	                tmpdatalist.add(key);
			}
			tmpdata.add(tmpdatalist);
			tmpdata.add(tmplist1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tmpdata;
	}
}
