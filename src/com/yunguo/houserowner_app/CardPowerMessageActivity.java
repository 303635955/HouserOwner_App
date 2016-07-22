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
import com.yunguo.houserowner.adpter.MyHouseCardPowerExpandableListAdapter;
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

public class CardPowerMessageActivity extends Activity{
	
	private String masg = ""; // 提示消息
	private Boolean fla = true; //记录上拉下拉
	private PullToRefreshListView listView;
	private List<Map<String, String>> CarNoList = new ArrayList<Map<String, String>>();
	private MyHouseCardPowerExpandableListAdapter adapter;
	private ImageView gifimg;
	private AnimationDrawable animaition;
	private LinearLayout loadlinear;
	private TextView showtext;
	private ImageView backimg;
	private MyDialogUtils mydialog;
	private String cardid = "";
	
	private String SerialNo = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cardpowermessage_activity);
		
		SerialNo = getIntent().getStringExtra("no");
		
		findView();
		initListView();
		setOnClick();
		animaition.start();
		new Thread(thread).start();
	}
	
	public void findView() {
		listView = (PullToRefreshListView) findViewById(R.id.powerhome_expandableListView);
		listView.setVisibility(View.GONE);
		listView.setMode(Mode.BOTH);
		
		loadlinear = (LinearLayout) findViewById(R.id.loadlinearpower);
		gifimg = (ImageView) findViewById(R.id.powergifimg);
		showtext = (TextView) findViewById(R.id.powershowtext);
		backimg = (ImageView) findViewById(R.id.powerbackimg);
		
		mydialog = new MyDialogUtils(this);
		mydialog.setCancelable(false); 
		mydialog.setTitle("请求中……");
	}

	public void initListView() {
		adapter = new MyHouseCardPowerExpandableListAdapter(CarNoList, this);
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
				loadlinear.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
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
			Toast.makeText(CardPowerMessageActivity.this, masg, Toast.LENGTH_SHORT).show();
		};
	};
	//查询
	 private Thread thread = new Thread(){
	    	@Override
	    	public void run() {
	    		try {
	    			Thread.sleep(1000);
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		Message msg = new Message();
	    		String url = "http://192.168.1.151:8118/HouseMobileApp/HouseLockCri";
	    		Map<String, String> houseid = new HashMap<String, String>();
	    		houseid.put("LockSn",SerialNo);
	    		houseid.put("rt","1");
	    		JSONObject js = new JSONObject(houseid);
	    		String str = js.toString();
	    		String res = HTTPUtil.PostStringToUrl(url, str);
	    		JSONObject jsonObject2 = null;
	    		if(res.equals("") || res == null){
					handler.sendEmptyMessage(2);
					return;
				}
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
	

	public List<Map<String, String>> getHouseInfo(String res) {
	 List<Map<String, String>> tmpdata = new ArrayList<Map<String, String>>();
		try {
			JSONObject jsonObject2 = new JSONObject(res);
			JSONArray jsonArray = jsonObject2.getJSONArray("msg");
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
					map.put("CardNo", jsonObjectSon.getString("CardNo"));
					map.put("BeginTime", jsonObjectSon.getString("BeginTime"));
					map.put("EndTime", jsonObjectSon.getString("EndTime"));
					String power = jsonObjectSon.getString("CriPri");
					if(power.equals("16") || power.equals("17")){
						map.put("CriPri", "有权限");
					}else if (power.equals("32") || power.equals("33")) {
						map.put("CriPri", "无权限");
					}else {
						map.put("CriPri", "问题权限");
					}
					tmpdata.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tmpdata;
	}
}
