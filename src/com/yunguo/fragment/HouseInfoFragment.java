package com.yunguo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yunguo.Bean.HouseBean;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.houserowner.adpter.ExpandInfoAdapter;
import com.yunguo.houserowner.adpter.HouseAdapter;
import com.yunguo.houserowner_app.R;

public class HouseInfoFragment extends Fragment {
	private String masg = ""; // ��ʾ��Ϣ
	public final static String NAME = "������:";
	public final static String Floor = "¥��:";
	public final static String Number = "����:";
	public final static String Use = "��;:";
	public final static String Owner = "����:";
	public final static String PHONE = "�绰:";
	public final static String Adress = "��ַ:";

	private static HTTPUtil httppost;
	private String codestr;

	private ExpandableListView listView;
	private TextView mTitle, showtext;
	private List<Map<String, String>> houseinfoList =new ArrayList<>();;
	public List<String> group;
	public List<List<String>> child;
	public ExpandInfoAdapter adapter;

	private ImageView gifimg;
	private AnimationDrawable animaition;
	private LinearLayout loadlinear;

	Map<String, String> map = new HashMap<>();
	/**
	 * listview
	 */
	/*
	 * private PullToRefreshListView HistoryMessge_list;
	 *//**
	 * �����б�������
	 */
	/*
	 * private HouseAdapter messgeListAdapter; private List<Map<String, String>>
	 * list; private List<HouseBean> data = new ArrayList<HouseBean>();
	 */

	private View view;

	private String id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.houseinfo_main, null);
		Bundle data = getArguments();
		id = data.getString("TEXT");
		Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
		findView();
		getHouseMessage("", handler);
		return view;
	}

	private void setListViewOnChildClickListener() {
		listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				return true;
			}
		});
	}

	public void findView() {
		loadlinear = (LinearLayout) view.findViewById(R.id.loadlinear);
		showtext = (TextView) view.findViewById(R.id.showtext);
		listView = (ExpandableListView) view
				.findViewById(R.id.expandable_list_view);
		gifimg = (ImageView) view.findViewById(R.id.gifimg);
		// mTitle = (TextView) view.findViewById(R.id.list_title_text);

		gifimg.setBackgroundResource(R.anim.animation);
		animaition = (AnimationDrawable) gifimg.getBackground();
		animaition.setOneShot(false);
	}

	public void initListView(List<Map<String, String>> list) {
		initialOther();
		for (int i = 0; i < list.size(); i++) {
			 map=(HashMap)list.get(i);
			 addItemByValue(map);
		}
		adapter = new ExpandInfoAdapter(getActivity(), group, child);
		listView.setAdapter(adapter);
	}

	public void initialOther() {
		group = new ArrayList<String>();
		child = new ArrayList<List<String>>();
	}

	public void addItemByValue(Map<String, String> map) {
		group.add(map.get("Name"));

		List<String> item = new ArrayList<String>();
		item.add(NAME + map.get("Name"));
		item.add(Floor + map.get("FloorCount"));
		item.add(Number + map.get("RoomCount"));
		item.add(Use + map.get("Useage"));
		item.add(Owner + map.get("OwnerName"));
		item.add(PHONE + map.get("TelNo"));
		item.add(Adress + map.get("Address"));
		child.add(item);
	}

	/**
	 * ˢ�½���
	 */
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			animaition.stop();
			switch (msg.what) {
			case 1:
				List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
				listView.setVisibility(View.VISIBLE);
				masg = "��ѯ�ɹ���";
				initListView(houseinfoList);
				setListViewOnChildClickListener();
				// ע�᳤��ѡ�������
				registerForContextMenu(listView);
				loadlinear.setVisibility(View.GONE);
				Toast.makeText(getContext(), "��ѯ�ɹ���", Toast.LENGTH_SHORT)
						.show();
				break;
			case 0:
				masg = "����û�з��ݣ�";
				gifimg.setBackgroundResource(R.drawable.gou);
				showtext.setText(masg);
				break;
			case 2:
				masg = "��ѯʧ�ܣ��������磡";
				gifimg.setBackgroundResource(R.drawable.gou);
				showtext.setText(masg);
				break;
			}
		};
	};

	/**
	 * ������������
	 */
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
					if(msg.equals("��ѯ�ɹ�")){
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
	}
}
