package com.yunguo.houserowner_app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yunguo.Bean.HouseBean;
import com.yunguo.houserowner.adpter.HouseAdapter;

public class PersonInfoActivity extends Activity {
	/**
	 * listview
	 */
	private PullToRefreshListView HistoryMessge_list;
	/**
	 * �����б�������
	 */
	private HouseAdapter messgeListAdapter;
	private String[] namelist = {"ʨ��","ˮƿ","��з","Ħ��","����","��Ů��","��ţ","����","˫��","����","��Ы","˫��"};
	private List<HouseBean> data = new ArrayList<HouseBean>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.houseinfo_main);
		HistoryMessge_list = (PullToRefreshListView) findViewById(R.id.houser_adpter_list);
		data = getData();  
		messgeListAdapter = new HouseAdapter(data, this);
		HistoryMessge_list.setAdapter(messgeListAdapter);
		HistoryMessge_list.setMode(Mode.BOTH);

		init();
		HistoryMessge_list.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						HouseBean bean = new HouseBean();
						bean.setHouseName("111");
						bean.setHouseAdress("����·5��");
		               //messgeListAdapter.addFirst(bean);
						new FinishRefresh().execute();
						messgeListAdapter.notifyDataSetChanged();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						HouseBean bean = new HouseBean();
						bean.setHouseName("111");
						bean.setHouseAdress("����·5��");
						//messgeListAdapter.addLast(bean);
						new FinishRefresh().execute();
						messgeListAdapter.notifyDataSetChanged();
					}
				});
		
		HistoryMessge_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String a=data.get(position-1).getHouseName();
			}
		});
	};

	private void init() {
		ILoadingLayout startLabels = HistoryMessge_list.getLoadingLayoutProxy(
				true, false);
		startLabels.setPullLabel("����ˢ��...");// ������ʱ����ʾ����ʾ
		startLabels.setRefreshingLabel("��������...");// ˢ��ʱ
		startLabels.setReleaseLabel("�ſ�ˢ��...");// �����ﵽһ������ʱ����ʾ����ʾ

		ILoadingLayout endLabels = HistoryMessge_list.getLoadingLayoutProxy(
				false, true);
		endLabels.setPullLabel("����ˢ��...");// ������ʱ����ʾ����ʾ
		endLabels.setRefreshingLabel("��������...");// ˢ��ʱ
		endLabels.setReleaseLabel("�ſ�ˢ��...");// �����ﵽһ������ʱ����ʾ����ʾ
	}

	private List<HouseBean> getData() {
		List<HouseBean> list = new ArrayList<HouseBean>();
		for (int i = 0; i < 12; i++) {
			HouseBean bean = new HouseBean();
			bean.setHouseName(namelist[i]);
			bean.setHouseAdress("����·5"+i+"��");
			list.add(bean);
		}
		return list;
	}

	private class FinishRefresh extends AsyncTask<Void, Void, Void> {
		List<HouseBean> list = new ArrayList<HouseBean>();
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			for (int i = 0; i < 12; i++) {
				HouseBean bean = new HouseBean();
				bean.setHouseName(namelist[i]);
				bean.setHouseAdress("����·5"+i+"��");
				data.add(bean);
			}
			messgeListAdapter.notifyDataSetChanged();
			HistoryMessge_list.onRefreshComplete();
			Toast.makeText(PersonInfoActivity.this,"ִ�н���", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}
}

